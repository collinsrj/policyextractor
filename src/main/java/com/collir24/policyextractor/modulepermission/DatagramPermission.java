package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class DatagramPermission extends ModulePermission {
	private final int port;

	public DatagramPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, int port) {
		super(list, className, key, line);
		this.port = port;
	}

	public int getPort() {
		return port;
	}
}
