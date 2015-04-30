package authoring.dialogs;

import game_engine.annotation.ReadProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import authoring.dataEditors.Action;

public abstract class ActionsDialog extends DataDialog{  
  
  TextField addParamTextField(List<TextField> list, List<ComboBox<Action>> comboList,
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
  
  ComboBox<Action> addActionComboBox(List<ComboBox<Action>> comboList,
      List<Label> descriptionList) throws IOException {
    ComboBox<Action> result = new ComboBox<>();
    for (Action a : grabActions()) {
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

  List<Action> grabActions() throws IOException {
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
