package authoring.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * Provides a dialog for user to specify sequence of images
 * for animation.
 * @author Natalie, Andrew
 *
 */
public class AnimationsDialog extends DataDialog {

    private List<Button> myImageAdderButtons;
    private List<TextField> myTextFields;
    private List<String> myStates, myImageURLs;
    private List<ImageView> myImageViews;
    private Map<String, String> myAnimations;
    private Sprite mySprite;

    private static final String TITLE = "Animations of Character";
    private static final String ADD_IMAGE = "Set image for state";
    private static final String STATE = "State";
    private static final String IMAGE = "Image";
    private static final String PREVIEW = "Preview";

    private static final String IMAGE_CHOOSER_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg",
                                                              "*.gif" };

    public AnimationsDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(3, 1, 1,
            new Node[] { new Label(STATE), new Label(IMAGE) , new Label(PREVIEW)});
        setBottomSpacing(130);
        addAddButton();
        myImageURLs.add(mySprite.getImageURI());
        myImageViews.get(0).setImage(new Image(mySprite.getImageURI()));
    }

    private Button addImageButton (String label, int index) {
        return addButton(label, e -> selectImage(index), myImageAdderButtons);
    }

    private void selectImage (int index) {
        File selectedImageFile;
        if ((selectedImageFile =
                new FileChooserDialog(IMAGE_CHOOSER_DESCRIPTION, IMAGE_CHOOSER_EXTENSIONS)
                        .initialize()) != null) {
            myImageURLs.add(selectedImageFile.toURI().toString());
            myImageViews.get(index).setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    public Map<String, String> getAnimations () {
        return myAnimations;
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite... s) {
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
    
    private ImageView addImageView(){
      ImageView result = new ImageView();
      myImageViews.add(result);
      result.setPreserveRatio(true);
      result.setFitHeight(100);
      return result;
    }
    
    private void populateAnimationsMap () {
        myAnimations = new LinkedHashMap<>();
        for (int i = 0; i < myImageViews.size(); i++) {
            myAnimations.put(myStates.get(i), myImageURLs.get(i));
        }
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer... grid) {
        grid[0].addRowEnd(addTextField(myTextFields), addImageButton(ADD_IMAGE, index), addImageView());
    }

    void initializeEverything (Sprite sprite) {
        mySprite = sprite;
        myAnimations = new LinkedHashMap<>();
        myTextFields = new ArrayList<>();
        myImageAdderButtons = new ArrayList<>();
        myImageURLs = new ArrayList<>();
        myImageViews = new ArrayList<>();
    }

    public AnimationsDialog update () {
        myImageURLs.set(0, mySprite.getImageURI());
        return this;
    }

    @Override
    void addOtherComponents (DialogGridOrganizer... grid) {
        // don't add any other components
    }
    
    String getMyTitle () {
        return TITLE;
    }

}