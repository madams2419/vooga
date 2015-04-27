package game_engine.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * class to read properties file and return map of integer id to map of key, value pairs
 * adapted from www.avajava.com
 * @author Emre Sonmez
 */
public class ReadProperties {
	private Map<Integer,Map<String,String>> properties;
	
	public ReadProperties(){
		properties = new HashMap<>();
	}
	
	/**
	 * calls process properties and returns map of id to map of key/value pairs
	 * @return
	 * @throws IOException
	 */
	public Map<Integer,Map<String,String>> getPropertiesMap(String fileName) throws IOException{
		processProperties(fileName);
		return properties;
	}
	
	/**
	 * uses file name to parse properties file strings and populate map
	 * @throws IOException
	 */
	private void processProperties(String fileName) throws IOException{
		File file = new File(fileName);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		
		Enumeration enuKeys = properties.keys();
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String[] splitString = key.split("_");
			String id = splitString[0];
			String keyName = splitString[2];
			String value = properties.getProperty(key);
			System.out.println(id + ", " + keyName + ", " + value);
			writeToMap(Integer.parseInt(id),keyName,value);
		}
	}
	
	/**
	 * helper method to populate properties map with (ID -> (tag,value))
	 * @param id
	 * @param key
	 * @param value
	 */
	private void writeToMap(Integer id, String key, String value){
		Map<String,String> propMap = properties.getOrDefault(id, new HashMap<String,String>());
		propMap.put(key, value);
		properties.put(id, propMap);
	}
	
	public static void main(String[] args) {
		ReadProperties rp = new ReadProperties();
		try {
			Map<Integer,Map<String,String>> p = rp.getPropertiesMap("Actions.properties");
			for(Integer i:p.keySet()){
				System.out.println(i + " " + p.get(i) + " " + p.get(p.get(i)));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}