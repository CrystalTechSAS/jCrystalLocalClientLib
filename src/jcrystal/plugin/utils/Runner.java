package jcrystal.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {

	File projectFolder;
	public Runner(File projectFolder) {
		this.projectFolder = projectFolder;
	}
	public void start() {
		try {
			String jcrsytalJarFile = new File(new File(projectFolder, ".jcrystal"), "jcrystal.jar").getAbsolutePath();
			
			List<String> classpath = Files.readAllLines(new File(projectFolder,".classpath").toPath());

			final String separator = File.pathSeparator;
			String libJars = classpath.stream().filter(l->l.contains("kind=\"lib\"")).map(f->f.substring(f.indexOf("path=\"")+"path=\"".length(),f.indexOf("\"", f.indexOf("path=\"")+"path=\"".length()+1))).collect(Collectors.joining(separator));
			
			String classesFolder = classpath.stream().filter(l->l.contains("kind=\"output\"")).findFirst().orElse(null).trim();
			classesFolder = classesFolder.substring(classesFolder.indexOf("path=\"")+"path=\"".length(),classesFolder.indexOf("\"", classesFolder.indexOf("path=\"")+"path=\"".length()+1));
			
			Process p = new ProcessBuilder("java","-cp","./"+classesFolder+separator+jcrsytalJarFile+separator+libJars,"jcrystal.EntryPoint").directory(projectFolder).start();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))){
				for(String l;(l=br.readLine())!=null;)
					System.out.println(l);
			}
			try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()))){
				for(String l;(l=br.readLine())!=null;)
					System.out.println(l);
			}
			p.waitFor();
			System.out.println("Completed");
		} catch (IOException e) {
			e.printStackTrace(System.out);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}	
	}
}
