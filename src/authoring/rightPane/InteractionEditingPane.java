package authoring.rightPane;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import authoring.Sprite;


/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau
 *
 */

public class InteractionEditingPane extends EditingPane {
    private static final String doneButtonString = "done";

    InteractionEditingPane (Scene scene,
                            Sprite sprite1,
                            Sprite sprite2,
                            List<String> actionPossibilities) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));
        
        // temporary
        sprite1 = new Sprite(1, "/images/turtle.png");
        sprite2 = new Sprite(1, "/images/luigi.png");
        
        addSpriteToPane(sprite1);
        addComboBoxToPane(actionPossibilities);
        addSpriteToPane(sprite2);
        addComboBoxToPane(actionPossibilities);
        addButtonToReturnToCreationPane(doneButtonString);
    }

    private void addComboBoxToPane (List<String> actionPossibilities) {
        final ComboBox<String> emailComboBox = new ComboBox<>();
        emailComboBox.getItems().addAll(
                                        actionPossibilities
                );
        getChildren().add(emailComboBox);
    }

    // TODO
    // TEMPORARILY partially copied code from CharacterCreationPane
    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        this.getChildren().add(spriteIcon);
    }
}
