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

public class MethodPermissionKey {
	private final String owner;
	private final String name;
	private final String description;
	public static final MethodPermissionKey GET_PROPERTY = new MethodPermissionKey(
			"java/lang/System", "getProperty",
			"(Ljava/lang/String;)Ljava/lang/String;");
	public static final MethodPermissionKey GET_PROPERTY_DEFAULT = new MethodPermissionKey(
			"java/lang/System", "getProperty",
			"getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
	public static final MethodPermissionKey SET_THREAD_NAME = new MethodPermissionKey(
			"java/lang/Thread", "setName", "(Ljava/lang/String;)V");
	public static final MethodPermissionKey FILE_INPUT_STREAM_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/FileInputStream", "<init>", "(Ljava/lang/String;)V");
	public static final MethodPermissionKey FILE_OUTPUT_STREAM_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/FileOutputStream", "<init>", "(Ljava/lang/String;)V");
	public static final MethodPermissionKey FILE_OUTPUT_STREAM_APPEND_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/FileOutputStream", "<init>", "(Ljava/lang/String;Z)V");
	public static final MethodPermissionKey RANDOM_ACCESS_FILE_STRING_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/RandomAccessFile", "<init>",
			"(Ljava/lang/String;Ljava/lang/String;)V");
	public static final MethodPermissionKey RANDOM_ACCESS_FILE_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/RandomAccessFile", "<init>",
			"(Ljava/io/File;Ljava/lang/String;)V");
	public static final MethodPermissionKey RUNTIME_LOAD = new MethodPermissionKey(
			"java/lang/Runtime", "load", "(Ljava/lang/String;)V");
	public static final MethodPermissionKey RUNTIME_LOAD_LIBRARY = new MethodPermissionKey(
			"java/lang/Runtime", "loadLibrary", "(Ljava/lang/String;)V");
	public static final MethodPermissionKey SET_PROPERTY = new MethodPermissionKey(
			"java/lang/System", "setProperty",
			"(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
	public static final MethodPermissionKey DATAGRAM_CONSTRUCTOR = new MethodPermissionKey(
			"java/net/DatagramSocket", "<init>", "(I)V");
	public static final MethodPermissionKey EXEC = new MethodPermissionKey(
			"java/lang/Runtime", "exec",
			"(Ljava/lang/String;)Ljava/lang/Process;");
	public static final MethodPermissionKey DEFINE_CLASS_1 = new MethodPermissionKey(
			"java/lang/ClassLoader", "defineClass", "([BII)Ljava/lang/Class;");
	public static final MethodPermissionKey DEFINE_CLASS_2 = new MethodPermissionKey(
			"java/lang/ClassLoader",
			"defineClass",
			"(Ljava/lang/String;Ljava/nio/ByteBuffer;Ljava/security/ProtectionDomain;)Ljava/lang/Class;");
	public static final MethodPermissionKey DEFINE_CLASS_3 = new MethodPermissionKey(
			"java/lang/ClassLoader", "defineClass",
			"(Ljava/lang/String;[BII)Ljava/lang/Class;");

	public MethodPermissionKey(String owner, String name, String description) {
		super();
		this.owner = owner;
		this.name = name;
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		MethodPermissionKey other = (MethodPermissionKey) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MethodPermissionKey [owner=" + owner + ", name=" + name
				+ ", description=" + description + "]";
	}

}
