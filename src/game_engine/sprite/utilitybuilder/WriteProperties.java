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
		properties.store(fileOut, "Favorite Things");
		fileOut.close();
	}
//	
//	public static void main(String[] args) {
//		try {
//			Properties properties = new Properties();
//			properties.setProperty("favoriteAnimal", "marmot");
//			properties.setProperty("favoriteContinent", "Antarctica");
//			properties.setProperty("favoritePerson", "Nicole");
//
//			File file = new File("test2.properties");
//			FileOutputStream fileOut = new FileOutputStream(file);
//			properties.store(fileOut, "Favorite Things");
//			fileOut.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//	}
}
