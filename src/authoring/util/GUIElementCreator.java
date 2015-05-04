package authoring.util;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import authoring.panes.types.ISafePane;


public interface GUIElementCreator {
    public static TextField addLabeledTextField (String labelContent,
                                                 String defaultText,
                                                 ISafePane container,
                                                 EventHandler<? super KeyEvent> onKeyPressed,
                                                 EventHandler<? super KeyEvent> onKeyReleased,
                                                 int spacing) {
        TextField textField = createTextField(defaultText, onKeyPressed, onKeyReleased);
        addHBox(spacing, container, textField, new Label(labelContent));
        return textField;
    }
    
    public static TextField addLabeledTextField (String labelContent,
                                                 String defaultText,
                                                 ISafePane container,
                                                 int spacing, boolean setDisable) {
        TextField textField = addLabeledTextField(labelContent, defaultText, container, null, 
                                              null, spacing);
        textField.setDisable(setDisable);
        return textField;
    }

    public static HBox addHBox (int spacing, ISafePane container, Node ... content) {
        HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(content);
        container.addToChildren(hbox);
        return hbox;
    }

    public static TextField createTextField (String defaultText,
                                             EventHandler<? super KeyEvent> onKeyPressed,
                                             EventHandler<? super KeyEvent> onKeyReleased) {

        TextField textField = new TextField(defaultText);
        textField.setOnKeyReleased(onKeyReleased);
        textField.setOnKeyPressed(onKeyPressed);
        return textField;
    }

    public static TextField addTextField (String defaultText,
                                             EventHandler<? super KeyEvent> onKeyPressed,
                                             EventHandler<? super KeyEvent> onKeyReleased,
                                             ISafePane container) {

        TextField textField = new TextField(defaultText);
        textField.setOnKeyReleased(onKeyReleased);
        textField.setOnKeyPressed(onKeyPressed);
        container.addToChildren(textField);
        return textField;
    }
    
    public static Button addButton (String label,
                                    EventHandler<ActionEvent> onAction,
                                    String image,
                                    ISafePane container) {
        Button b = addButton(label, onAction, container);
        b.setGraphic(new ImageView(image));
        return b;
    }

    public static Button addButton (String label,
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

    public static void addLabel (String labelContent, ISafePane container) {
        Label label = new Label(labelContent);
        container.addToChildren(label);
    }

    public static void addImageView (ImageView imageView,
                                     EventHandler<? super MouseEvent> onMouseClicked,
                                     EventHandler<? super MouseEvent> onMouseEntered,
                                     EventHandler<? super MouseEvent> onMouseExited,
                                     ISafePane container) {

        imageView.setOnMouseClicked(onMouseClicked);
        imageView.setOnMouseEntered(onMouseEntered);
        imageView.setOnMouseExited(onMouseExited);
        container.addToChildren(imageView);
    }

}
