// This entire file is part of my masterpiece.
// Natalie Chanfreau

package authoring.util;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import authoring.panes.types.ISafePane;


/**
 * This utility creates components of a GUI.
 * 
 * @author Natalie Chanfreau
 *
 */
public interface GUIElementCreator {

    /**
     * Creates and adds a checkbox to an ISafePane
     * 
     * @param controls
     * @param label
     * @param initial
     * @param selectedChangeListener
     * @param container
     * @return
     */
    public static CheckBox addCheckBox (Button controls,
                                        String label,
                                        boolean initial,
                                        ChangeListener<? super Boolean> selectedChangeListener,
                                        ISafePane container) {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(initial);
        checkBox.selectedProperty().addListener(selectedChangeListener);
        container.addToChildren(checkBox);
        return checkBox;
    }

    /**
     * Creates and adds a labeled text field to an ISafePane
     * 
     * @param labelContent
     * @param defaultText
     * @param container
     * @param onKeyPressed
     * @param onKeyReleased
     * @param spacing
     * @return
     */
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

    /**
     * Creates and adds a labeled text field to an ISafePane with the option of setting it to be
     * initially disabled
     * 
     * @param labelContent
     * @param defaultText
     * @param container
     * @param spacing
     * @param setDisable
     * @return
     */
    public static TextField addLabeledTextField (String labelContent,
                                                 String defaultText,
                                                 ISafePane container,
                                                 int spacing, boolean setDisable) {
        TextField textField = addLabeledTextField(labelContent, defaultText, container, null,
                                                  null, spacing);
        textField.setDisable(setDisable);
        return textField;
    }

    /**
     * Creates and adds an HBox with its content to an ISafePane
     * 
     * @param spacing
     * @param container
     * @param content
     * @return
     */
    public static HBox addHBox (int spacing, ISafePane container, Node ... content) {
        HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(content);
        container.addToChildren(hbox);
        return hbox;
    }

    /**
     * Creates a labeled text field
     * 
     * @param defaultText
     * @param onKeyPressed
     * @param onKeyReleased
     * @return
     */
    public static TextField createTextField (String defaultText,
                                             EventHandler<? super KeyEvent> onKeyPressed,
                                             EventHandler<? super KeyEvent> onKeyReleased) {

        TextField textField = new TextField(defaultText);
        textField.setOnKeyReleased(onKeyReleased);
        textField.setOnKeyPressed(onKeyPressed);
        return textField;
    }

    /**
     * Creates and adds a text field to an ISafePane
     * 
     * @param defaultText
     * @param onKeyPressed
     * @param onKeyReleased
     * @param container
     * @return
     */
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

    /**
     * Creates and adds a button to an ISafePane
     * 
     * @param label
     * @param onAction
     * @param image
     * @param container
     * @return
     */
    public static Button addButton (String label,
                                    EventHandler<ActionEvent> onAction,
                                    String image,
                                    ISafePane container) {
        Button b = addButton(label, onAction, container);
        b.setGraphic(new ImageView(image));
        return b;
    }

    /**
     * Creates and adds a button to an ISafePane
     * 
     * @param label
     * @param onAction
     * @param container
     * @return
     */
    public static Button addButton (String label,
                                    EventHandler<ActionEvent> onAction,
                                    ISafePane container) {
        Button button = new Button(label);
        button.setOnAction(onAction);
        container.addToChildren(button);
        return button;
    }

    /**
     * Creates and adds a ComboBox to an ISafePane
     * 
     * @param options
     * @param initialValue
     * @param container
     * @return
     */
    public static ObjectProperty<String> addComboBoxToPane (List<String> options,
                                                            String initialValue, ISafePane container) {
        final ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll(options);
        container.addToChildren(actionComboBox);
        setInitialComboBoxValue(actionComboBox, initialValue);
        return actionComboBox.valueProperty();
    }

    /**
     * Sets and initial value for a ComboBox
     * 
     * @param comboBox
     * @param value
     */
    public static void setInitialComboBoxValue (ComboBox<String> comboBox,
                                                String value) {
        if (value != null) {
            comboBox.setValue(value);
        }
    }

    /**
     * Creates and adds a label to an ISafePane
     * 
     * @param labelContent
     * @param container
     */
    public static void addLabel (String labelContent, ISafePane container) {
        Label label = new Label(labelContent);
        container.addToChildren(label);
    }

    /**
     * Creates and adds an ImageView with certain EventHandlers to an ISafePane
     * 
     * @param imageView
     * @param onMouseClicked
     * @param onMouseEntered
     * @param onMouseExited
     * @param container
     */
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
