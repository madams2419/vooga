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


public abstract class ActionsDialog extends DataDialog {

    private static final String PROPERTIES_EXTENSION = ".properties";
    private static final String PARAM_DETAILS = "paramDetails";
    private static final String NUM_PARAMS = "numParams";
    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String ACTIONS_PROPERTIES = "Sprite.properties";

    TextField addParamTextField (List<TextField> list, List<ComboBox<Action>> comboList,
                                 List<Label> check) {
        TextField result = new TextField();
        list.add(result);
        result.setOnKeyReleased(e -> {
            int index = list.indexOf(result);
            check.get(index).setText(
                                     Boolean.toString(comboList.get(index).getValue()
                                             .checkParams(result.getText())));
        });
        return result;
    }

    ComboBox<Action> addActionComboBox (List<ComboBox<Action>> comboList,
                                        List<Label> descriptionList,
                                        String properties) throws IOException {
        ComboBox<Action> result = new ComboBox<>();
        for (Action a : grabActions(properties)) {
            result.getItems().add(a);
        }
        result.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {
            @Override
            public ListCell<Action> call (ListView<Action> p) {
                final ListCell<Action> cell = new ListCell<Action>() {
                    @Override
                    protected void updateItem (Action t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getAction());
                        }
                        else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        result.valueProperty().addListener(new ChangeListener<Action>() {
            @Override
            public void changed (ObservableValue<? extends Action> ov,
                                 Action before, Action after) {
                int index = comboList.indexOf(result);
                descriptionList.get(index).setText(after.getDescription());
            }
        });
        comboList.add(result);
        return result;
    }

    List<Action> grabActions (String properties) throws IOException {
        ReadProperties reader = new ReadProperties();
        Map<Integer, Map<String, String>> actionsMap = reader
                .getPropertiesMap(properties+PROPERTIES_EXTENSION);
        List<Action> actionsList = new ArrayList<>();
        actionsLoop(actionsMap, actionsList);
        return actionsList;
    }

    private void actionsLoop (Map<Integer, Map<String, String>> actionMap, List<Action> actionsList) {
        for (Map<String, String> action : actionMap.values()) {
            actionsList.add(new Action(action.get(NAME), action
                    .get(DESCRIPTION), action.get(PARAM_DETAILS), Integer.parseInt(action
                    .get(NUM_PARAMS))));
        }
    }

}
