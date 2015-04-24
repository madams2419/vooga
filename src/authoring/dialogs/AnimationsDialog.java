package authoring.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class AnimationsDialog extends DataDialog {

    private static final String IMAGE_FILE_URL = "Image File URL";
    private List<ComboBox<String>> myComboBoxes;
    private List<Button> myImageAdderButtons;
    private List<Label> myShortImageURLs;
    private List<Label> myCompleteImageURLs;
    private ObservableList<String> myStates;
    // private List<TextField> myTextFields;
    private Map<String, String> myAnimations;
    private Sprite mySprite;

    private static final String ADD_IMAGE = "Set image for state";
    private static final String STATE = "State";
    private static final String IMAGE = "Image";

    private static final String IMAGE_CHOOSER_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg",
                                                            "*.gif" };

    public AnimationsDialog (Sprite sprite, ObservableList<String> states) {
        super(sprite, 3,
              new Node[] { new Label(STATE), new Label(IMAGE), new Label(IMAGE_FILE_URL) }, states);

        myStates = states;
        mySprite = sprite;
        
    }

    private Button addImageButton (String label) {
        Button button = new Button(label);
        myImageAdderButtons.add(button);
        button.setOnKeyPressed(e -> selectImage(myComboBoxes.size() - 1));
        return button;
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
        if ((selectedImageFile = new FileChooserDialog(IMAGE_CHOOSER_DESCRIPTION, IMAGE_CHOOSER_EXTENSIONS).initialize()) != null) {
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
                myAnimations.put(myComboBoxes.get(i).getValue(),
                                 myShortImageURLs.get(i).getText());
            }
            mySprite.setAnimations(myAnimations);
        });
    }

    @Override
    ObservableList<String> getComboBoxContent () {
        return myStates;
    }
    
    public ObservableList<String> getStates () {
        return myStates;
    }

    public void setStates (ObservableList<String> states) {
        myStates = states;
    }
    
    @Override
    void addRow (DialogGridOrganizer grid, ObservableList<String> comboBoxContent) {
        grid.addRowEnd(addComboBox(), addImageButton(ADD_IMAGE), addImageURL());
    }

    @Override
    List<ComboBox<String>> getComboBoxes () {
        System.out.println(myComboBoxes);
        return myComboBoxes;
    }

    @Override
    void initializeEverything (ObservableList<String> states) {
        myStates = states;
        myComboBoxes = new ArrayList<>();
        myImageAdderButtons = new ArrayList<>();
        myShortImageURLs = new ArrayList<>();
        myCompleteImageURLs = new ArrayList<>();
    }
}
