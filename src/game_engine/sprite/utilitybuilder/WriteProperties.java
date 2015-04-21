package game_engine.sprite.utilitybuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

//http://www.avajava.com/tutorials/lessons/how-do-i-write-a-properties-file.html
public class WriteProperties {
	private Properties properties;
	public WriteProperties(String fileName){
		
	}
	
	public void start(){
		properties = new Properties();
	}
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("favoriteAnimal", "marmot");
			properties.setProperty("favoriteContinent", "Antarctica");
			properties.setProperty("favoritePerson", "Nicole");

			File file = new File("test2.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Favorite Things");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
