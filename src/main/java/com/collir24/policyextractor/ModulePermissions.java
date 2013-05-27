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
