package authoring.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * 
 * @author Natalie
 *
 */
public class SimpleDialog {
	
    public SimpleDialog(String message, String title){
	    initialize(message, title);
	}
	
	private void initialize(String message, String title) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setHeaderText(message);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.showAndWait();
	}
}
