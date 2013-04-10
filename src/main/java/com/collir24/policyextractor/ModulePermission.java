package com.collir24.policyextractor;

import java.util.List;

public class ModulePermission {
	/**
	 * A list of permissions required by this call
	 */
	private final List<Permission> list;
	/**
	 * The name of the class which requires the permissions
	 */
	private final String className;
	/**
	 * The
	 */
	private final MethodPermissionKey key;
	private final int line;

	public ModulePermission(List<Permission> list, String className,
			MethodPermissionKey key, int line) {
		super();
		this.list = list;
		this.className = className;
		this.key = key;
		this.line = line;
	}

	public MethodPermissionKey getKey() {
		return key;
	}

	public List<Permission> getList() {
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + line;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModulePermission other = (ModulePermission) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (line != other.line)
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModulePermission [list=" + list + ", className=" + className
				+ ", key=" + key + ", line=" + line + "]";
	}

}
