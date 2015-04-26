package authoring.dialogs;

import java.util.function.Consumer;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class CharacterPhysicsDialog extends DataDialog {

    private Label myTypeLabel, myMaterialLabel;
    private ComboBox<String> myTypeBox, myMaterialBox;
    private Sprite mySprite;

    private static final String TYPE = "Type";
    private static final String MATERIAL = "Material";

    public CharacterPhysicsDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(sprite, 1, null, 0);
    }

    @Override
    public void addAddButton() {
        // don't make an add button
    }
    
    @Override
    Consumer<ButtonType> getTodoOnOK () {
        return (response -> {
            updateSpritePhysics();
        });
    }

    private void updateSpritePhysics () {
        mySprite.setMyType(myTypeBox.getValue());
        mySprite.setMyMaterial(myMaterialBox.getValue());
    }

    @Override
    void addBlankRow (DialogGridOrganizer grid, int index) { }

    void initializeEverything (Sprite sprite) {
        mySprite = sprite;
        myTypeLabel = new Label(TYPE);
        myMaterialLabel = new Label(MATERIAL);
        myTypeBox = new ComboBox<String>();
        myMaterialBox = new ComboBox<String>();
    }

}
