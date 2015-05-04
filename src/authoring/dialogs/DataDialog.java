// This entire file is part of my masterpiece.
// Andrew Sun

package authoring.dialogs;

import java.util.List;
import java.util.function.Consumer;

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
import javafx.scene.layout.HBox;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;

/**
 * Superclass for the dialogs.
 * @author Natalie, Andrew Sun
 *
 */

public abstract class DataDialog extends Dialog<ButtonType> {

    private static final String ADD = "Add";
    private static final String BLANK = "";
    private int myBottomSpacing = 25; // default value
    private int myIndex;
    private DialogGridOrganizer[] myGrids;

    void initialize (int sizeOfGridOrganizer, int numCols, int numGrids,
        Node[]... titleLabelRow) {
        setTitle(getMyTitle());
        HBox myHBox = new HBox();
        myGrids = new DialogGridOrganizer[numGrids];
        for (int i = 0; i < numGrids; i++){
          DialogGridOrganizer myGrid = new DialogGridOrganizer(sizeOfGridOrganizer);
          myGrid.addRowEnd(titleLabelRow[i]);
          myGrids[i] = myGrid;
          myHBox.getChildren().add(myGrid);
        }

        for (int i = 0; i < numCols; i++) {
            this.setHeight(this.getHeight() + myBottomSpacing);
            addBlankRow(myIndex++, myGrids);
        }
        
        addOtherComponents(myGrids);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().setContent(myHBox);
    }

    abstract String getMyTitle ();

    void addAddButton() {
        ButtonType addButton = new ButtonType(ADD);
        getDialogPane().getButtonTypes().add(addButton);
        lookupAddButton(addButton);
    }
    
    void lookupAddButton (ButtonType b) {
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        System.out.println("add button: " + addButton);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            this.setHeight(this.getHeight() + myBottomSpacing);
            addRow(myIndex++);
            event.consume();
        });
    }

    public void showBox (Sprite... s) {
        this.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(getTodoOnOK(s));
    }
    
    ComboBox<String> addComboBox (List<ComboBox<String>> list, List<String> toAdd) {
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
        return addTextField(list, BLANK);
    }
    
    TextField addTextField (List<TextField> list, String value) {
        TextField textField = new TextField();
        textField.setText(value);
        list.add(textField);
        return textField;
    }
    
    DialogGridOrganizer[] getGrid(){
      return myGrids;
    }
    
    void setBottomSpacing(int i){
      myBottomSpacing = i;
    }
    
    void addRow (int index) {
        addBlankRow(index, myGrids);
    }
    
    abstract void addBlankRow (int index, DialogGridOrganizer... grid);
    
    abstract Consumer<ButtonType> getTodoOnOK (Sprite... s);

    abstract void addOtherComponents(DialogGridOrganizer... grid);

}
