package com.collir24.policyextractor;

import org.objectweb.asm.MethodVisitor;

public class ExtractorVisitor extends MethodVisitor {
	private MethodPermissions permissions;
	private ModulePermissions modulePermissions;

	protected ExtractorVisitor(int api) {
		super(api);
	}

	public void setPermissions(MethodPermissions permissions) {
		this.permissions = permissions;
	}

	public void setModulePermissions(ModulePermissions modulePermissions) {
		this.modulePermissions = modulePermissions;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		String key = buildKey(owner, name, desc);
		System.out.println(owner + "\t" + name + "\t" + desc);
//		if (permissions.contains(key)) {
//			modulePermissions.addAll(permissions.get(key));
//		}
	}

	private static String buildKey(String owner, String name, String desc) {
		return new StringBuilder().append(owner).append(":").append(name)
				.append(":").append(desc).toString();
	}
}
