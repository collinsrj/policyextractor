package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class LoadPermission extends ModulePermission {

	private final String fileName;

	public LoadPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String fileName) {
		super(list, className, key, line);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

}
