package com.collir24.policyextractor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ModulePermissions {

	private final Set<Permission> permissions = new LinkedHashSet<Permission>();
	private final String moduleName;

	public ModulePermissions(String name) {
		this.moduleName = name;
	}

	void addAll(Collection<Permission> permission) {
		permissions.addAll(permission);
	}

	@Override
	public String toString() {
		return "ModulePermissions [permissions=" + permissions
				+ ", moduleName=" + moduleName + "]";
	}

}
