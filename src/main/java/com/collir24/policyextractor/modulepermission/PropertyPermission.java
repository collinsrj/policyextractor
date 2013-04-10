package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class PropertyPermission extends ModulePermission {
	private final String propertyKey;
	private final String def;

	public PropertyPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String propertyKey) {
		super(list, className, key, line);
		this.propertyKey = propertyKey;
		this.def = null;
	}

	public PropertyPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String propertyKey, String def) {
		super(list, className, key, line);
		this.propertyKey = propertyKey;
		this.def = def;
	}

	public String getPropertyKey() {
		return propertyKey;
	}
	public String getDefault() {
		return def;
	}	
}
