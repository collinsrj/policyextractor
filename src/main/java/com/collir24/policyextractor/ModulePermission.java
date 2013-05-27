/*
 * Copyright 2013 Robert Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.collir24.policyextractor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public String getClassName() {
		return className;
	}

	public int getLine() {
		return line;
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

	public Set<String> getPolicy() {
		Set<String> permissionSet = new HashSet<String>(list.size());
		StringBuilder sb = new StringBuilder();
		for (Permission p : list) {
			sb.setLength(0);
			String target = p.getTarget();
			List<String> actions = p.getActions();
			sb.append("permission ").append(p.getPermission().trim()).append(" ");
			if (target != null && !target.isEmpty()) {
				sb.append("\"").append(target).append("\"");
			}
			if (!actions.isEmpty()) {
				sb.append(" \"");
				for (int i = 0; i < actions.size(); i++) {
					sb.append(actions.get(i));
					i++;
					if (i != actions.size()) {
						sb.append(",");
					}
				}
				sb.append("\"");
			}
			sb.append(";");
			permissionSet.add(sb.toString());
		}
		return permissionSet;
	}
}
