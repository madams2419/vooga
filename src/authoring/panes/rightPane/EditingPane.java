package authoring.panes.rightPane;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import authoring.dataEditors.Sprite;
import authoring.userInterface.ClickHandler;


/**
 * This is the superclass for any specific pane on the right side of the screen.
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class EditingPane extends VBox {

    private static final String returnToCreationMethod = "switchToCharacterCreationPane";
    private Scene myScene;
    protected RightPane myParent;

    public EditingPane (Scene scene, RightPane parent) {
        assert (scene != null);
        myScene = scene;
        myParent = parent;
    }

    public Scene getMyScene () {
        return myScene;
    }
    
    public RightPane getMyParent() {
        return myParent;
    }

    @SuppressWarnings("unchecked")
    void addButtonToReturnToCreationPane (String label) {
        Button b = new Button(label);
        try {
            b.setOnAction(new ClickHandler(RightPane.class
                    .getMethod(returnToCreationMethod), myParent));
        }
        catch (NoSuchMethodException | SecurityException e) {
            // TODO
        }
        this.getChildren().add(b);
    }
    
    Button createButton(Sprite sprite, String label, EventHandler<ActionEvent> onAction) {
        Button button = new Button(label);
        button.setOnAction(onAction);
        this.getChildren().add(button);
        return button;
    }
    
    Button createButton(Sprite sprite, String label, EventHandler<ActionEvent> onAction, String image) {
        Button b = createButton(sprite, label, onAction);
        b.setGraphic(new ImageView(image));
        return b;
    }

    protected ObjectProperty<String> addComboBoxToPane (
                                                        List<String> actionPossibilities,
                                                        String action) {
        final ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll(actionPossibilities);
        getChildren().add(actionComboBox);
        setInitialComboBoxValue(actionComboBox, action);
        return actionComboBox.valueProperty();
    }

    private void setInitialComboBoxValue (ComboBox<String> actionComboBox,
                                          String action) {
        if (action != null) {
            actionComboBox.setValue(action);
        }
    }

}
