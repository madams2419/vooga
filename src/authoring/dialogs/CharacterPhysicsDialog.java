package authoring.dialogs;

import java.io.IOException;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;
import authoring.util.PropertiesFileParser;


/**
 * Creates a dialog that allows users to modify physics of a sprite.
 * @author Natalie, Andrew
 *
 */
public class CharacterPhysicsDialog extends DataDialog {

    private Label myTypeLabel, myMaterialLabel;
    private ComboBox<String> myTypeBox, myMaterialBox;
    private Sprite mySprite;

    private static final String TITLE = "Physics of Character";
    private static final String PROPERTIES_FILEPATH = "/Resources/properties/CharacterPhysicsDialog.properties";
    private static final String TYPE = "Type";
    private static final String MATERIAL = "Material";
    private static final String[] myTypeProperties = {"ComplexPhysicsObject", "MovingPhysicsObject", 
                                                      "AcceleratingPhysicsObject"};
    private static final String[] myMaterialProperties = 
        {"rock", "wood", "metal", "bouncy_ball", "super_ball", "pillow", "static", "floating"};
    private String[] myMaterialChoices, myTypeChoices;

    public static CharacterPhysicsDialog defaultPhysics (Sprite s) {
        return new CharacterPhysicsDialog(s);
    }
    
    public CharacterPhysicsDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(1, 0, 1, new Node[]{});
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite... s) {
        return (response -> {
            updateSpritePhysics();
        });
    }

    private void updateSpritePhysics () {
        mySprite.setMyType(findPair(myTypeBox.getValue(), myTypeProperties, myTypeChoices));
        mySprite.setMyMaterial(findPair(myMaterialBox.getValue(), myMaterialProperties, myMaterialChoices));
    }

    private String findPair (String value, String[] properties, String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            if (value.equals(choices[i])) {
                return properties[i];
            }
        }
        return null; //should never get here...
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer... grid) {
      // not needed
    }

    void initializeEverything (Sprite sprite) {
        importFromPropertiesFile();
        mySprite = sprite;
        myTypeLabel = new Label(TYPE);
        myMaterialLabel = new Label(MATERIAL);
        myTypeBox = createComboBox(myTypeChoices);
        myMaterialBox = createComboBox(myMaterialChoices);
    }

    private ComboBox<String> createComboBox (String[] choices) {
        ComboBox<String> box = new ComboBox<String>();
        box.getItems().addAll(choices);
        box.setValue(choices[0]);
        return box;
    }

    private void importFromPropertiesFile () {
        try {
            myMaterialChoices = PropertiesFileParser.loadProperties(myMaterialProperties, PROPERTIES_FILEPATH);
            myTypeChoices = PropertiesFileParser.loadProperties(myTypeProperties, PROPERTIES_FILEPATH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    String getMyTitle () {
        return TITLE;
    }

    @Override
    void addOtherComponents (DialogGridOrganizer... grid) {
        grid[0].addRowEnd(myTypeLabel);
        grid[0].addRowEnd(myTypeBox);
        grid[0].addRowEnd(myMaterialLabel);
        grid[0].addRowEnd(myMaterialBox);
    }
}
