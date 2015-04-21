package game_engine.sprite.utilitybuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * class to create/write to properties file
 * adapted from www.avajava.com
 */
public class WriteProperties {
	private Properties properties;
	private File file;
	
	public WriteProperties(String fileName){
		this.file = new File(fileName);
		properties = new Properties();
	}
	
	public void addProperty(String propertyName, String propertyValue){
		properties.setProperty(propertyName, propertyValue);
	}
	
	public void writeToFile() throws IOException{
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, "Parameters");
		fileOut.close();
	}

}
