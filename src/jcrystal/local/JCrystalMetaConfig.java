package jcrystal.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import jcrystal.plugin.utils.RunType;

public class JCrystalMetaConfig {

	private final static String META_FOLDER = ".jcrystal";
	private final static String PROJECT_PROPS_NAME = "jcrystal.txt";
	private final static String RUN_PROPS_NAME = "run.txt";
	
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
	private Properties runProperties = new Properties();
	private File projectFolder;
	private JCrystalMetaConfig() {
		new Properties();
	}
	public boolean reload(File projectFolder) {
		props.clear();
		runProperties.clear();
		this.projectFolder = projectFolder;
		if(!new File(projectFolder,META_FOLDER).exists())
			return false;
		try(FileInputStream fis = new FileInputStream(new File(projectFolder, META_FOLDER +  "/" + PROJECT_PROPS_NAME))){
			props.load(fis);			
		} 
		catch (Exception e) {}
		
		try(FileInputStream fis = new FileInputStream(new File(projectFolder, META_FOLDER +  "/" + RUN_PROPS_NAME))){
			runProperties.load(fis);			
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
		try(FileOutputStream fos = new FileOutputStream(new File(root, META_FOLDER +  "/" + PROJECT_PROPS_NAME))){
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
	
	public void saveRunProperties() {
		File root = projectFolder;
		try(FileOutputStream fos = new FileOutputStream(new File(root, META_FOLDER +  "/" + RUN_PROPS_NAME))){
			runProperties.store(fos, null);
		}catch (Exception e) {
		}
	}
	
	public boolean getDeleteOnRun() {
		return Boolean.valueOf(runProperties.getProperty("deleteFiles", "false"));
	}
	public JCrystalMetaConfig setDeleteOnRun(boolean delete) {
		runProperties.setProperty("deleteFiles",Boolean.toString(delete));
		return this;
	}
	public void deleteRunProperties() {
		File runFile = new File(projectFolder,META_FOLDER +  "/" + RUN_PROPS_NAME);
		if (runFile.exists()) {
			runFile.delete();
		}
	}
}
