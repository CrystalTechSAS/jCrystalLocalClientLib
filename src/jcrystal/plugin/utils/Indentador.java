package jcrystal.plugin.utils;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Indentador {
	public static void main(String[] args) throws Exception{
		 R(new File("/Users/gasotelo/Documents/workspace/jCrystal/src/"));
		 R(new File("/Users/gasotelo/Documents/workspace/jCrystalUtils/src"));
	}
	static void R(File f) throws Exception{
		if(f.isDirectory()) {
			for(File x : f.listFiles())R(x);;
		}else indentar(f);
	}
	static final String WHITESPACES = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
	public static void indentar(File f) throws Exception{
		if(f.getName().endsWith(".java")) {
			System.out.println(f.getAbsolutePath());
			List<String> lines = Files.readAllLines(f.toPath()).stream().map(l->l.trim()).collect(Collectors.toList());
			int level = 0;
			String last = "";
			List<String> nuevos = new ArrayList<>();
			
			int currentLine = 0;
			try {
				for(String line: lines) {
					String copy = line;
					while(copy.contains("\"")) {
						int s = copy.indexOf('"'), e = s+1;
						for(;e < copy.length();e++) {
							if(copy.charAt(e-1)!='\\' && copy.charAt(e)=='"')
								break;
						}
						copy = copy.substring(0, s)+copy.substring(e+1);
					}
					if(line.startsWith("}")) {
						level -= copy.length()-copy.replace("}", "").length();
						nuevos.add(WHITESPACES.substring(0, level)+line);
						level += copy.length()-copy.replace("{", "").length();
					}else {
						if((last.startsWith("if") && !last.endsWith("{")) || (last.startsWith("else if") && !last.endsWith("{"))  || last.endsWith("else"))
							nuevos.add(WHITESPACES.substring(0, 1+level)+line);
						else
							nuevos.add(WHITESPACES.substring(0, level)+line);
						level -= copy.length()-copy.replace("}", "").length();	
						level += copy.length()-copy.replace("{", "").length();
					}
					last = line;
					currentLine++;
				}
				Files.write(f.toPath(), nuevos);
			}catch (Exception ex) {
				System.err.println("On line "+ currentLine);
				ex.printStackTrace();
			}
			
		}
	}
}
