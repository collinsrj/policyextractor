package com.collir24.policyextractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class Tester {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		PrintStream ps = new PrintStream("/Users/rcollins/Desktop/junk.txt");
		System.setOut(ps);
		Collection<File> files = FileUtils.listFiles(new File(
				"/Users/rcollins/.m2/"), FileFilterUtils
				.suffixFileFilter("jar"), TrueFileFilter.INSTANCE);

		List<String> filePaths = new ArrayList<String>();
		for (File f : files) {
			filePaths.add(f.getAbsolutePath());
		}
		String[] filePathArray = filePaths.toArray(new String[files.size()]);
		System.out.println(filePathArray[0]);
		System.out.println(filePathArray[1]);
		System.out.println(filePathArray[2]);
		Extract e = new Extract(filePathArray);
		e.examineFiles();
	}

}
