/* Copyright (C) Germán Augusto Sotelo Arévalo - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Germán Augusto Sotelo Arévalo <gasotelo@crystaltech.co>, December 2018
 */
package jcrystal.local;

import java.io.File;
import java.util.Arrays;

public class LocalPaths {
	public static File ROOT = new File(".");
	private static File srcFile;
	private static File srcUtils;
	private static File webSrcFile;
	public static void setROOT(File rOOT) {
		ROOT = rOOT;
		srcFile = getExistingFolder("src/main/java","src/java","src");
		srcUtils = new File(srcFile.getParentFile(), "utils");
		webSrcFile = getExistingFolderNull("src/main/webapp","WebContent","war","web");
	}
	public static File getSrcFile() {
		if(srcFile==null)
			srcFile = getExistingFolder("src/main/java","src/java","src");
		return srcFile;
	}
	public static File getSrcUtils() {
		if(srcUtils==null)
			srcUtils = new File(srcFile.getParentFile(), "utils");
		return srcUtils;
	}
	public static File getWebSrcfile() {
		if(webSrcFile==null)
			webSrcFile = getExistingFolderNull("src/main/webapp","WebContent","war","web");
		return webSrcFile;
	}
	private static File getExistingFolder(String...files) {
		for(String f : files)if(new File(ROOT,f).exists())return new File(ROOT, f).getAbsoluteFile();
		throw new NullPointerException("Folder not found "+ Arrays.toString(files));
	}
	private static File getExistingFolderNull(String...files) {
		for(String f : files)if(new File(ROOT,f).exists())return new File(ROOT,f).getAbsoluteFile();
		return null;
	}
}
