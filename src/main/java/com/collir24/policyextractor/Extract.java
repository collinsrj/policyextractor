package com.collir24.policyextractor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

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
		Extract extract = new Extract(args);
		extract.examineFiles();
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
				System.out.println(permissions);
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
