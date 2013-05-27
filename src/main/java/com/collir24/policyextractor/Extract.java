/*
 * Copyright 2013 Robert Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.collir24.policyextractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.objectweb.asm.ClassReader;

public class Extract {
	private static final Logger LOGGER = Logger.getLogger(Extract.class
			.getName());
	private final String[] filePaths;

	public Extract(String[] filePaths) {
		this.filePaths = filePaths;
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out
					.println("Usage: java com.collir24.policyextractor.Extract <file_path>");
			System.exit(1);
		}
		List<ModulePermissions> modulePermissions = new ArrayList<ModulePermissions>();
		Extract extract = new Extract(args);
		extract.examineFiles(modulePermissions);
		format(modulePermissions);
	}

	private static void format(List<ModulePermissions> modulePermissions) {
		XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("test.xml"));
			Document doc = buildDocument(modulePermissions);
			xmlOut.output(doc, writer);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem writing output file.", e);
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Problem writing output file.", e);
				}
			}
		}
	}

	private static Document buildDocument(
			List<ModulePermissions> modulePermissions) {
		Element modulePolicy = new Element("modulePolicy");
		for (ModulePermissions mps : modulePermissions) {
			Element module = new Element("module");
			module.setAttribute("name", mps.getModuleName());
			Set<String> policySet = new HashSet<String>();
			for (ModulePermission mp : mps.getPermissions()) {
				Element permRequired = new Element("permRequired");
				permRequired.setAttribute("line",
						Integer.toString(mp.getLine()));
				permRequired.setAttribute("className", mp.getClassName());
				for (String s : mp.getPolicy()) {
					Element perm = new Element("perm");
					perm.setText(s);
					permRequired.addContent(perm);
				}
				module.addContent(permRequired);
				// TODO: say what caused the permission to be required - see key
				policySet.addAll(mp.getPolicy());
			}
			CDATA policyData = new CDATA(generatePolicy(policySet));
			module.addContent(policyData);
			modulePolicy.addContent(module);
		}
		return new Document(modulePolicy);
	}

	private static String generatePolicy(Set<String> policySet) {
		StringBuilder sb = new StringBuilder();
		sb.append("grant {\n");
		for (String policyLine : policySet) {
			sb.append("\t").append(policyLine).append("\n");
		}
		sb.append("};\n");
		return sb.toString();
	}

	public void examineFiles() {
		for (String filePath : filePaths) {
			try {
				JarFile file = new JarFile(new File(filePath));
				examineFile(file);
			} catch (ZipException e) {
				System.err.println("Bad jar: " + filePath);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	/**
	 * Examines all the jar files found on the path specified in the
	 * constructor.
	 * 
	 * @param permissions
	 *            a collection into which module permissions will be placed
	 */
	public void examineFiles(Collection<ModulePermissions> permissions) {
		for (String filePath : filePaths) {
			try {
				JarFile file = new JarFile(new File(filePath));
				ModulePermissions permission = examineFile(file);
				if (permission != null) {
					permissions.add(permission);
				}
			} catch (ZipException e) {
				System.err.println("Bad jar: " + filePath);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public static ModulePermissions examineFile(JarFile file) {
		ModulePermissions permissions = null;
		try {
			permissions = visitClasses(file);
			if (!permissions.getPermissions().isEmpty()) {
				LOGGER.fine("Permissions are: " + permissions);
			}

		} catch (SecurityException se) {
			LOGGER.log(Level.SEVERE, "Problem looking at: " + file.getName(),
					se);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem looking at: " + file.getName(), e);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem looking at: " + file.getName(), e);
		}
		return permissions;
	}

	private static ModulePermissions visitClasses(JarFile file)
			throws IOException {
		final ModulePermissions modPermissions = new ModulePermissions(
				new File(file.getName()).getName());
		String className;
		Enumeration<? extends ZipEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.endsWith(".class")) {
				ClassReader cr = new ClassReader(file.getInputStream(entry));
				className = cr.getClassName();
				cr.accept(new ExtractorClassVisitor(className, modPermissions),
						0);
			}
		}
		return modPermissions;
	}

}
