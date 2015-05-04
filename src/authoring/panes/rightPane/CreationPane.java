package authoring.panes.rightPane;

import java.io.File;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import authoring.dataEditors.Sprite;
import authoring.userInterface.SpriteCursor;
import authoring.util.FrontEndUtils;
import authoring.util.GUIElementCreator;
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
    private static final String ADD_NEW_SPRITE_LABEL = "+";
    private static final String IMAGE_CHOOSER_TITLE = "Add New Image";
    private static final String IMAGE_CHOOSER_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg",
                                                            "*.gif" };
    private static int ID; //we have a static instance variable because we only want to create this once.
    private List<String> myAvailableTypeURIs;
    private VBox mySpriteHolder;
    
    CreationPane (Scene scene, RightPane parent, List<String> availableTypeURIs) {
        super(scene, parent);
        createComponents(availableTypeURIs);
    }

    private void createComponents (List<String> availableTypeURIs) {
        GUIElementCreator.createLabel(HELP_LABEL_CONTENT, this);
        addSpritesToPane();
        GUIElementCreator.createButton(ADD_NEW_SPRITE_LABEL, i -> addNewCreatable(), this);
        myAvailableTypeURIs = availableTypeURIs;
    }

    private void addNewCreatable() {
        File selectedImageFile =
                FrontEndUtils.selectFile(IMAGE_CHOOSER_TITLE, IMAGE_CHOOSER_DESCRIPTION, 
                                         IMAGE_CHOOSER_EXTENSIONS);
        
        if (selectedImageFile != null) {
            // not sure if "file://" is the right beginning for all computers
            String path = "file://" + selectedImageFile.getAbsolutePath();
            myAvailableTypeURIs.add(path);
            addSpriteToPane(myAvailableTypeURIs.size(), path, mySpriteHolder);
        }
    }
    
    private void addSpritesToPane () {
        mySpriteHolder = new VBox(20);
        ScrollPane s = new ScrollPane(mySpriteHolder);
        this.getChildren().add(s);
        for (int i = 0; i < myAvailableTypeURIs.size(); i++) {
            addSpriteToPane(i, myAvailableTypeURIs.get(i), mySpriteHolder);
        }
    }

    private void addSpriteToPane (int id, String imageURI, VBox v) {
        Sprite sampleImage = new Sprite(id, imageURI, myParent.getParent()
                .getCenterPane());

        ImageView sampleImageIcon = sampleImage.getIcon();
        sampleImageIcon.setOnMouseClicked(e -> imageClicked(sampleImage));
        sampleImageIcon.setOnMouseEntered(i -> ImageEditor
                .reduceOpacity(sampleImageIcon, Sprite.OPACITY_REDUCTION_RATIO));
        sampleImageIcon.setOnMouseExited(i -> ImageEditor
                .restoreOpacity(sampleImageIcon));
        v.getChildren().add(sampleImageIcon);
    }

    private void imageClicked (Sprite sampleImage) {
        getMyScene().setCursor(
                               new SpriteCursor(new Sprite(sampleImage, ID++, myParent
                                       .getParent().getCenterPane())));

    }

    public static int incrementID () {
        return ID++;
    }
}
