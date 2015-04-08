package src.authoring.rightPane;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import authoring.SpriteType;


/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau
 *
 */

public class InteractionEditingPane extends EditingPane {

    InteractionEditingPane (Scene scene, SpriteType sprite1, SpriteType sprite2) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));
    }
}
