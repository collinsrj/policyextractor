package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class LoadLibPermission extends ModulePermission {
	private final String libname;

	public LoadLibPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String libname) {
		super(list, className, key, line);
		this.libname = libname;
	}

	public String getFileName() {
		return libname;
	}
}
