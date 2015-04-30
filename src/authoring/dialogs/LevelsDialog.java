package authoring.dialogs;

import java.util.function.Consumer;
import javafx.scene.control.ButtonType;
import authoring.dataEditors.Sprite;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;

public class LevelsDialog extends ActionsDialog{

    public LevelsDialog(RightPane parent, Sprite a, Sprite b){
        
    }
    
    @Override
    String getMyTitle () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer ... grid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite ... s) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void addOtherComponents (DialogGridOrganizer ... grid) {
        // TODO Auto-generated method stub
        
    }

}
