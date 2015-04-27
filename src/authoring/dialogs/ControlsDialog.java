package authoring.dialogs;

import game_engine.annotation.ReadProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import authoring.dataEditors.Action;
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

	private List<ComboBox<Action>> myComboBoxes;
	private List<TextField> myTextFields, myParamTextFields;
	private List<Label> myDescriptions, myChecks;
	private Map<String, String> myKeyActions;
	private RightPane myParent;
	private List<Action> myActions;
	private int c = 0;

	private String[] myMouseClickedStrings = { "onClicked", "whilePressed",
			"onReleased" };
	private static final int BOTTOM_SPACING = 25;

	// TODO: refactoring
	public ControlsDialog(Sprite s, RightPane r){

		myParent = r;

		myComboBoxes = new ArrayList<>();
		myTextFields = new ArrayList<>();
		myParamTextFields = new ArrayList<>();
		try {
			myActions = grabActions();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myChecks = new ArrayList<>();
		myDescriptions = new ArrayList<>();
		DialogGridOrganizer grid = new DialogGridOrganizer(5);
		grid.addRowEnd(new Label("Action"), new Label("Key"), new Label(
				"Params"), new Label("Description"), new Label("Check"));

		grid.addRowEnd(addComboBox(myComboBoxes, myDescriptions), 
				addKeyPressedField(), addParamTextField(), addLabel(myDescriptions),
				addLabel(myChecks));
		this.getDialogPane().setContent(grid);
		ButtonType b = new ButtonType("Add");
		this.getDialogPane().getButtonTypes()
				.addAll(b, ButtonType.OK, ButtonType.CANCEL);

		final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		addButton.addEventFilter(ActionEvent.ACTION, event -> {
			this.setHeight(this.getHeight() + BOTTOM_SPACING);
			grid.addRowEnd(addComboBox(myComboBoxes, myDescriptions), 
					addKeyPressedField(), addParamTextField(), addLabel(myDescriptions),
					addLabel(myChecks));
			this.setWidth(800);
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
								behaviours.add(String.format("%s:%s:%s",
										s.getID(), myComboBoxes.get(i)
												.getSelectionModel()
												.getSelectedItem(),
										myParamTextFields.get(i).getText()));

								KeyAction_XML action = new KeyAction_XML(
										myTextFields.get(i).getText(),
										behaviours, true);

								myParent.getParent()
										.getCenterPane()
										.getActiveTab()
										.addKeyAction(
												myTextFields.get(i).getText(),
												action);
							}
							s.setKeyActions(myKeyActions);
							System.out.println(myKeyActions);
						});
	}

	private ComboBox<Action> addComboBox(List<ComboBox<Action>> comboList,
			List<Label> descriptionList) {
		ComboBox<Action> result = new ComboBox<>();
		for (Action a : myActions) {
			result.getItems().add(a);
		}
		result.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {
			@Override
			public ListCell<Action> call(ListView<Action> p) {
				final ListCell<Action> cell = new ListCell<Action>() {
					@Override
					protected void updateItem(Action t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getAction());
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});
		result.valueProperty().addListener(new ChangeListener<Action>() {
			@Override
			public void changed(ObservableValue<? extends Action> ov,
					Action before, Action after) {
				int index = comboList.indexOf(result);
				descriptionList.get(index).setText(after.getDescription());
			}
		});
		comboList.add(result);
		return result;
	}

	private List<Action> grabActions() throws IOException {
		ReadProperties reader = new ReadProperties();
		Map<Integer, Map<String, String>> actionsMap = reader
				.getPropertiesMap("Actions.properties");
		List<Action> actionsList = new ArrayList<>();
		for (Map<String, String> action : actionsMap.values()) {
			actionsList.add(new Action(action.get("name"), action
					.get("description"), Integer.parseInt(action
					.get("numParams"))));
		}
		return actionsList;
	}

	private TextField addKeyPressedField() {
		TextField result = new TextField();
		result.setEditable(false);
		result.setContextMenu(new ContextMenu());
		result.setOnKeyPressed(e -> result.setText(e.getCode().toString()));
		result.setOnMouseClicked(e -> {
			result.setText(e.getButton().toString() + " "
					+ myMouseClickedStrings[c++ % myMouseClickedStrings.length]);

		});
		myTextFields.add(result);
		return result;
	}

	private TextField addParamTextField() {
		TextField result = new TextField();
		myParamTextFields.add(result);
		return result;
	}

	public List<ComboBox<Action>> getComboBoxes() {
		return myComboBoxes;
	}

	public void PopulateComboBox(List<String> controlsList) {

	}
		
	private Label addLabel(List<Label> labelList) {
		Label result = new Label();
		labelList.add(result);
		return result;
	}

	public Map<String, String> getKeyActions() {
		return myKeyActions;
	}
}
