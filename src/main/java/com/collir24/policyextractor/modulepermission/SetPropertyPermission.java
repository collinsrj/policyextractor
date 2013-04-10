package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class SetPropertyPermission extends ModulePermission {
	private final String propertyKey;
	private final String value;

	public SetPropertyPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String propertyKey, String value) {
		super(list, className, key, line);
		this.propertyKey = propertyKey;
		this.value = value;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public String getValue() {
		return value;
	}

}
