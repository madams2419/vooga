package authoring.rightPane;

import authoring.Sprite;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;


/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau
 *
 */

public class InteractionEditingPane extends EditingPane {

    InteractionEditingPane (Scene scene, Sprite sprite1, Sprite sprite2) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));
    }
}
