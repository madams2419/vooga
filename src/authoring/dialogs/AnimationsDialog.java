package authoring.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class AnimationsDialog extends DataDialog {

    private List<Button> myImageAdderButtons;
    private List<Label> myStateLabels, myShortImageURLs, myCompleteImageURLs;
    private ObservableList<String> myStates;
    private Map<String, String> myAnimations;
    private Sprite mySprite;

    private static final String IMAGE_FILE_URL = "Image File URL";
    private static final String ADD_IMAGE = "Set image for state";
    private static final String STATE = "State";
    private static final String IMAGE = "Image";

    private static final String IMAGE_CHOOSER_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg",
                                                              "*.gif" };

    public AnimationsDialog (Sprite sprite, ObservableList<String> states) {
        initializeEverything(states, sprite);
        initialize(sprite, 3,
                   new Node[] { new Label(STATE), new Label(IMAGE), new Label(IMAGE_FILE_URL) });
    }

    private Button addImageButton (String label, int index) {
        Button button = new Button(label);
        myImageAdderButtons.add(button);
        button.setOnKeyPressed(e -> selectImage(index));
        return button;
    }
    
    @Override
    void addAddButton() {
        //don't put an Add button
    }

    private Label addImageURL () {
        Label label = new Label("");
        myCompleteImageURLs.add(label);
        Label shortLabel = shortenLabel(label);
        myShortImageURLs.add(shortLabel);
        return shortLabel;
    }

    private Label shortenLabel (Label label) {
        Label shortLabel = new Label(shortenURL(label.getText()));
        return shortLabel;
    }

    private String shortenURL (String text) {
        String[] array = text.split("/");
        String ret = array[array.length - 1];
        System.out.println(ret);
        return ret;
    }

    private void selectImage (int index) {
        File selectedImageFile;
        if ((selectedImageFile =
                new FileChooserDialog(IMAGE_CHOOSER_DESCRIPTION, IMAGE_CHOOSER_EXTENSIONS)
                        .initialize()) != null) {
            myShortImageURLs.get(index).setText(shortenURL(selectedImageFile.toURI().toString()));
            myCompleteImageURLs.get(index).setText(selectedImageFile.toURI().toString());
        }
    }

    public Map<String, String> getAnimations () {
        return myAnimations;
    }

    @Override
    Consumer<ButtonType> getTodoOnOK () {
        return (response -> {
            myAnimations = new HashMap<>();
            for (int i = 0; i < myImageAdderButtons.size(); i++) {
                myAnimations.put(myStates.get(i),
                                 myShortImageURLs.get(i).getText());
            }
            mySprite.setAnimations(myAnimations);
        });
    }

    @Override
    ObservableList<String> getListContent () {
        return myStates;
    }

    public void setStates (ObservableList<String> states) {
        myStates = states;
    }

    private Label addLabel (int index) {
        Label label = new Label(myStates.get(index));
        myStateLabels.add(label);
        return label;
    }

    @Override
    void addBlankRow (DialogGridOrganizer grid, int index) {
        grid.addRowEnd(addLabel(index), addImageButton(ADD_IMAGE, index), addImageURL());
    }

    void initializeEverything (ObservableList<String> states, Sprite sprite) {
        myStates = states;
        mySprite = sprite;
        myStateLabels = new ArrayList<>();
        myImageAdderButtons = new ArrayList<>();
        myShortImageURLs = new ArrayList<>();
        myCompleteImageURLs = new ArrayList<>();
    }

    public AnimationsDialog update (ObservableList<String> newStates) {
        myStates = newStates;
        int goalSize = myStates.size();

        while (myImageAdderButtons.size() < goalSize) {
            System.out.println("current size: " + myImageAdderButtons.size());
            int i = myImageAdderButtons.size();
            addRow(i);
        }

        for (int i = 0; i < goalSize; i++) {
            myStateLabels.get(i).setText(myStates.get(i));
        }

        return this;
    }
}
