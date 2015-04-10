package authoring.rightPane;

import authoring.userInterface.ClickHandler;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * This is the superclass for any specific pane on the right side of the screen.
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class EditingPane extends VBox {

    private static final String returnToCreationMethod = "switchToCharacterCreationPane";
    private Scene myScene;

    public EditingPane (Scene scene) {
        assert (scene != null);
        myScene = scene;
    }

    public Scene getMyScene () {
        return myScene;
    }

    void addButtonToReturnToCreationPane (String label) {
        Button b = new Button(label);
        try {
            b.setOnAction(new ClickHandler(RightPane.class
                    .getMethod(returnToCreationMethod), RightPane
                    .getInstance(), null));
        }
        catch (NoSuchMethodException | SecurityException e) {
            // TODO
        }
        this.getChildren().add(b);
    }

}
