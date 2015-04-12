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
import javafx.scene.input.KeyCode;

/**
 * 
 * @author Andrew
 *
 */
public class ControlsDialog extends Dialog<ButtonType>{
	
	private List<ComboBox<String>> myComboBoxes;
	private List<TextField> myTextFields;
	
	private static final int BOTTOM_SPACING = 25;
	
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
			 this.setHeight(this.getHeight() + BOTTOM_SPACING);
			 grid.addRowEnd(addComboBox(), addTextField());
			 event.consume();
		 });
		 
		 showBox();
			
	}
	
	public void showBox(){
		this.showAndWait().filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			// TODO: save responses
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
		result.setEditable(false);
		result.setOnKeyPressed(e -> result.setText(e.getText()));
		myTextFields.add(result);
		return result;
	}
	
	
	public List<ComboBox<String>> getComboBoxes(){
		return myComboBoxes;
	}
	
	public void PopulateComboBox(List<String> controlsList, List<KeyCode> keycodeList){
		
	}
	
}
