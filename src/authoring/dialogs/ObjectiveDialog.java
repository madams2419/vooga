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

        private static final String SET_STATE = "setState";
        private static final String JUMP = "jump";
        private static final String MOVE_FORWARD = "moveForward";
        private static final String INCREMENT_SCORE = "incrementScore";
        private static final String EMPTY_STRING = "";
        private static final String TITLE = "Objective %d Behaviors";
        private static final String ACTION_SPLIT_BY = ", ";
        private static final String ACTION_FORMAT = "%s:%s:%s";
        private static final String PREREQS = "prereqs";
        private static final String ON_FAILED = "onFailed";
        private static final String ON_COMPLETE = "onComplete";
        private static final String PRE_REQUISITES = "Pre-requisites";
        private static final String PARAMETERS = "Parameters";
        private static final String ACTION = "Action";
        private static final String SPRITE = "Sprite";
        private static final String COMPLETE_FAILED = "Complete/Failed";
        private static final List<String> myActionsList = Arrays.asList(new String[]{INCREMENT_SCORE, 
                                                          MOVE_FORWARD, JUMP, SET_STATE});
        
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
		           new Node[] { new Label(COMPLETE_FAILED), new Label(SPRITE),
		                        new Label(ACTION), new Label(PARAMETERS), new Label(
		                                PRE_REQUISITES)});
		addAddButton();
		showBox();
	}

    private Map<String, List<String>> collectBehaviours() {
		Map<String, List<String>> mResult = new HashMap<>();
		mResult.put(ON_COMPLETE, new ArrayList<String>());
		mResult.put(ON_FAILED, new ArrayList<String>());
		String prereq = mPrereqs.get(0).getValue();
		mResult.put(PREREQS, Arrays.asList(new String[]{prereq == null ? EMPTY_STRING : prereq}));
		for (int i = 0; i < mActions.size(); i++) {
			String action = String.format(ACTION_FORMAT, mSpriteBoxes.get(i)
					.getSelectionModel().getSelectedItem().split(ACTION_SPLIT_BY)[1], 
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
		    Arrays.asList(new String[]{ON_COMPLETE, ON_FAILED}));
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
    return String.format(TITLE, myIndex);
  }

  @Override
  void addBlankRow(int index, DialogGridOrganizer... grid) {
    grid[0].addRowEnd(addStatesBox(), addSpritesBox(), addComboBox(mActions, 
        myActionsList), addTextField(mParams), addPrereqsBox());
  }
  
  @Override
  void addAdditionalBlankRow(int index, DialogGridOrganizer... grid) {
    grid[0].addRowEnd(addStatesBox(), addSpritesBox(), addComboBox(mActions, 
        myActionsList), addTextField(mParams));
  }

  @Override
  Consumer<ButtonType> getTodoOnOK(Sprite... s) {
    return (response -> {
      Map<String, List<String>> res = collectBehaviours();
      Objective_XML newObjective = new Objective_XML(
          myDescription.getText());
      newObjective.addOnComplete(res.get(ON_COMPLETE), null);
      newObjective.addOnFailed(res.get(ON_FAILED), null);
      newObjective.addPrereqs(res.get(PREREQS));
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