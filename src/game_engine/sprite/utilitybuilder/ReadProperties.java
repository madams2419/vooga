package game_engine.sprite.utilitybuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * class to read properties file
 * adapted from www.avajava.com
 */
public class ReadProperties {
	private String fileName;
	private Map<Integer,Map<String,String>> properties = new HashMap<>();
	
	public ReadProperties(String fileName){
		this.fileName = fileName;
	}
	
	public Map<Integer,Map<String,String>> getPropertiesMap() throws IOException{
		processProperties();
		return properties;
	}
	
	private void processProperties() throws IOException{
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
	
	private void writeToMap(Integer id, String key, String value){
		Map<String,String> propMap = properties.getOrDefault(id, new HashMap<String,String>());
		propMap.put(key, value);
		properties.put(id, propMap);
	}
	
	public static void main(String[] args) {
		ReadProperties rp = new ReadProperties("Actions.properties");
		try {
			Map<Integer,Map<String,String>> p = rp.getPropertiesMap();
			for(Integer i:p.keySet()){
				System.out.println(i + " " + p.get(i) + " " + p.get(p.get(i)));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}