package authoring.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class ControlsDialog extends ActionsDialog {

  private List<ComboBox<Action>> myComboBoxes;
  private List<TextField> myTextFields, myParamTextFields;
  private List<Label> myDescriptions, myChecks;
  private Map<String, String> myKeyActions;
  private RightPane myParent;
  private int c = 0;

  private String[] myMouseClickedStrings = { "onClicked", "whilePressed", "onReleased" };

  
  public ControlsDialog(Sprite s, RightPane r) {
    initializeVariables(r);
    initialize (5, 1, 1,
        new Node[]{new Label("Action"), new Label("Key"), new Label("Params"), new Label(
            "Description"), new Label("Check")});
    addAddButton();
    showBox(s);
  }

  private void initializeVariables(RightPane r) {
    myParent = r;
    myComboBoxes = new ArrayList<>();
    myTextFields = new ArrayList<>();
    myParamTextFields = new ArrayList<>();
    myChecks = new ArrayList<>();
    myDescriptions = new ArrayList<>();
  }

  private void loopThroughKeyMap(Sprite s){
    myKeyActions = new HashMap<>();
    myParent.getParent().getCenterPane().getActiveTab().resetKeyActions();
    for (int i = 0; i < myTextFields.size(); i++) {
      myKeyActions.put(myTextFields.get(i).getText(), myComboBoxes.get(i).getValue()
          + ", " + myParamTextFields.get(i).getText());
      List<String> behaviours = new ArrayList<>();
      behaviours.add(String.format("%s:%s:%s", s.getID(), myComboBoxes.get(i)
          .getSelectionModel().getSelectedItem(), myParamTextFields.get(i).getText()));

      KeyAction_XML action = new KeyAction_XML(myTextFields.get(i).getText(), behaviours,
          true);

      myParent.getParent().getCenterPane().getActiveTab()
      .addKeyAction(myTextFields.get(i).getText(), action);
    }
    s.setKeyActions(myKeyActions);
    System.out.println(myKeyActions);
  }
  
  private TextField addKeyPressedField() {
    TextField result = new TextField();
    result.setEditable(false);
    result.setContextMenu(new ContextMenu());
    result.setOnKeyPressed(e -> result.setText(e.getCode().toString()));
    result.setOnMouseClicked(e -> {
      result.setText("Mouse "
          + myMouseClickedStrings[c++ % myMouseClickedStrings.length]);
    });
    myTextFields.add(result);
    return result;
  }

  public List<ComboBox<Action>> getComboBoxes() {
    return myComboBoxes;
  }

  public void PopulateComboBox(List<String> controlsList) {

  }

  public Map<String, String> getKeyActions() {
    return myKeyActions;
  }

  @Override
  String getMyTitle() {
    return "Controls";
  }

  @Override
  void addBlankRow(int index, DialogGridOrganizer... grid) {
    try {
      grid[0].addRowEnd(addActionComboBox(myComboBoxes, myDescriptions, "Sprite"), addKeyPressedField(),
          addTextField(myParamTextFields), addLabel("", myDescriptions), addLabel("", myChecks));
      this.setWidth(1200);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  Consumer<ButtonType> getTodoOnOK(Sprite... s) {
    return (response -> {
      loopThroughKeyMap(s[0]);
    });
  }

  @Override
  void addOtherComponents(DialogGridOrganizer... grid) {
    // None needed
  }
}
