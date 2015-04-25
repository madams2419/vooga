package authoring.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;
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
	private Map<String, String> myKeyActions;
	
	private static final int BOTTOM_SPACING = 25;
	
	// TODO: refactoring
	public ControlsDialog(Sprite s){
		
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
		 
		 showBox(s);
			
	}
	
	public void showBox(Sprite s){
		this.showAndWait().filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			myKeyActions = new HashMap<>();
			for (int i = 0; i < myTextFields.size(); i++){
				myKeyActions.put(myTextFields.get(i).getText(), myComboBoxes.get(i).getValue());
			}
			s.setKeyActions(myKeyActions);
			System.out.println(myKeyActions);
		});
	}
	
	public ComboBox<String> addComboBox(){
		// TODO: Add String list of interactions
		ComboBox<String> result = new ComboBox<>();
		result.getItems().addAll("jump", "forward", "backward");
		myComboBoxes.add(result);
		return result;
	}
	
	public TextField addTextField(){
		TextField result = new TextField();
		result.setEditable(false);
		result.setOnKeyPressed(e -> result.setText(e.getCode().toString()));
		myTextFields.add(result);
		return result;
	}
	
	
	public List<ComboBox<String>> getComboBoxes(){
		return myComboBoxes;
	}
	
	public void PopulateComboBox(List<String> controlsList, List<KeyCode> keycodeList){
		
	}
	
	public Map<String, String> getKeyActions(){
		return myKeyActions;
	}
	
}
