package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {
	
	private Properties properties;

	private final String propertyFileName = returnPropertyFilePath();
	
	
	
	public PropertyFile() {		
		validateFileExists();
		loadPropertyContent();
	}

	private String returnPropertyFilePath(){
		File directory = new File("src/main/resources/global.properties");

		return directory.getAbsolutePath();
		

	}
	private void validateFileExists() {
		File propertyFile = new File(propertyFileName);
		if (!propertyFile.exists())
			throw new RuntimeException("property file does not exist");
	}

	private void loadPropertyContent() {
		
		properties = new Properties();
		File file = new File(propertyFileName);
		InputStream input;
		
		try {
			input = new FileInputStream(file);
			properties.load(input);
			return;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		throw new RuntimeException("could not load the property file content");
	}
	
	public String get(String name)
	{		
		try {
			return properties.getProperty(name);
		}
		catch(Exception exception) {
			throw new RuntimeException ("cannot find the " + name);
		}		 
	}	
	
}
