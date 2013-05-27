package com.collir24.policyextractor.modulepermission;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.collir24.policyextractor.MethodPermissionKey;
import com.collir24.policyextractor.ModulePermission;
import com.collir24.policyextractor.Permission;

public class TestDefineClassInPackagePermission {

	private static final List<Permission> PERMISSIONS = Arrays
			.asList(new Permission[] { new Permission(
					"java.lang.RuntimePermission",
					"defineClassInPackage.{packageName}") });

	@Test
	public void testSimplePackageName() {
		Set<String> expectedPolicy = new HashSet<String>(1);
		expectedPolicy
				.add("permission java.lang.RuntimePermission \"defineClassInPackage.com.collir24.test\";");
		ModulePermission mp = new DefineClassInPackagePermission(PERMISSIONS,
				"com.collir24.test.TestClass",
				MethodPermissionKey.DEFINE_CLASS_1, 100,
				"com.collir24.test.NewClass");
		assertEquals(expectedPolicy, mp.getPolicy());
	}

	@Test
	public void testNoPackageName() {
		Set<String> expectedPolicy = new HashSet<String>(1);
		expectedPolicy
				.add("permission java.lang.RuntimePermission \"defineClassInPackage.{packageName}\";");
		ModulePermission mp = new DefineClassInPackagePermission(PERMISSIONS,
				"TestClass", MethodPermissionKey.DEFINE_CLASS_1, 100,
				"NewClass");
		assertEquals(expectedPolicy, mp.getPolicy());
	}
}
