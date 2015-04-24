package authoring.dialogs;

import java.io.File;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class FileChooserDialog {

    private FileChooser myFileChooser;

    public FileChooserDialog (ExtensionFilter ... filters) {
        createFileChooser();
        addExtensionFilters(filters);
    }

    public FileChooserDialog (String description, String[] extensions) {
        createFileChooser();
        addExtensionFilters(description, extensions);
    }
    
    private void createFileChooser () {
        myFileChooser = new FileChooser();
        myFileChooser.setTitle("Open Resource File");
    }
    
    private void addExtensionFilters(ExtensionFilter ... filters) {
        myFileChooser.getExtensionFilters().addAll(filters);
    }

    private void addExtensionFilters(String imageChooserDescription, String[] imageChooserExtensions) {
        myFileChooser.getExtensionFilters().add(new ExtensionFilter(imageChooserDescription, imageChooserExtensions));
    }
    
    public File initialize () {
        return myFileChooser.showOpenDialog(null);
    }

    public Image grabImage () {
        return new Image("file:///" + initialize().getPath());
    }
}
