package authoring.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import authoring.dataEditors.Sprite;
import authoring.fileBuilders.KeyAction_XML;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Andrew
 *
 */
public class ControlsDialog extends Dialog<ButtonType> {

	private List<ComboBox<String>> myComboBoxes;
	private List<TextField> myTextFields;
	private List<TextField> myParamTextFields;
	private Map<String, String> myKeyActions;
	private RightPane myParent;
	private int c = 0;

	private String[] myMouseClickedStrings = {"onClicked", "whilePressed", "onReleased"};
	private static final int BOTTOM_SPACING = 25;

	// TODO: refactoring
	public ControlsDialog(Sprite s, RightPane r) {

		myParent = r;

		myComboBoxes = new ArrayList<>();
		myTextFields = new ArrayList<>();
		myParamTextFields = new ArrayList<>();

		DialogGridOrganizer grid = new DialogGridOrganizer(3);
		grid.addRowEnd(new Label("Action"), new Label("Key"), new Label(
				"Params"));

		grid.addRowEnd(addComboBox(), addKeyPressedField(), addParamTextField());
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes()
				.addAll(b, ButtonType.OK, ButtonType.CANCEL);

		final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		addButton.addEventFilter(ActionEvent.ACTION, event -> {
			this.setHeight(this.getHeight() + BOTTOM_SPACING);
			grid.addRowEnd(addComboBox(), addKeyPressedField(),
					addParamTextField());
			event.consume();
		});

		showBox(s);

	}

	public void showBox(Sprite s) {
		this.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(
						response -> {
							myKeyActions = new HashMap<>();
							for (int i = 0; i < myTextFields.size(); i++) {
								myKeyActions.put(myTextFields.get(i).getText(),
										myComboBoxes.get(i).getValue()
												+ ", "
												+ myParamTextFields.get(i)
														.getText());
								List<String> behaviours = new ArrayList<>();
								behaviours.add(String.format("%s:%s:%s", s
										.getID(), myComboBoxes.get(i)
										.getValue(), myParamTextFields.get(i)));
								KeyAction_XML action = new KeyAction_XML(myTextFields
										.get(i).getText(), behaviours, true);
								myParent.getParent().getCenterPane().getActiveTab()
								.addKeyAction(myTextFields.get(i).getText(), action);
							}
							s.setKeyActions(myKeyActions);
							System.out.println(myKeyActions);
						});
	}

	private ComboBox<String> addComboBox() {
		// TODO: Add String list of interactions
		ComboBox<String> result = new ComboBox<>();
		result.getItems().addAll("jump", "forward", "backward");
		myComboBoxes.add(result);
		return result;
	}

	private TextField addKeyPressedField() {
		TextField result = new TextField();
		result.setEditable(false);
		result.setContextMenu(new ContextMenu());
		result.setOnKeyPressed(e -> result.setText(e.getCode().toString()));
		result.setOnMouseClicked(e -> {
			result.setText(e.getButton().toString() + " " + 
					myMouseClickedStrings[c++ % myMouseClickedStrings.length]
					);
			
		});
		myTextFields.add(result);
		return result;
	}

	private TextField addParamTextField() {
		TextField result = new TextField();
		myParamTextFields.add(result);
		return result;
	}

	public List<ComboBox<String>> getComboBoxes() {
		return myComboBoxes;
	}
	
	public void PopulateComboBox(List<String> controlsList){
	
	}
		

	public Map<String, String> getKeyActions() {
		return myKeyActions;
	}
}
