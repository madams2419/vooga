package authoring.rightPane;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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
    private Sprite mySprite1, mySprite2;
    private ObjectProperty<String> myActionProperty1, myActionProperty2;
    private Interaction myInteraction;

    InteractionEditingPane (Scene scene,
                            Sprite sprite1,
                            Sprite sprite2,
                            List<String> actionPossibilities) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));

        // temporary
        sprite1 = new Sprite(1, "/images/smallLuigi.png");
        sprite2 = new Sprite(1, "/images/luigi.png");
        
        mySprite1 = sprite1;
        mySprite2 = sprite2;

        myInteraction = getInteraction();
        myActionProperty1 =
                addSpriteAndComboBoxToPane(sprite1, actionPossibilities, myInteraction.getAction1());
        myActionProperty2 =
                addSpriteAndComboBoxToPane(sprite2, actionPossibilities, myInteraction.getAction2());
        addButtonToUpdate("Update");
        addButtonToReturnToCreationPane(doneButtonString);
    }

    private void addButtonToUpdate (String label) {
        Button b = new Button(label);
        b.setOnMouseClicked(i -> setInteraction(myActionProperty1.getValue(),
                                                myActionProperty2.getValue()));
        this.getChildren().add(b);
    }

    private Interaction getInteraction () {
        return InteractionManager.getInstance().getOrCreateInteraction(mySprite1, mySprite2);
    }

    private void setInteraction (String action1, String action2) {
        myInteraction = new Interaction(mySprite1, mySprite2, action1, action2);
        InteractionManager.getInstance().setInteraction(mySprite1, mySprite2, myInteraction);
    }

    private ObjectProperty<String> addSpriteAndComboBoxToPane (Sprite sprite,
                                                               List<String> actionPossibilities,
                                                               String action) {
        addSpriteToPane(sprite);
        return addComboBoxToPane(actionPossibilities, action);
    }

    private ObjectProperty<String> addComboBoxToPane (List<String> actionPossibilities,
                                                      String action) {
        final ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll(actionPossibilities);
        getChildren().add(actionComboBox);
        setInitialComboBoxValue(actionComboBox, action);
        return actionComboBox.valueProperty();
    }

    private void setInitialComboBoxValue (ComboBox<String> actionComboBox, String action) {
        if (action != null) {
            actionComboBox.setValue(action);
        }
    }

    // TODO
    // TEMPORARILY partially copied code from CharacterCreationPane
    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        this.getChildren().add(spriteIcon);
    }
}
