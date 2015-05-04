package authoring.panes.rightPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.dialogs.ChooseInteractionDialog;
import authoring.util.GUIElementCreator;


/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau and Andrew Sun
 *
 */

public class InteractionEditingPane extends EditingPane {
    private Sprite mySprite1, mySprite2;
    private ObservableList<String> mySprite1Interactions =
            FXCollections.observableArrayList();
    private ObservableList<String> mySprite2Interactions =
            FXCollections.observableArrayList();

    InteractionEditingPane (Scene scene, RightPane parent, Sprite sprite1,
                            Sprite sprite2) throws IOException {
        super(scene, parent);

        mySprite1 = sprite1;
        mySprite2 = sprite2;

        setupSprite(mySprite1Interactions, mySprite1, mySprite2);
        setupSprite(mySprite2Interactions, mySprite2, mySprite1);

        addInteractionParams(Constants.UPDATE_INTERACTION_PARAMS);

        List<String> collisionChoices = Arrays.asList(Constants.SIMPLE, Constants.PIXEL_PERFECT, 
                                                      Constants.HIT_BOX);
        GUIElementCreator.addComboBoxToPane(collisionChoices, Constants.SIMPLE, this);

        getInteractions();
        addButtonToReturnToCreationPane(Constants.DONE);
    }

    private void setupSprite (ObservableList<String> mySpriteInteractions,
                              Sprite mySprite, Sprite other) {

        ListView<String> list = new ListView<>(mySpriteInteractions);
        getInteractions(mySprite, mySpriteInteractions, other);
        addSpriteToPane(mySprite);
        getChildren().add(list);
    }

    private void getInteractions (Sprite s, ObservableList<String> list, Sprite other) {
        list.clear();
        Map<Action, String> m = s.getInteractionWithSprite(other);
        for (Action a : m.keySet()) {
            list.add(a.getAction());
        }
    }

    private void addInteractionParams (String s) throws IOException {
        GUIElementCreator.addButton(s, e -> newInteractionDialog(), this);
    }

    private void newInteractionDialog () {
        new ChooseInteractionDialog(getMyParent(), mySprite1, mySprite2);
        getInteractions();
    }

    private void addSpriteToPane (Sprite sprite) {
        getChildren().add(sprite.getIcon());
    }

    private void getInteractions () {
        getInteractions(mySprite1, mySprite1Interactions, mySprite2);
        getInteractions(mySprite2, mySprite2Interactions, mySprite1);
    }

}
