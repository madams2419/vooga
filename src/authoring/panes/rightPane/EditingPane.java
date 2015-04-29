package authoring.panes.rightPane;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    
    TextField addLabeledTextField(String label){
      HBox h = new HBox(10);
      TextField text = new TextField();
      Label l = new Label(label);
      h.getChildren().addAll(l, text);
      getChildren().add(h);
      return text;
    }
    
    TextField addLabeledTextField(String label, String defaultText){
      TextField result = addLabeledTextField(label);
      result.setText(defaultText);
      return result;
    }
    
    Button createButton(Sprite sprite, String label, EventHandler<ActionEvent> onAction, String image) {
        return createButton(sprite, label, onAction, image, this);
    }
    
    Button createButton(Sprite sprite, String label, EventHandler<ActionEvent> onAction, 
                        String image, Pane container) {
        Button b = createButton(label, onAction, container);
        b.setGraphic(new ImageView(image));
        return b;
    }
    
    Button createButton(String label, EventHandler<ActionEvent> onAction) {
        return createButton(label, onAction, this);
    }
    
    Button createButton(String label, EventHandler<ActionEvent> onAction, Pane container) {
        Button button = new Button(label);
        button.setOnAction(onAction);
        container.getChildren().add(button);
        return button;
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
