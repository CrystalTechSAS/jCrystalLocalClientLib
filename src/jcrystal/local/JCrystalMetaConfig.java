package jcrystal.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import jcrystal.plugin.utils.RunType;

public class JCrystalMetaConfig {

	private static JCrystalMetaConfig instance = new JCrystalMetaConfig();
	
	public static JCrystalMetaConfig getConfigFor(File projectFolder) {
		if(projectFolder != null) {
			if(instance.reload(projectFolder)) {
				return instance;
			}
		}
		return null;
	}
	
	
	private Properties props = new Properties();
	private File projectFolder;
	private JCrystalMetaConfig() {
		new Properties();
	}
	public boolean reload(File projectFolder) {
		props.clear();
		this.projectFolder = projectFolder;
		if(!new File(projectFolder,".jcrystal").exists())
			return false;
		try(FileInputStream fis = new FileInputStream(new File(projectFolder,".jcrystal/jcrystal.txt"))){
			props.load(fis);			
		} 
		catch (Exception e) {}
		
		if(!props.containsKey("runType")) {
			if(new File(projectFolder, "pom.xml").exists()||new File(projectFolder, "pom.XML").exists()||new File(projectFolder, "POM.XML").exists()||new File(projectFolder, "POM.xml").exists()) {
				props.setProperty("runType", "POM");
			}else {
				props.setProperty("runType", "JAR");
			}
		}
		return true;
	}
	public void save() {
		File root = projectFolder;
		try(FileOutputStream fos = new FileOutputStream(new File(root,".jcrystal/jcrystal.txt"))){
			props.store(fos, null);
		}catch (Exception e) {
		}
	}
	public RunType getRunType() {
		return RunType.valueOf(props.getProperty("runType"));
	}
	public String getDevKey() {
		return props.getProperty("devKey");
	}
	public JCrystalMetaConfig setDevKey(String key) {
		props.setProperty("devKey",key);
		return this;
	}
	public File getProjectFolder() {
		return projectFolder;
	}
}
