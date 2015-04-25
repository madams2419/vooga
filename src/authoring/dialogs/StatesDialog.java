package authoring.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class StatesDialog extends DataDialog {

    private static final String STATES = "States";
    private List<TextField> myTextFields;
    private ObservableList<String> myStates;
    private Sprite mySprite;

    public StatesDialog (Sprite sprite) {
        initializeEverything(sprite);
        initialize(sprite, 1,
                   new Node[] { new Label(STATES) });
    }

    private void initializeEverything (Sprite sprite) {
        mySprite = sprite;
        myStates = FXCollections.observableArrayList();
        myTextFields = new ArrayList<>();
    }

    @Override
    ObservableList<String> getListContent () {
        return myStates;
    }

    @Override
    void addBlankRow (DialogGridOrganizer grid, int index) {
        grid.addRowEnd(addTextField(myTextFields));
    }

    @Override
    Consumer<ButtonType> getTodoOnOK () {
        return (response -> {
            myStates = FXCollections.observableArrayList();
            for (int i = 0; i < myTextFields.size(); i++) {
                myStates.add(myTextFields.get(i).getText());
            }
            mySprite.setStates(myStates);
        });
    }

}
