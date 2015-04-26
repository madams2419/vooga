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
 * 
 * @author Natalie, Andrew
 *
 */
public class CharacterPhysicsDialog extends DataDialog {

    private Label myTypeLabel, myMaterialLabel;
    private ComboBox<String> myTypeBox, myMaterialBox;
    private Sprite mySprite;

    private static final String PROPERTIES_FILEPATH = "/Resources/properties/CharacterPhysicsDialog.properties";
    private static final String TYPE = "Type";
    private static final String MATERIAL = "Material";
    private static final String[] myTypeProperties = {"ComplexPhysicsObject", "MovingPhysicsObject", 
                                                      "AcceleratingPhysicsObject"};
    private static final String[] myMaterialProperties = 
        {"rock", "wood", "metal", "bouncy_ball", "super_ball", "pillow", "static", "floating"};
    private String[] myMaterialChoices, myTypeChoices;

    public CharacterPhysicsDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(sprite, 1, new Node[]{}, 0);
    }

    @Override
    public void addAddButton () {
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
    void addBlankRow (DialogGridOrganizer grid, int index) {
    }

    void initializeEverything (Sprite sprite) {
        importFromPropertiesFile();
        mySprite = sprite;
        myTypeLabel = new Label(TYPE);
        myMaterialLabel = new Label(MATERIAL);
        myTypeBox = new ComboBox<String>();
        myTypeBox.getItems().addAll(myTypeChoices);
        myMaterialBox = new ComboBox<String>();
        myMaterialBox.getItems().addAll(myMaterialChoices);
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

    @Override
    void addOtherComponents (DialogGridOrganizer grid) {
        grid.addRowEnd(myTypeLabel);
        grid.addRowEnd(myTypeBox);
        grid.addRowEnd(myMaterialLabel);
        grid.addRowEnd(myMaterialBox);
    }
}
