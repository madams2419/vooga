package authoring.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;
import authoring.util.FrontEndUtils;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class AnimationsDialog extends Dialog<ButtonType> {

    private static final String ADD = "Add";
    private static final String IMAGE_FILE_URL = "Image File URL";
    private List<ComboBox<String>> myComboBoxes;
    private List<Button> myImageAdderButtons;
    private List<Label> myShortImageURLs;
    private List<Label> myCompleteImageURLs;
    // private List<TextField> myTextFields;
    private Map<String, String> myAnimations;

    private static final String ADD_IMAGE = "Set image for state";
    private static final String STATE = "State";
    private static final String IMAGE = "Image";
    private static final int BOTTOM_SPACING = 25;
    
    private static final String imageChooserTitle = "Change Character Image";
    private static final String imageChooserDescription = "Image Files";
    private static final String[] imageChooserExtensions = { "*.png", "*.jpg",
                                                            "*.gif" };
    
    //TODO should really be refactored with a superclass because a lot of this is copied from ControlsDialog
    public AnimationsDialog (Sprite s) {

        myComboBoxes = new ArrayList<>();
        myImageAdderButtons = new ArrayList<>();
        myShortImageURLs = new ArrayList<>();
        myCompleteImageURLs  = new ArrayList<>();

        DialogGridOrganizer grid = new DialogGridOrganizer(3);
        grid.addRowEnd(new Label(STATE), new Label(IMAGE), new Label(IMAGE_FILE_URL));
        grid.addRowEnd(addComboBox(), addImageButton(ADD_IMAGE), addImageURL());
        this.getDialogPane().setContent(grid);
        ButtonType b = new ButtonType(ADD);
        this.getDialogPane().getButtonTypes().addAll(b, ButtonType.OK, ButtonType.CANCEL);

        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            grid.addRowEnd(addComboBox(), addImageButton(ADD_IMAGE));
            event.consume();
        });

        showBox(s);

    }

    public void showBox (Sprite s) {
        this.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    myAnimations = new HashMap<>();
                    for (int i = 0; i < myImageAdderButtons.size(); i++) {
                        myAnimations.put(myComboBoxes.get(i).getValue(),
                                         myShortImageURLs.get(i).getText());
                    }
                    s.setAnimations(myAnimations);
                });
    }

    public ComboBox<String> addComboBox () {
        // TODO: Add String list of interactions
        ComboBox<String> result = new ComboBox<>();
        result.getItems().addAll("jump", "forward", "backward");
        myComboBoxes.add(result);
        return result;
    }

    private Button addImageButton (String label) {
        Button button = new Button(label);
        myImageAdderButtons.add(button);
        button.setOnKeyPressed(e -> selectImage(myComboBoxes.size() - 1));
        return button;
    }

    private Label addImageURL () {
        Label label = new Label("woo");
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

    private void selectImage(int index) {
        File selectedImageFile;
        if ((selectedImageFile = FrontEndUtils.selectFile(imageChooserTitle, imageChooserDescription, imageChooserExtensions)) != null) {
            myShortImageURLs.get(index).setText(shortenURL(selectedImageFile.toURI().toString()));
            myCompleteImageURLs.get(index).setText(selectedImageFile.toURI().toString());
        }
    }

    public List<ComboBox<String>> getComboBoxes () {
        return myComboBoxes;
    }

    public void populateComboBox (ObservableList<String> states) {
         for (ComboBox<String> box : myComboBoxes) {
             box.setItems(states);
         }
    }

    public Map<String, String> getAnimations () {
        return myAnimations;
    }

}
