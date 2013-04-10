package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class FileOutputStreamPermission extends ModulePermission {
	private final String name;
	private final boolean append;

	public FileOutputStreamPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String name) {
		super(list, className, key, line);
		this.name = name;
		this.append = false;
	}

	public FileOutputStreamPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String name, boolean append) {
		super(list, className, key, line);
		this.name = name;
		this.append = append;
	}

	public String getName() {
		return name;
	}

	public boolean isAppend() {
		return append;
	}
}
