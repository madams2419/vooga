package authoring.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Natalie
 *
 */
public class ErrorDialog {
	
	private static final String ERROR_TITLE = "Error";

    public ErrorDialog(String errorMessage){
	    initialize(errorMessage);
	}
	
	private void initialize(String errorMessage) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(ERROR_TITLE);
            
            //GridPane grid = new GridPane();
            DialogGridOrganizer grid = new DialogGridOrganizer(3);
            
            grid.addRowEnd(new Label(errorMessage));
            
            
            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.showAndWait();
	}
}
