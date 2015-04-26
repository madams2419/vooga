package authoring.panes.rightPane;

import java.io.File;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import authoring.dataEditors.Sprite;
import authoring.userInterface.SpriteCursor;
import authoring.util.FrontEndUtils;
import authoring.util.ImageEditor;


/**
 * This will allow the user to select from a number of images (or insert his/her
 * own image) to create something that can be placed on the canvas.
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */

public class CreationPane extends EditingPane {
    private static final String HELP_LABEL_CONTENT = "Click on one of the images to select it.";
    private List<String> myAvailableTypeURIs;
    private VBox myVBox;
    private static final String buttonLabel = "+";
    private static final String imageChooserTitle = "Add New Image";
    private static final String imageChooserDescription = "Image Files";
    private static final String[] imageChooserExtensions = { "*.png", "*.jpg",
                                                            "*.gif" };
    private static int ID; //we have a static instance variable because we only want to create this once.
    
    CreationPane (Scene scene, RightPane parent, List<String> availableTypeURIs) {
        super(scene, parent);
        addHelpLabel();
        myAvailableTypeURIs = availableTypeURIs;
        addSpritesToPane();
        addButtonToPane(buttonLabel);
    }

    private void addHelpLabel () {
        Label label = new Label(HELP_LABEL_CONTENT);
        getChildren().add(label);
    }

    private void addButtonToPane (String buttonLabel) {
        Button b = new Button(buttonLabel);
        b.setOnAction(i -> addNewCreatable());
        getChildren().add(b);
    }
    
    private void addNewCreatable() {
        File selectedImageFile =
                FrontEndUtils.selectFile(imageChooserTitle, imageChooserDescription, imageChooserExtensions);
        
        if (selectedImageFile != null) {
            // not sure if "file://" is the right beginning for all computers
            String path = "file://" + selectedImageFile.getAbsolutePath();
            myAvailableTypeURIs.add(path);
            addSpriteToPane(myAvailableTypeURIs.size(), path, myVBox);
        }
    }
    
    private void addSpritesToPane () {
        myVBox = new VBox(20);
        ScrollPane s = new ScrollPane(myVBox);
        this.getChildren().add(s);
        for (int i = 0; i < myAvailableTypeURIs.size(); i++) {
            addSpriteToPane(i, myAvailableTypeURIs.get(i), myVBox);
        }
    }

    private void addSpriteToPane (int id, String imageURI, VBox v) {
        Sprite sampleImage = new Sprite(id, imageURI, myParent.getParent()
                .getCenterPane());

        int ID = 100; // for now, it doesn't change, but this should eventually
                      // be unique for each sprite

        ImageView sampleImageIcon = sampleImage.getIcon();
        sampleImageIcon.setOnMouseClicked(e -> imageClicked(sampleImage, ID));
        sampleImageIcon.setOnMouseDragged(e -> imageDragged(e));
        sampleImageIcon.setOnMouseEntered(i -> ImageEditor
                .reduceOpacity(sampleImageIcon, Sprite.OPACITY_REDUCTION_RATIO));
        sampleImageIcon.setOnMouseExited(i -> ImageEditor
                .restoreOpacity(sampleImageIcon));
        v.getChildren().add(sampleImageIcon);
    }

    private void imageDragged (MouseEvent e) {

    }

    // an image in the right pane is clicked to be moved to the center pane
    private void imageClicked (Sprite sampleImage, int id) {
        // need to now set mouse cursor to the sprite image
        // getMyScene().setCursor(new SpriteCursor(new Sprite(sampleImage, ID,
        // spriteClicked)));
        getMyScene().setCursor(
                               new SpriteCursor(new Sprite(sampleImage, ID++, myParent
                                       .getParent().getCenterPane())));

    }

    public static int incrementID () {
        return ID++;
    }
}
