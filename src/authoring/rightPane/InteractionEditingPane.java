package authoring.rightPane;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import authoring.Interaction;
import authoring.InteractionManager;
import authoring.Sprite;


/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau
 *
 */

public class InteractionEditingPane extends EditingPane {
    private static final String doneButtonString = "done";
    private Sprite sprite1, sprite2;
    private Interaction interaction;

    InteractionEditingPane (Scene scene,
                            Sprite sprite1,
                            Sprite sprite2,
                            List<String> actionPossibilities) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));

        // temporary
        sprite1 = new Sprite(1, "/images/smallerLuigi.png");
        sprite2 = new Sprite(1, "/images/luigi.png");

        addSpriteAndComboBoxToPane(sprite1, actionPossibilities);
        addSpriteAndComboBoxToPane(sprite2, actionPossibilities);
        addButtonToUpdate("Update");
        addButtonToReturnToCreationPane(doneButtonString);
    }

    private void addButtonToUpdate (String label) {
        Button b = new Button(label);
        b.setOnMouseClicked(i -> setInteraction());
        this.getChildren().add(b);
    }

    private void setInteraction () {
        InteractionManager interactionManager = InteractionManager.getInstance();
    }

    private void addSpriteAndComboBoxToPane (Sprite sprite, List<String> actionPossibilities) {
        addSpriteToPane(sprite);
        addComboBoxToPane(actionPossibilities);
    }

    private void addComboBoxToPane (List<String> actionPossibilities) {
        final ComboBox<String> emailComboBox = new ComboBox<>();
        emailComboBox.getItems().addAll(actionPossibilities);
        getChildren().add(emailComboBox);
    }

    // TODO
    // TEMPORARILY partially copied code from CharacterCreationPane
    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        this.getChildren().add(spriteIcon);
    }
}
