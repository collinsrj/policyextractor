package com.collir24.policyextractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] jars = new String[] {
				"/Users/rcollins/.m2/repository/asm/asm-commons/3.3/asm-commons-3.3.jar",
				"/Users/rcollins/.m2/repository/asm/asm-tree/3.3/asm-tree-3.3.jar",
				"/Users/rcollins/.m2/repository/asm/asm/3.3/asm-3.3.jar",
				"/Users/rcollins/.m2/repository/commons-fileupload/commons-fileupload/1.2.2/commons-fileupload-1.2.2.jar",
				"/Users/rcollins/.m2/repository/commons-io/commons-io/2.0.1/commons-io-2.0.1.jar",
				"/Users/rcollins/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar",
				"/Users/rcollins/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar",
				"/Users/rcollins/.m2/repository/javassist/javassist/3.11.0.GA/javassist-3.11.0.GA.jar",
				"/Users/rcollins/.m2/repository/javax/servlet/jsp-api/2.4/jsp-api-2.4.jar",
				"/Users/rcollins/.m2/repository/javax/servlet/servlet-api/2.4/servlet-api-2.4.jar",
				"/Users/rcollins/.m2/repository/ognl/ognl/3.0.3/ognl-3.0.3.jar",
				"/Users/rcollins/.m2/repository/org/apache/struts/struts2-config-browser-plugin/2.3.1/struts2-config-browser-plugin-2.3.1.jar",
				"/Users/rcollins/.m2/repository/org/apache/struts/struts2-core/2.3.1/struts2-core-2.3.1.jar",
				"/Users/rcollins/.m2/repository/org/apache/struts/xwork/xwork-core/2.3.1/xwork-core-2.3.1.jar",
				"/Users/rcollins/.m2/repository/org/freemarker/freemarker/2.3.18/freemarker-2.3.18.jar",
				"/Users/rcollins/.m2/repository/org/javassist/javassist/3.13.0-GA/javassist-3.13.0-GA.jar" };
		Extract extract = new Extract(jars);
		List<ModulePermissions> modulePermissions = new ArrayList<ModulePermissions>(
				jars.length);
		extract.examineFiles(modulePermissions);
		Set<String> permissionSet = new HashSet<String>();

		for (ModulePermissions mps : modulePermissions) {
			for (ModulePermission mp : mps.getPermissions()) {
				for (Permission p : mp.getList()) {
					String permissionKey = getPermissionKey(p);
					permissionSet.add(permissionKey);

					// <edge source="asm-commons-3.3.jar"
					// target="asm-tree-3.3.jar">
					StringBuilder sb = new StringBuilder();
					sb.append("<edge source=\"").append(mps.getModuleName())
							.append("\" target=\"").append(permissionKey)
							.append("\"/>").append("\n");
					// System.out.println(sb.toString());
				}
			}
		}
		for (String s : permissionSet) {
			// <node id="com.collir24:demo:war:1.0-SNAPSHOT"
			// label="com.collir24:demo:war:1.0-SNAPSHOT">
			// <attvalues><attvalue for="1" value="0.0.1-SNAPSHOT"/></attvalues>
			StringBuilder sb = new StringBuilder();
			sb.append("<node id=\"")
					.append(s)
					.append("\" label=\"")
					.append(s)
					.append("\">")
					.append("\n")
					.append("<attvalues><attvalue for=\"0\" value=\"perm\"/></attvalues>\n</node>");
			// System.out.println(sb.toString());
		}
	}

	/**
	 * Return a simple key for the permission.
	 * 
	 * @return
	 */
	private static String getPermissionKey(Permission permission) {
		StringBuilder keyBuilder = new StringBuilder();
		return keyBuilder.append(permission.getPermission()).append(".")
				.append(permission.getTarget()).toString();
	}

}
