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
