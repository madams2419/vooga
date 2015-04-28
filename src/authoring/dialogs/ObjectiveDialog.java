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
 * @author Daniel, Natalie, refactored by Andrew
 *
 */
public class ObjectiveDialog extends DataDialog {

	private List<ComboBox<String>> mActions = new ArrayList<>();
	private List<ComboBox<String>> mPrereqs = new ArrayList<>();
	private List<ComboBox<String>> mSpriteBoxes = new ArrayList<>();
	private List<ComboBox<String>> mStates = new ArrayList<>();
	private List<TextField> mParams = new ArrayList<>();
	private List<Sprite> mSprites = new ArrayList<>();
	private List<String> mSpriteNames = new ArrayList<>();
	private TextField myDescription;

	private ObjectivePane myParent;
	private int selected, myIndex;

	public ObjectiveDialog(ObjectivePane parent, int objectiveNumber, List<Sprite> sprites) {
		myParent = parent;
		myIndex = objectiveNumber;
		myDescription = new TextField();
		mSprites = sprites;
		setupSprites();
		initialize(5, 1, 1,
		           new Node[] { new Label("Complete/Failed"), new Label("Sprite"),
		                        new Label("Action"), new Label("Parameters"), new Label(
		                                "Pre-requisites")});
		addAddButton();
		showBox();
	}

    private Map<String, List<String>> collectBehaviours() {
		Map<String, List<String>> mResult = new HashMap<>();
		mResult.put("onComplete", new ArrayList<String>());
		mResult.put("onFailed", new ArrayList<String>());
		mResult.put("prereqs", Arrays.asList(new String[]{mPrereqs.get(0).getValue()}));
		for (int i = 0; i < mActions.size(); i++) {
			String action = String.format("%s:%s:%s", mSpriteBoxes.get(i)
					.getSelectionModel().getSelectedItem().split(", ")[1], 
					mActions.get(i)
					.getSelectionModel().getSelectedItem(), mParams.get(i)
					.getText());
			mResult.get(mStates.get(i).getSelectionModel().getSelectedItem())
					.add(action);
		}
		return mResult;
	}

	public ComboBox<String> addSpritesBox() {
	    ComboBox<String> box = addComboBox(mSpriteBoxes, mSpriteNames);
	    return box;
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
		mSpriteBoxes.get(selected).getItems().add(s.toString());
	}

  @Override
  String getMyTitle() {
    return String.format("Objective %d Behaviours", myIndex);
  }

  @Override
  void addBlankRow(int index, DialogGridOrganizer... grid) {
    grid[0].addRowEnd(addStatesBox(), addSpritesBox(), addComboBox(mActions, 
        Arrays.asList(new String[]{"win", "lose", "die"})),
        addTextField(mParams), addPrereqsBox());
  }
  
  @Override
  void addAdditionalBlankRow(int index, DialogGridOrganizer... grid) {
    grid[0].addRowEnd(addStatesBox(), addSpritesBox(), addComboBox(mActions, 
        Arrays.asList(new String[]{"win", "lose", "die"})),
        addTextField(mParams));
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
      .getActiveTab().setObjective(newObjective, myIndex);
  });
  }

  @Override
  void addOtherComponents(DialogGridOrganizer... grid) {
    // not needed    
  }
  
  private void setupSprites () {
      mSpriteNames = new ArrayList<>();
      for (Sprite sprite : mSprites) {
          mSpriteNames.add(sprite.toString());
      }
      
  }

public void update () {
    setupSprites();
    updateLists();
    this.showBox();
}

private void updateLists () {
    List<String> prereqList = myParent.getObjectives().stream()
            .map(e -> {
                return e.getText();
        }).collect(Collectors.toList());
    
    updateList(mSpriteBoxes, mSpriteNames);
    updateList(mPrereqs, prereqList);
}
private void updateList(List<ComboBox<String>> comboBoxes, List<String> list) {
    for (ComboBox<String> box : comboBoxes) {
        String value = box.getValue();
        box.getItems().clear();
        box.getItems().addAll(list);
        box.setValue(value);
    }
}


}