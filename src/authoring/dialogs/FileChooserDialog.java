package authoring.dialogs;

import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChooserDialog{
	
	private FileChooser myFileChooser;
	
	public FileChooserDialog(ExtensionFilter... filters){
		myFileChooser = new FileChooser();
		myFileChooser.setTitle("Open Resource File");
		myFileChooser.getExtensionFilters().addAll(filters);
	}
	
	public File initialize(){
		return myFileChooser.showOpenDialog(null);
	}
	
	public Image grabImage(){
        return new Image("file:///" + initialize().getPath());
	}
}
