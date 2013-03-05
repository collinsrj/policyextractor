package com.collir24.policyextractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Hello world!
 * 
 */
public class Extract {
	private static final Logger LOGGER = Logger.getLogger(Extract.class
			.getName());
	private final List<ZipFile> files;
	private final MethodPermissions permissions = new MethodPermissions();

	public Extract(String[] filePaths) {
		files = new ArrayList<ZipFile>(filePaths.length);
		for (String filePath : filePaths) {
			try {
				ZipFile file = new ZipFile(new File(filePath));
				files.add(file);
			} catch (ZipException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}

	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out
					.println("Usage: java com.collir24.policyextractor.Extract <jar_file>");
			System.exit(1);
		}

		Extract extract = new Extract(args);
		extract.examineFiles();
	}

	public void examineFiles() {
		for (ZipFile file : files) {
			LOGGER.info("Looking at: " + file.getName());
			try {
				visitClasses(file);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Problem looking at: " + file, e);
			}
		}
	}

	private void visitClasses(ZipFile file)
			throws IOException {
		final ModulePermissions modPermissions = new ModulePermissions(file.getName());		
		System.err.println(modPermissions);
		ClassVisitor cVisitor = new ClassVisitor(Opcodes.ASM4) {
			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				ExtractorVisitor visitor = new ExtractorVisitor(Opcodes.ASM4);
				visitor.setPermissions(permissions);
				visitor.setModulePermissions(modPermissions);
				return visitor;
			}
		};
		Enumeration<? extends ZipEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.endsWith(".class")) {
				ClassReader cr = new ClassReader(file.getInputStream(entry));
				cr.accept(cVisitor, ClassReader.SKIP_DEBUG);
			}
		}
		System.out.println(modPermissions);
	}

}
