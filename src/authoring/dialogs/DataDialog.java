package authoring.dialogs;

import java.util.List;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;

/**
 * Superclass for the dialogs.
 * 
 * @author Natalie, Andrew
 *
 */

public abstract class DataDialog extends Dialog<ButtonType> {

    private static final String ADD = "Add";
    private static final int BOTTOM_SPACING = 25;
    private int myIndex;
    private DialogGridOrganizer myGrid;

    void initialize (Sprite s, int sizeOfGridOrganizer, Node[] titleLabelRow, int numCols) {
        myGrid = new DialogGridOrganizer(sizeOfGridOrganizer);
        myGrid.addRowEnd(titleLabelRow);

        for (int i = 0; i < numCols; i++) {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addBlankRow(myGrid, myIndex++);
        }

        addAddButton();
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().setContent(myGrid);
        showBox(s);
    }

    void addAddButton() {
        ButtonType addButton = new ButtonType(ADD);
        getDialogPane().getButtonTypes().add(addButton);
        lookupAddButton(addButton);
    }
    
    void lookupAddButton (ButtonType b) {
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        System.out.println("add button: " + addButton);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addBlankRow(myGrid, myIndex++);
            event.consume();
        });
    }

    abstract void addBlankRow (DialogGridOrganizer grid, int index);

    abstract Consumer<ButtonType> getTodoOnOK ();

    public void showBox (Sprite s) {
        this.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(getTodoOnOK());
    }
    
    ComboBox<String> addComboBox (List<ComboBox<String>> list, ObservableList<String> toAdd) {
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(toAdd);
        list.add(box);
        return box;
    }
    
    Button addButton (String label, EventHandler<MouseEvent> toDoOnClick, List<Button> list) {
        Button button = new Button(label);
        list.add(button);
        button.setOnMouseClicked(toDoOnClick);
        return button;
    }
    
    Label addLabel (String content, List<Label> list) {
        Label label = new Label(content);
        list.add(label);
        return label;
    }
    
    TextField addTextField (List<TextField> list) {
        TextField textField = new TextField();
        list.add(textField);
        return textField;
    }
    
    void addRow (int index) {
        addBlankRow(myGrid, index);
    }

}
