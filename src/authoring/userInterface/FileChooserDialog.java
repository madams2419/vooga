package authoring.userInterface;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChooserDialog {

	public FileChooserDialog(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Image Files", "*.png",
						"*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav",
						"*.mp3", "*.aac"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.showOpenDialog(null);
	}
}
