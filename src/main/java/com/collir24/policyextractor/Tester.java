package com.collir24.policyextractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Tester {
	private static final ExecutorService EXEC = Executors
			.newSingleThreadExecutor();
	private static final Logger LOGGER = Logger.getLogger(Tester.class
			.getName());

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		PrintStream ps = new PrintStream("/Users/rcollins/Desktop/junk.txt");
		System.setOut(ps);
		Collection<File> files = FileUtils.listFiles(new File(
				"/Volumes/Maven/maven2/com/"), FileFilterUtils
				.suffixFileFilter("jar"), TrueFileFilter.INSTANCE);

		List<String> filePaths = new ArrayList<String>();
		for (File f : files) {
			filePaths.add(f.getAbsolutePath());
		}
		final String[] filePathArray = filePaths.toArray(new String[files
				.size()]);

		final BlockingQueue<ModulePermissions> permsQueue = new LinkedBlockingQueue<ModulePermissions>();
		EXEC.submit(new Runnable() {

			public void run() {
				Extract e = new Extract(filePathArray);
				e.examineFiles(permsQueue);
			}
		});
		EXEC.shutdown();
		writeOutput(permsQueue);
	}

	private static void writeOutput(
			final BlockingQueue<ModulePermissions> permsQueue) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("perms.gexf"));
			outputPermissions(permsQueue, writer);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem creating writer", e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Problem closing writer", e);
				}
			}
		}
	}

	private static void outputPermissions(
			final BlockingQueue<ModulePermissions> permsQueue,
			final Writer writer) {
		boolean timeout = false;
		XMLOutputter xmlOut = new XMLOutputter(Format.getCompactFormat());
		Element root = new Element("gexf", "http://www.gexf.net/1.2draft");
		root.setAttribute("version", "1.2");
		Element graph = new Element("graph", "http://www.gexf.net/1.2draft");
		Element attributes = new Element("attributes",
				"http://www.gexf.net/1.2draft");
		Element nodes = new Element("nodes", "http://www.gexf.net/1.2draft");
		Element edges = new Element("edges", "http://www.gexf.net/1.2draft");
		attributes.setAttribute("class", "node");
		Element permissionAttribute = new Element("attribute",
				"http://www.gexf.net/1.2draft");
		permissionAttribute.setAttribute("id", "0");
		permissionAttribute.setAttribute("title", "type");
		permissionAttribute.setAttribute("type", "string");
		Element defaultAttributeElement = new Element("default",
				"http://www.gexf.net/1.2draft");
		defaultAttributeElement.setText("module");
		permissionAttribute.addContent(defaultAttributeElement);
		attributes.addContent(permissionAttribute);

		graph.setAttribute("defaultedgetype", "directed");
		root.addContent(graph);
		graph.addContent(attributes);
		graph.addContent(nodes);
		graph.addContent(edges);
		Map<String, Integer> methodPermissionMap = getMethodPermissionMap();
		Set<String> permissionSet = new HashSet<String>();
		int nodeId = 10000;
		int edgeId = Integer.MAX_VALUE;
		while (!timeout) {
			permissionSet.clear();
			try {
				ModulePermissions perms = permsQueue.poll(5, TimeUnit.SECONDS);
				if (perms == null) {
					timeout = true;
					break;
				}

				List<ModulePermission> modulePermissionList = perms
						.getPermissions();
				for (ModulePermission permission : modulePermissionList) {
					for (Permission p : permission.getList()) {
						permissionSet.add(getPermissionKey(p));
					}
				}
				if (permissionSet.isEmpty()) {
					continue;
				}
				Element moduleElement = new Element("node",
						"http://www.gexf.net/1.2draft");
				moduleElement.setAttribute("id", Integer.toString(nodeId));
				moduleElement.setAttribute("label", perms.getModuleName());
				nodes.addContent(moduleElement);
				for (String permissionKey : permissionSet) {
					Element permissionLink = new Element("edge",
							"http://www.gexf.net/1.2draft");
					permissionLink.setAttribute("id", Integer.toString(edgeId));
					permissionLink.setAttribute("source",
							Integer.toString(nodeId));
					permissionLink.setAttribute("target", methodPermissionMap
							.get(permissionKey).toString());
					edges.addContent(permissionLink);
					edgeId--;
				}
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
			nodeId++;
		}
		for (Map.Entry<String, Integer> entry : methodPermissionMap.entrySet()) {
			Element permElement = new Element("node",
					"http://www.gexf.net/1.2draft");
			permElement.setAttribute("id", entry.getValue().toString());
			permElement.setAttribute("label", entry.getKey());
			nodes.addContent(permElement);
			Element attvalues = new Element("attvalues",
					"http://www.gexf.net/1.2draft");
			Element attvalue = new Element("attvalue",
					"http://www.gexf.net/1.2draft");
			attvalues.addContent(attvalue);
			permElement.addContent(attvalues);
			attvalue.setAttribute("for", "0");
			attvalue.setAttribute("value", "perm");
		}
		try {
			xmlOut.output(root, writer);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem writing doc", e);
		}
	}

	private static Map<String, Integer> getMethodPermissionMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		MethodPermissions perms = new MethodPermissions();
		int i = 1000;
		for (Permission p : perms.getAllPermissions()) {
			map.put(getPermissionKey(p), i);
			i++;
		}
		return map;
	}

	/**
	 * Return a simple key for the permission.
	 * 
	 * @return
	 */
	private static String getPermissionKey(Permission permission) {
		StringBuilder keyBuilder = new StringBuilder();
		return keyBuilder.append(permission.getPermission()).append(".")
				.append(permission.getTarget()).toString();
	}
}
