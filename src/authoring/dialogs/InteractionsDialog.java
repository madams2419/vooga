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
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.fileBuilders.Collision_XML;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Andrew Sun
 *
 */
public class InteractionsDialog extends Dialog<ButtonType> {

	private List<Action> myActions;
	private Map<Action, String> mySprite1Interactions, mySprite2Interactions;
	private List<ComboBox<Action>> myComboBoxes1, myComboBoxes2;
	private List<Label> myDescriptions1, myDescriptions2, myChecks1, myChecks2;
	private List<TextField> myParams1, myParams2;
	private DialogGridOrganizer mySprite1Grid, mySprite2Grid;
	private HBox myHBox;
	private RightPane myParent;
	

	private static final int BOTTOM_SPACING = 31;

	public InteractionsDialog(RightPane parent, Sprite a, Sprite b) throws IOException {
		myParent = parent;
		mySprite1Interactions = new HashMap<>();
		mySprite2Interactions = new HashMap<>();
		myDescriptions1 = new ArrayList<>();
		myChecks1 = new ArrayList<>();
		myParams1 = new ArrayList<>();
		myComboBoxes1 = new ArrayList<>();
		myDescriptions2 = new ArrayList<>();
		myChecks2 = new ArrayList<>();
		myParams2 = new ArrayList<>();
		myComboBoxes2 = new ArrayList<>();

		mySprite1Grid = createGrid();
		mySprite2Grid = createGrid();
		myHBox = new HBox(50);
		myHBox.getChildren().addAll(mySprite1Grid, mySprite2Grid);

		myActions = grabActions();

		this.getDialogPane().setContent(myHBox);
		ButtonType spr1 = new ButtonType("Add Sprite 1 Action");
		ButtonType spr2 = new ButtonType("Add Sprite 2 Action");
		this.getDialogPane().getButtonTypes()
				.addAll(spr1, spr2, ButtonType.OK, ButtonType.CANCEL);
		
		
		addButton(spr1, e -> {
			System.out.println(mySprite1Grid.getHeight());
			System.out.println(this.getHeight());
			if (mySprite1Grid.getHeight() + 116 >= this.getHeight()) {
				this.setHeight(this.getHeight() + BOTTOM_SPACING);
			}
			mySprite1Grid.addRowEnd(addComboBox(myComboBoxes1, myDescriptions1),
					addParamTextField(myParams1, myComboBoxes1, myChecks1), addLabel(myDescriptions1),
					addLabel(myChecks1));
			this.setWidth(1200);
			e.consume();
		});

		addButton(spr2, e -> {
			System.out.println(mySprite2Grid.getHeight());
			System.out.println(this.getHeight());
			if (mySprite2Grid.getHeight() + 116 >= this.getHeight()) {
				this.setHeight(this.getHeight() + BOTTOM_SPACING);
			}
			mySprite2Grid.addRowEnd(addComboBox(myComboBoxes2, myDescriptions2),
					addParamTextField(myParams2,  myComboBoxes2, myChecks2), addLabel(myDescriptions2),
					addLabel(myChecks2));
			this.setWidth(1200);
			e.consume();
		});
		
		populateDialog(a, b);
		
		showBox(a, b);
	}

	private void populateDialog(Sprite a, Sprite b) {
		mySprite1Interactions = a.getInteractionMap().getOrDefault(b, new HashMap<>());
		mySprite2Interactions = b.getInteractionMap().getOrDefault(a, new HashMap<>());
		
		for (Action act: mySprite1Interactions.keySet()){
			ComboBox<Action> combo = addComboBox(myComboBoxes1, myDescriptions1);
			combo.setValue(act);
			TextField param = addParamTextField(myParams1, myComboBoxes1, myChecks1);
			param.setText(mySprite1Interactions.get(act));
			mySprite1Grid.addRowEnd(combo,
					param, addLabel(myDescriptions1),
					addLabel(myChecks1));
		}
		
		for (Action act: mySprite2Interactions.keySet()){
			ComboBox<Action> combo = addComboBox(myComboBoxes2, myDescriptions2);
			combo.setValue(act);
			TextField param = addParamTextField(myParams2, myComboBoxes2, myChecks2);
			param.setText(mySprite2Interactions.get(act));
			mySprite2Grid.addRowEnd(combo,
					param, addLabel(myDescriptions2),
					addLabel(myChecks2));
		}
	}

	private DialogGridOrganizer createGrid() {
		DialogGridOrganizer result = new DialogGridOrganizer(4);
		result.addRowEnd(new Label("Action"), new Label("Params"), new Label(
				"Description"), new Label("Check"));
		return result;
	}

	public void showBox(Sprite a, Sprite b) {
		this.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> collectProperties(a, b));
	}
	
	private void collectProperties(Sprite a, Sprite b) {
		mySprite1Interactions = new HashMap<>();
		mySprite2Interactions = new HashMap<>();
		List<String> totalInteractions = new ArrayList<>();
		for (int i = 0; i < myDescriptions1.size(); i++) {
			mySprite1Interactions.put(myComboBoxes1.get(i)
					.getValue(),
					myParams1.get(i).getText());
			totalInteractions.add(myComboBoxes1.get(i)
					.getValue().getAction()
					+ myParams1.get(i).getText());
		}
		for (int i = 0; i < myDescriptions2.size(); i++) {
			mySprite2Interactions.put(myComboBoxes2.get(i)
					.getValue(),
					myParams2.get(i).getText());
			totalInteractions.add(myComboBoxes2.get(i)
					.getValue().getAction()
					+ myParams2.get(i).getText());
		}
		a.addInteraction(b, mySprite1Interactions);
		b.addInteraction(a, mySprite2Interactions);
		myParent.getParent().getCenterPane().getActiveTab().addCollision(new Collision_XML(a,b,totalInteractions), a,b);
	}

	private void addButton(ButtonType b, EventHandler<ActionEvent> e) {
		final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		addButton.addEventFilter(ActionEvent.ACTION, e);
	}

	private TextField addParamTextField(List<TextField> list, List<ComboBox<Action>> comboList,
			List<Label> check) {
		TextField result = new TextField();
		list.add(result);
		result.setOnKeyReleased(e -> {
			int index = list.indexOf(result);
			check.get(index).setText(
					Boolean.toString(comboList.get(index).getValue().checkParams(result.getText())));
		});
		return result;
	}

	private Label addLabel(List<Label> labelList) {
		Label result = new Label();
		labelList.add(result);
		return result;
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
}
