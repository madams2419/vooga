package authoring.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import authoring.dataEditors.Sprite;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;

public class ChooseInteractionDialog extends DataDialog{

    private static final int BOTTOM_SPACING = 31;
    private static final int GRID_ORGANIZER_SIZE = 1;
    private static final int INITIAL_ROWS = 0;
    private static final int NUM_GRIDS = 1;
    
    private RightPane myParent;
    private Sprite mySprite1, mySprite2;
    
    public ChooseInteractionDialog(RightPane parent, Sprite a, Sprite b){
        initialize(GRID_ORGANIZER_SIZE, INITIAL_ROWS, NUM_GRIDS,
                   new Node[] {new Label("Choose")});
        myParent = parent;
        mySprite1 = a;
        mySprite2 = b;
        showBox(a, b);
    }
    
    
    @Override
    String getMyTitle () {
        return "Choose Interaction";
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer ... grid) {
        
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite ... s) {
        //Do nothing on OK.
        return (response -> {});
    }

    @Override
    void addOtherComponents (DialogGridOrganizer ... grid) {
        grid[0].addRowEnd(addButton("Set Interactions", e -> setInteraction(), new ArrayList<>()));
        grid[0].addRowEnd(addButton("Set Objectives", e -> setObjectives(), new ArrayList<>()));
        grid[0].addRowEnd(addButton("Set Levels", e -> setLevels(), new ArrayList<>()));
    }


    private void setLevels () {
       new LevelsDialog(myParent, mySprite1, mySprite2);
    }


    private void setObjectives () {
        try {
            new InteractionObjectiveDialog(myParent, mySprite1, mySprite2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInteraction () {
        try {
            new InteractionsSpritesDialog(myParent, mySprite1, mySprite2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
