package com.collir24.policyextractor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ModulePermissions {
	private final String moduleName;
	private final List<ModulePermission> permissions = new LinkedList<ModulePermission>();

	public ModulePermissions(String name) {
		this.moduleName = name;
	}

	public void add(ModulePermission permission) {
		this.permissions.add(permission);
	}

	public void add(Collection<ModulePermission> permissions) {
		this.permissions.addAll(permissions);
	}

	public List<ModulePermission> getPermissions() {
		return permissions;
	}

	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String toString() {
		return "ModulePermissions [moduleName=" + moduleName + ", permissions="
				+ permissions + "]";
	}

}
