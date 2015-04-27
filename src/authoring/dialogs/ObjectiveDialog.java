package authoring.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.dataEditors.Sprite;
import authoring.fileBuilders.Objective_XML;
import authoring.panes.rightPane.ObjectivePane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * Creates a dialog that lets user define the objectives for the game.
 * @author Daniel, refactored by Andrew
 *
 */
public class ObjectiveDialog extends DataDialog {

	private List<ComboBox<String>> mActions = new ArrayList<>();
	private List<ComboBox<String>> mPrereqs = new ArrayList<>();
	private List<ComboBox<String>> mSprites = new ArrayList<>();
	private List<ComboBox<String>> mStates = new ArrayList<>();
	private List<TextField> mParams = new ArrayList<>();
	private TextField myDescription;

	private ObjectivePane myParent;
	private int selected, myIndex;

	public ObjectiveDialog(ObjectivePane parent, int objectiveNumber) {
		myParent = parent;
		myIndex = objectiveNumber;
    myDescription = new TextField();	
    initialize(5, 1, 1,
        new Node[] { new Label("Complete/Failed"), new Label("Sprite"),
        new Label("Action"), new Label("Parameters"), new Label(
            "Pre-requisites")});
		showBox();
	}

	private Map<String, List<String>> collectBehaviours() {
		Map<String, List<String>> mResult = new HashMap<>();
		mResult.put("onComplete", new ArrayList<String>());
		mResult.put("onFailed", new ArrayList<String>());
		mResult.put("prereqs", new ArrayList<String>());
		for (int i = 0; i < mActions.size(); i++) {
			String action = String.format("%s:%s:%s", mSprites.get(i)
					.getSelectionModel().getSelectedItem(), mActions.get(i)
					.getSelectionModel().getSelectedItem(), mParams.get(i)
					.getText());
			mResult.get(mStates.get(i).getSelectionModel().getSelectedItem())
					.add(action);
			mResult.get("prereqs").add(
					mPrereqs.get(i).getSelectionModel().getSelectedItem());
		}
		return mResult;
	}


	public ComboBox<String> addSpritesBox(int index) {
		ComboBox<String> b = addComboBox(mSprites, 
		    Arrays.asList(new String[]{"Main player", "other"}));
		b.valueProperty().addListener((ov, t, t1) -> {
			if (t1.equals("other")) {
				this.selected = index;
				this.myParent.getMyParent().getParent().setSpriteWaiting(true);
				this.close();
			}
		});
		return b;
	}

	public ComboBox<String> addStatesBox() {
		ComboBox<String> b = addComboBox(mStates, 
		    Arrays.asList(new String[]{"onComplete", "onFailed"}));
		b.getSelectionModel().select(0);
		return b;
	}

	public ComboBox<String> addPrereqsBox() {
		ComboBox<String> b = addComboBox(mPrereqs, myParent.getObjectives().stream()
				.map(e -> {
					return e.getText();
				}).collect(Collectors.toList()));
		return b;
	}


	public void setSprite(Sprite s) {
		mSprites.get(selected).getItems().add(s.toString());
	}

  @Override
  String getMyTitle() {
    return String.format("Objective %d Behaviours", myIndex);
  }

  @Override
  void addBlankRow(int index, DialogGridOrganizer... grid) {
    grid[0].addRowEnd(addStatesBox(), addSpritesBox(0), addComboBox(mActions, 
        Arrays.asList(new String[]{"win", "lose", "die"})),
        addTextField(mParams), addPrereqsBox());
  }

  @Override
  Consumer<ButtonType> getTodoOnOK(Sprite... s) {
    return (response -> {
      Map<String, List<String>> res = collectBehaviours();
      Objective_XML newObjective = new Objective_XML(
          myDescription.getText());
      newObjective.addOnComplete(res.get("onComplete"), null);
      newObjective.addOnFailed(res.get("onFailed"), null);
      newObjective.addPrereqs(res.get("prereqs"));
      myParent.getMyParent().getParent().getCenterPane()
          .getActiveTab().addObjective(newObjective);
  });
  }

  @Override
  void addOtherComponents(DialogGridOrganizer... grid) {
    // not needed    
  }
}