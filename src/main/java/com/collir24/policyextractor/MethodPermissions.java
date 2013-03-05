package com.collir24.policyextractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodPermissions {

	private final Map<String, List<Permission>> methodPermissions;
	private static final String INIT_FILE_PATH = "MethodPermissions.txt";

	public MethodPermissions() {
		InputStream is = null;
		methodPermissions = new HashMap<String, List<Permission>>();
		StringBuilder sb = new StringBuilder();
		try {
			is = MethodPermissions.class.getResourceAsStream(INIT_FILE_PATH);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			while (reader.ready()) {
				String line = reader.readLine();
				String[] values = line.split("\t");
				sb.setLength(0);
				String key = sb.append(values[0]).append(":").append(values[1])
						.append(":").append(values[2]).toString();
				List<Permission> permList = methodPermissions.get(key); 
				if (permList == null) {
					permList = new ArrayList<Permission>();
				}
				switch (values.length) {
				case 5:					
					permList.add(new Permission(values[3],
							values[4]));					
					break;
				case 6:
					permList.add(new Permission(values[3],
							values[4], values[5]));					
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

	protected boolean contains(String key) {
		return methodPermissions.containsKey(key);
	}

	public List<Permission> get(String key) {
		return methodPermissions.get(key);
	}
}
