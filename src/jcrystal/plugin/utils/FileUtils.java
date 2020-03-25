package jcrystal.plugin.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class FileUtils {
	public static File getExistingFolder(File projectFolder, String...files) {
		for(String f : files)if(new File(projectFolder, f).exists())return new File(projectFolder, f);
		throw new NullPointerException("Folder not found "+ Arrays.toString(files));
	}
	public static void copy(int bufferSize, InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[bufferSize];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
			output.flush();
		} finally {
			input.close();
			output.close();
		}
	}
}
