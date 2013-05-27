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
package com.collir24.policyextractor.modulepermission;

import java.util.List;
import java.util.Set;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class LoadLibPermission extends ModulePermission {
	private final String libname;

	public LoadLibPermission(List<Permission> list, String className,
			MethodPermissionKey key, int line, String libname) {
		super(list, className, key, line);
		this.libname = libname;
	}

	public String getFileName() {
		return libname;
	}

	@Override
	public Set<String> getPolicy() {
		Set<String> policyStrings = super.getPolicy();
		if (policyStrings.size() != 1) {
			throw new IllegalStateException(
					"Should only be one policy for this permission.");
		}
		String policy = policyStrings.iterator().next();
		policyStrings.clear();
		policyStrings.add(policy.replace("{libName}", libname));
		return policyStrings;
	}
}
