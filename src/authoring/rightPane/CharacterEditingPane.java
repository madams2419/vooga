package authoring.rightPane;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import authoring.Sprite;

/**
 * This will be for when a character already on the screen is clicked on. It will allow the
 * designer to edit the character.
 * 
 * @author Natalie Chanfreau
 *
 */

public class CharacterEditingPane extends EditingPane {

    CharacterEditingPane (Scene scene, Sprite sprite) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("Character editing pane")));
        this.getChildren().add(sprite);
    }

}
