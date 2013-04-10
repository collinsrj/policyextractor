package com.collir24.policyextractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MethodPermissions {

	private final Map<MethodPermissionKey, List<Permission>> methodPermissions;
	private static final String INIT_FILE_PATH = "MethodPermissions.txt";

	public MethodPermissions() {
		InputStream is = null;
		methodPermissions = new HashMap<MethodPermissionKey, List<Permission>>();
		try {
			is = MethodPermissions.class.getResourceAsStream(INIT_FILE_PATH);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			while (reader.ready()) {
				String line = reader.readLine();
				String[] values = line.split("\t");
				MethodPermissionKey key = new MethodPermissionKey(values[0],
						values[1], values[2]);
				List<Permission> permList = methodPermissions.get(key);
				if (permList == null) {
					permList = new ArrayList<Permission>();
				}
				switch (values.length) {
				case 5:
					permList.add(new Permission(values[3], values[4]));
					break;
				case 6:
					permList.add(new Permission(values[3], values[4], values[5]));
					break;
				default:
					throw new IllegalStateException(
							"The method permissions file was not formatted correctly. See: "
									+ INIT_FILE_PATH + ". The line was: "
									+ line + ". The length was: "
									+ values.length);
				}
				methodPermissions.put(key, permList);
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to process input file at: "
					+ INIT_FILE_PATH, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(
							"Unable to close init file stream at: "
									+ INIT_FILE_PATH, e);
				}
			}
		}
	}

	protected int size() {
		return methodPermissions.size();

	}

	protected boolean contains(MethodPermissionKey key) {
		return methodPermissions.containsKey(key);
	}

	public List<Permission> get(MethodPermissionKey key) {
		return methodPermissions.get(key);
	}

	/**
	 * 
	 * @return
	 */
	public Set<Permission> getAllPermissions() {
		Set<Permission> perms = new HashSet<Permission>();
		for (List<Permission> permissionList : methodPermissions.values()) {
			perms.addAll(permissionList);
		}
		return perms;
	}
}
