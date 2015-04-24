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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;


public abstract class DataDialog extends Dialog<ButtonType> {

    private static final String ADD = "Add";
    private static final int BOTTOM_SPACING = 25;
    private int myIndex;

    void initialize(Sprite s, int sizeOfGridOrganizer, Node[] titleLabelRow, ObservableList<String> comboBoxContent) {
        DialogGridOrganizer grid = new DialogGridOrganizer(sizeOfGridOrganizer);
        grid.addRowEnd(titleLabelRow);

        for (int i = 0; i < getListContent().size(); i++) {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addRow(grid, myIndex++);
        }
        
        this.getDialogPane().setContent(grid);

//        ButtonType b = addAddButton(grid);
//        this.getDialogPane().getButtonTypes().addAll(b, ButtonType.OK, ButtonType.CANCEL);

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        showBox(s);
   
    }
    
    ButtonType addAddButton(DialogGridOrganizer grid) {
        ButtonType b = new ButtonType(ADD);
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            this.setHeight(this.getHeight() + BOTTOM_SPACING);
            addRow(grid, myIndex++);
            event.consume();
        });
        return b;
    }

    abstract void initializeEverything (ObservableList<String> comboBoxContent);

    abstract ObservableList<String> getListContent ();

    abstract void addRow (DialogGridOrganizer grid, int index);

    abstract Consumer<ButtonType> getTodoOnOK();
    
    abstract List<ComboBox<String>> getComboBoxes ();

    public void showBox (Sprite s) {
        Consumer<ButtonType> todoOnOK = getTodoOnOK();
        this.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(todoOnOK);
    }
    
    ComboBox<String> addComboBox () {
        List<ComboBox<String>> myComboBoxes = getComboBoxes();
        ObservableList<String> toAdd = getListContent();
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(toAdd);
        myComboBoxes.add(box);
        return box;
    }

    TextField addTextField (EventHandler<KeyEvent> todoOnKeyPressed, List<TextField> myTextFields) {
        TextField result = new TextField();
        result.setEditable(false);
        result.setOnKeyPressed(todoOnKeyPressed);
        myTextFields.add(result);
        return result;
    }

}
