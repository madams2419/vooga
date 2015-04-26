package authoring.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class AnimationsDialog extends DataDialog {

    private List<Button> myImageAdderButtons;
    private List<TextField> myTextFields;
    private List<String> myStates;
    private List<String> myImageURLs;
    private Map<String, String> myAnimations;
    private Sprite mySprite;

    private static final String ADD_IMAGE = "Set image for state";
    private static final String STATE = "State";
    private static final String IMAGE = "Image";

    private static final String IMAGE_CHOOSER_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg",
                                                              "*.gif" };
    public static AnimationsDialog defaultAnimations(Sprite s){
    	return new AnimationsDialog(s);
    }
    
    public AnimationsDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(sprite, 3,
                   new Node[] { new Label(STATE), new Label(IMAGE) }, 1);
    }

    private Button addImageButton (String label, int index) {
        return addButton(label, e -> selectImage(index), myImageAdderButtons);
    }

    private void selectImage (int index) {
        File selectedImageFile;
        if ((selectedImageFile =
                new FileChooserDialog(IMAGE_CHOOSER_DESCRIPTION, IMAGE_CHOOSER_EXTENSIONS)
                        .initialize()) != null) {
            myImageURLs.set(index, selectedImageFile.toURI().toString());
        }
    }

    public Map<String, String> getAnimations () {
        return myAnimations;
    }

    @Override
    Consumer<ButtonType> getTodoOnOK () {
        return (response -> {
            populateStates();
            populateAnimationsMap();
            changeSpriteImage();
        });
    }

    private void changeSpriteImage () {
        String image = myImageURLs.get(0);
        if (image.length() > 0) {
            mySprite.changeImage(image);
        }
    }

    private void populateStates () {
        myStates = new ArrayList<>();
        for (TextField field : myTextFields) {
            myStates.add(field.getText());
        }
    }

    private void populateAnimationsMap () {
        myAnimations = new HashMap<>();
        for (int i = 0; i < myImageAdderButtons.size(); i++) {
            myAnimations.put(myStates.get(i), myImageURLs.get(i));
        }
    }

    @Override
    void addBlankRow (DialogGridOrganizer grid, int index) {
        grid.addRowEnd(addTextField(myTextFields), addImageButton(ADD_IMAGE, index));
        myImageURLs.add(mySprite.getImageURI());
    }

    void initializeEverything (Sprite sprite) {
        mySprite = sprite;
        myTextFields = new ArrayList<>();
        myImageAdderButtons = new ArrayList<>();
        myImageURLs = new ArrayList<>();
    }

    public AnimationsDialog update () {
        myImageURLs.set(0, mySprite.getImageURI());
        return this;
    }

    @Override
    void addOtherComponents (DialogGridOrganizer grid) {
        // don't add any other components
    }

}
