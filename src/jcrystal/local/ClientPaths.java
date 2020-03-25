package jcrystal.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ClientPaths {
	private static ClientPaths instance = new ClientPaths();
	
	public static ClientPaths getConfig(File projectFolder) {
		if(projectFolder != null) {
			if(instance.reload(projectFolder)) {
				return instance;
			}
		}
		return null;
	}
	
	
	private Properties props = new Properties();
	private File projectFolder;
	private ClientPaths() {
		new Properties();
	}
	public boolean reload(File projectFolder) {
		props.clear();
		this.projectFolder = projectFolder;
		if(!new File(projectFolder,".jcrystal").exists())
			return false;
		try(FileInputStream fis = new FileInputStream(new File(projectFolder,".jcrystal/locals.txt"))){
			props.load(fis);			
		} 
		catch (Exception e) {}
		return true;
	}
	public void save() {
		File root = projectFolder;
		try(FileOutputStream fos = new FileOutputStream(new File(root,".jcrystal/locals.txt"))){
			props.store(fos, null);
		}catch (Exception e) {
		}
	}
	public String getClientPath(String id) {
		return props.getProperty(id+".path");
	}
	public void setClientPath(String id, String path) {
		props.setProperty(id+".path", path);
	}
	public File getProjectFolder() {
		return projectFolder;
	}
}
