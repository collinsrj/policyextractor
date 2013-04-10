package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class ExecPermission extends ModulePermission {
	private final String command;

	public ExecPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String command) {
		super(list, className, key, line);
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
