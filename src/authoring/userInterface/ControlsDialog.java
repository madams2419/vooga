package authoring.userInterface;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 
 * @author Andrew
 *
 */
public class ControlsDialog extends Dialog<ButtonType>{
	
	private List<ComboBox<String>> myComboBoxes;
	private List<TextField> myTextFields;
	
	// TODO: refactoring
	public ControlsDialog(){
		
		myComboBoxes = new ArrayList<>();
		myTextFields = new ArrayList<>();

		DialogGridOrganizer grid = new DialogGridOrganizer(2);
		grid.addRowEnd(new Label("Action"), new Label("Key"));
		
		grid.addRowEnd(addComboBox(), addTextField());
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes().addAll(b, ButtonType.OK, ButtonType.CANCEL);

		 final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		 addButton.addEventFilter(ActionEvent.ACTION, event -> {
			 this.setHeight(this.getHeight() + 25);
			 grid.addRowEnd(addComboBox(), addTextField());
			 event.consume();
		 });
		 
			this.showAndWait().filter(response -> response == ButtonType.OK)
			.ifPresent(response -> {
				// TODO: output controls to xml
			});
			
	}
	
	
	public ComboBox<String> addComboBox(){
		// TODO: Add String list of interactions
		ComboBox<String> result = new ComboBox<>();
		myComboBoxes.add(result);
		return result;
	}
	
	public TextField addTextField(){
		TextField result = new TextField();
		myTextFields.add(result);
		return result;
	}
	
	public List<ComboBox<String>> getComboBoxes(){
		return myComboBoxes;
	}
	
}
