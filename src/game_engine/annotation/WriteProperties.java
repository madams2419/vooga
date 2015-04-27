package game_engine.annotation;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * class to create/write to properties file
 * adapted from www.avajava.com
 * Call Sequence:
 * WriteProperties wp = new WriteProperties();
 * wp.addProperty("description", jump action);
 * wp.writeToFile("Actions.properties");
 */
public class WriteProperties {
	private Properties properties;
	
	public WriteProperties(){
		properties = new Properties();
	}
	
	/**
	 * adds property to be written to properties file
	 * @param propertyName
	 * @param propertyValue
	 */
	public void addProperty(String propertyName, String propertyValue){
		properties.setProperty(propertyName, propertyValue);
	}
	
	/**
	 * writes properties to specified file 
	 * @throws IOException
	 */
	public void writeToFile(String file) throws IOException{
		for(Object p:properties.keySet()){
			System.out.println(p.toString());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, "Parameters");
		fileOut.close();
	}

}
