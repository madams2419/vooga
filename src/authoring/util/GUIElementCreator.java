package authoring.util;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Sprite;
import authoring.panes.types.ISafePane;
import authoring.panes.types.SafeHBox;


public interface GUIElementCreator {
    public static TextField createLabeledTextField (String labelContent, ISafePane container, int spacing) {
        SafeHBox hbox = new SafeHBox(spacing);
        TextField textField = new TextField();
        Label label = new Label(labelContent);
        hbox.addToChildren(label, textField);
        container.addToChildren(hbox);
        return textField;
    }

    public static TextField createLabeledTextField (String label, String defaultText, ISafePane container,
                                                    int spacing) {
        TextField result = createLabeledTextField(label, container, spacing);
        result.setText(defaultText);
        return result;
    }

    public static Button createButton (Sprite sprite,
                                       String label,
                                       EventHandler<ActionEvent> onAction,
                                       String image,
                                       ISafePane container) {
        Button b = createButton(label, onAction, container);
        b.setGraphic(new ImageView(image));
        return b;
    }

    public static Button createButton (String label,
                                       EventHandler<ActionEvent> onAction,
                                       ISafePane container) {
        Button button = new Button(label);
        button.setOnAction(onAction);
        container.addToChildren(button);
        return button;
    }

    public static ObjectProperty<String> addComboBoxToPane (List<String> options,
                                                            String initialValue, ISafePane container) {
        final ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll(options);
        container.addToChildren(actionComboBox);
        setInitialComboBoxValue(actionComboBox, initialValue);
        return actionComboBox.valueProperty();
    }

    public static void setInitialComboBoxValue (ComboBox<String> comboBox,
                                                 String value) {
        if (value != null) {
            comboBox.setValue(value);
        }
    }

    public static void createLabel (String labelContent, ISafePane container) {
        Label label = new Label(labelContent);
        container.addToChildren(label);
    }
}
