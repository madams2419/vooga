package authoring.dialogs;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Natalie, Andrew
 *
 */
public class StatesDialog extends Dialog<ButtonType>{
	
	private List<TextField> myTextFields;
	private ObservableList<String> myStates;
	
	private static final int BOTTOM_SPACING = 25;
	
	// TODO: refactoring again... don't worry; i'll get to it! :)
	public StatesDialog(Sprite s){
		
		myTextFields = new ArrayList<>();

		DialogGridOrganizer grid = new DialogGridOrganizer(1);
		grid.addRowEnd(new Label("States"));
		
		grid.addRowEnd(addTextField());
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes().addAll(b, ButtonType.OK, ButtonType.CANCEL);

		 final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		 addButton.addEventFilter(ActionEvent.ACTION, event -> {
			 this.setHeight(this.getHeight() + BOTTOM_SPACING);
			 grid.addRowEnd(addTextField());
			 event.consume();
		 });
		 
		 showBox(s);
			
	}
	
	public void showBox(Sprite s){
		this.showAndWait().filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			myStates = FXCollections.observableArrayList();
			for (int i = 0; i < myTextFields.size(); i++){
				myStates.add(myTextFields.get(i).getText());
			}
			s.setStates(myStates);
		});
	}
	
	public TextField addTextField(){
		TextField result = new TextField();
		myTextFields.add(result);
		return result;
	}
	
	
	public List<String> getStates(){
		return myStates;
	}
}
