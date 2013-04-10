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
			"java/io/FileOutputStream","<init>","(Ljava/lang/String;)V");
	public static final MethodPermissionKey FILE_OUTPUT_STREAM_APPEND_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/FileOutputStream","<init>","(Ljava/lang/String;Z)V");
	public static final MethodPermissionKey RANDOM_ACCESS_FILE_STRING_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/RandomAccessFile","<init>","(Ljava/lang/String;Ljava/lang/String;)V");
	public static final MethodPermissionKey RANDOM_ACCESS_FILE_CONSTRUCTOR = new MethodPermissionKey(
			"java/io/RandomAccessFile","<init>","(Ljava/io/File;Ljava/lang/String;)V");
	public static final MethodPermissionKey GET_DECLARED_FIELD = new MethodPermissionKey(
			"java/lang/Class","getDeclaredField","(Ljava/lang/String;)Ljava/lang/reflect/Field;");

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
