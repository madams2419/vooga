package game_engine.sprite.utilitybuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * class to read properties file
 * adapted from www.avajava.com
 * to be continued
 */
public class ReadProperties {
	private String fileName;
	private HashMap<Integer,String[]> properties = new HashMap<>();
	
	public ReadProperties(String fileName){
		this.fileName = fileName;
	}
	
	public ArrayList<String[]> getProperties() throws IOException{
		File file = new File(fileName);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		
		Enumeration enuKeys = properties.keys();
		while (enuKeys.hasMoreElements()) {
			String[] parameters = new String[3];
			String key = (String) enuKeys.nextElement();
			int id = Integer.parseInt(key.split("_")[1]);
			String type = key.split("_")[2];
			// refactor this
			
			String value = properties.getProperty(key);
			System.out.println(key + ": " + value);
		}
		
		return null;
		
	}
	public static void main(String[] args) {
		try {
			File file = new File("test.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}