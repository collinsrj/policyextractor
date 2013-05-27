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

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.collir24.policyextractor.modulepermission.DatagramPermission;
import com.collir24.policyextractor.modulepermission.DefineClassInPackagePermission;
import com.collir24.policyextractor.modulepermission.ExecPermission;
import com.collir24.policyextractor.modulepermission.FileInputStreamPermission;
import com.collir24.policyextractor.modulepermission.FileOutputStreamPermission;
import com.collir24.policyextractor.modulepermission.LoadLibPermission;
import com.collir24.policyextractor.modulepermission.LoadPermission;
import com.collir24.policyextractor.modulepermission.PropertyPermission;
import com.collir24.policyextractor.modulepermission.RandomAccessFilePermission;
import com.collir24.policyextractor.modulepermission.SetPropertyPermission;
import com.collir24.policyextractor.modulepermission.ThreadNamePermission;

public class ExtractorVisitor extends MethodVisitor {
	private static final MethodPermissions PERMISSIONS = new MethodPermissions();
	private final ModulePermissions modulePermissions;
	private final String className;
	private int line;
	private List<Object> stackConstants = new ArrayList<Object>();
	private Boolean booleanOnStack = null;
	private Integer intOnStack = null;

	protected ExtractorVisitor(final ModulePermissions modulePermissions,
			final String className) {
		super(Opcodes.ASM4);
		this.modulePermissions = modulePermissions;
		this.className = className;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		MethodPermissionKey key = new MethodPermissionKey(owner, name, desc);
		switch (stackConstants.size()) {
		case 1: {
			addPermission(key, stackConstants.get(0));
			break;
		}
		case 2: {
			addPermission(key, stackConstants.get(0), stackConstants.get(1));
			break;
		}
		default: {
			addPermission(key);
		}
		}
		clear();
	}

	private void addPermission(MethodPermissionKey key, Object object,
			Object object2) {
		if (MethodPermissionKey.GET_PROPERTY_DEFAULT.equals(key)
				&& object instanceof String && object2 instanceof String) {
			modulePermissions.add(new PropertyPermission(PERMISSIONS.get(key),
					className, key, line, (String) object, (String) object2));
		} else if (MethodPermissionKey.RANDOM_ACCESS_FILE_STRING_CONSTRUCTOR
				.equals(key)
				&& object instanceof String
				&& object2 instanceof String) {
			modulePermissions.add(new RandomAccessFilePermission(PERMISSIONS
					.get(key), className, key, line, (String) object,
					(String) object2));
		} else if (MethodPermissionKey.SET_PROPERTY.equals(key)
				&& object instanceof String && object2 instanceof String) {
			modulePermissions.add(new SetPropertyPermission(PERMISSIONS
					.get(key), className, key, line, (String) object,
					(String) object2));
		} else {
			// we haven't found any more specific permission
			addPermission(key);
		}

	}

	private void addPermission(MethodPermissionKey key, Object object) {
		if (MethodPermissionKey.GET_PROPERTY.equals(key)
				&& object instanceof String) {
			modulePermissions.add(new PropertyPermission(PERMISSIONS.get(key),
					className, key, line, (String) object));
		} else if (MethodPermissionKey.SET_THREAD_NAME.equals(key)
				&& object instanceof String) {
			modulePermissions
					.add(new ThreadNamePermission(PERMISSIONS.get(key),
							className, key, line, (String) object));
		} else if (MethodPermissionKey.FILE_INPUT_STREAM_CONSTRUCTOR
				.equals(key) && object instanceof String) {
			modulePermissions.add(new FileInputStreamPermission(PERMISSIONS
					.get(key), className, key, line, (String) object));
		} else if (MethodPermissionKey.FILE_OUTPUT_STREAM_CONSTRUCTOR
				.equals(key) && object instanceof String) {
			modulePermissions.add(new FileOutputStreamPermission(PERMISSIONS
					.get(key), className, key, line, (String) object));
		} else if (MethodPermissionKey.FILE_OUTPUT_STREAM_APPEND_CONSTRUCTOR
				.equals(key)
				&& object instanceof String
				&& booleanOnStack != null) {
			modulePermissions.add(new FileOutputStreamPermission(PERMISSIONS
					.get(key), className, key, line, (String) object,
					booleanOnStack));
		} else if (MethodPermissionKey.RANDOM_ACCESS_FILE_CONSTRUCTOR
				.equals(key) && object instanceof String) {
			modulePermissions.add(new RandomAccessFilePermission(PERMISSIONS
					.get(key), className, key, line, (String) object));
		} else if (MethodPermissionKey.RUNTIME_LOAD.equals(key)
				&& object instanceof String) {
			modulePermissions.add(new LoadPermission(PERMISSIONS.get(key),
					className, key, line, (String) object));
		} else if (MethodPermissionKey.RUNTIME_LOAD_LIBRARY.equals(key)
				&& object instanceof String) {
			modulePermissions.add(new LoadLibPermission(PERMISSIONS.get(key),
					className, key, line, (String) object));
		} else if (MethodPermissionKey.DATAGRAM_CONSTRUCTOR.equals(key)
				&& object instanceof Integer) {
			modulePermissions.add(new DatagramPermission(PERMISSIONS.get(key),
					className, key, line, ((Integer) object).intValue()));
		} else if (MethodPermissionKey.EXEC.equals(key)
				&& object instanceof String) {
			modulePermissions.add(new ExecPermission(PERMISSIONS.get(key),
					className, key, line, (String) object));
		} else if ((MethodPermissionKey.DEFINE_CLASS_1.equals(key)
				|| MethodPermissionKey.DEFINE_CLASS_2.equals(key) || MethodPermissionKey.DEFINE_CLASS_3
					.equals(key)) && object instanceof String) {
			modulePermissions
					.add(new DefineClassInPackagePermission(PERMISSIONS
							.get(key), className, key, line, (String) object));
		} else {
			// we haven't found any more specific permission
			addPermission(key);
		}
	}

	private void addPermission(MethodPermissionKey key) {
		if (MethodPermissionKey.DATAGRAM_CONSTRUCTOR.equals(key)
				&& intOnStack != null) {
			modulePermissions.add(new DatagramPermission(PERMISSIONS.get(key),
					className, key, line, intOnStack));
		} else if (PERMISSIONS.contains(key)) {
			modulePermissions.add(new ModulePermission(PERMISSIONS.get(key),
					className, key, line));
		}
	}

	@Override
	public void visitLdcInsn(Object cst) {
		stackConstants.add(cst);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		if (opcode == Opcodes.SIPUSH) {
			intOnStack = operand;
		}
	}

	@Override
	public void visitInsn(int opcode) {
		switch (opcode) {
		case Opcodes.ICONST_0: {
			booleanOnStack = false;
			break;
		}
		case Opcodes.ICONST_1: {
			booleanOnStack = true;
			break;
		}
		default: {
			booleanOnStack = null;
		}
		}
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		this.line = line;
		super.visitLineNumber(line, start);
	}

	/**
	 * Clear any local state
	 */
	private void clear() {
		stackConstants.clear();
		booleanOnStack = null;
		intOnStack = null;
	}

}
