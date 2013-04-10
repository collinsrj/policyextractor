package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class RandomAccessFilePermission extends ModulePermission {

	private final String name;
	private final String mode;

	public RandomAccessFilePermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String mode) {
		super(list, className, key, line);
		this.name = null;
		this.mode = mode;
	}

	public RandomAccessFilePermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String name, String mode) {
		super(list, className, key, line);
		this.name = name;
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public String getMode() {
		return mode;
	}
}
