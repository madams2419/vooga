package authoring.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import authoring.panes.rightPane.ObjectivePane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Daniel
 *
 */
public class ObjectiveDialog extends Dialog<ButtonType> {

	private static final int BOTTOM_SPACING = 25;

	private final String[] behaviours = { "targetType", "targetIndex", "name",
			"parameters" };

	private List<ComboBox<String>> myComboBoxes;
	private List<TextField> myTextFields;
	private Map<String, String> myKeyActions;
	private ObjectivePane myParent;

	// TODO: refactoring
	public ObjectiveDialog(ObjectivePane parent) {

		myComboBoxes = new ArrayList<>();
		myTextFields = new ArrayList<>();
		myParent = parent;

		DialogGridOrganizer grid = new DialogGridOrganizer(4);
		grid.addRowEnd(new Label("Complete/Failed"), new Label("Sprite"),
				new Label("Action"), new Label("Pre-requisites"));

		grid.addRowEnd(addStatesBox(), addSpritesBox(), addActionsBox(),
				addPrereqsBox());
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes()
				.addAll(b, ButtonType.OK, ButtonType.CANCEL);

		final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		addButton.addEventFilter(ActionEvent.ACTION, event -> {
			this.setHeight(this.getHeight() + BOTTOM_SPACING);
			grid.addRowEnd(addStatesBox(), addSpritesBox(), addActionsBox(),
					addPrereqsBox());
			event.consume();
		});

		showBox();

	}

	public void showBox() {
		this.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(
						response -> {
							myKeyActions = new HashMap<>();
							for (int i = 0; i < myTextFields.size(); i++) {
								myKeyActions.put(myTextFields.get(i).getText(),
										myComboBoxes.get(i).getValue());
							}
							// s.setKeyActions(myKeyActions);
							System.out.println(myKeyActions);
						});
	}

	public ComboBox<String> addActionsBox() {
		return addComboBox("win", "lose", "die");
	}

	public ComboBox<String> addSpritesBox() {
		return addComboBox("Main player", "other");
	}

	public ComboBox<String> addStatesBox() {
		ComboBox<String> b = addComboBox("On complete", "On failed");
		b.getSelectionModel().select(0);
		return b;
	}

	public ComboBox<String> addPrereqsBox() {
		return addComboBox(myParent.getObjectives().stream().map(e -> {
			return e.getText();
		}).collect(Collectors.toList()));
	}

	private ComboBox<String> addComboBox(Collection<String> elements) {
		ComboBox<String> result = new ComboBox<>();
		result.getItems().addAll(elements);
		myComboBoxes.add(result);
		return result;
	}

	private ComboBox<String> addComboBox(String... elements) {
		return addComboBox(Arrays.asList(elements));
	}

	public List<ComboBox<String>> getComboBoxes() {
		return myComboBoxes;
	}

	public void PopulateComboBox(List<String> controlsList,
			List<KeyCode> keycodeList) {
	}

	public Map<String, String> getKeyActions() {
		return myKeyActions;
	}

}
