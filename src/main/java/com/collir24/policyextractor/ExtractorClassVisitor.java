package com.collir24.policyextractor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExtractorClassVisitor extends ClassVisitor {
	private final String className;
	private final ModulePermissions modulePermissions;

	public ExtractorClassVisitor(String className,
			ModulePermissions modPermissions) {
		super(Opcodes.ASM4);
		this.className = className;
		this.modulePermissions = modPermissions;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		return new ExtractorVisitor(modulePermissions, className);
	}
}
