package com.collir24.policyextractor.modulepermission;

import java.util.List;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class GetDeclaredFieldPermission extends ModulePermission {
	private final String name;

	public GetDeclaredFieldPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String name) {
		super(list, className, key, line);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
