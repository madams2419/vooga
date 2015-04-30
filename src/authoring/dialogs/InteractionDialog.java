package authoring.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.fileBuilders.Collision_XML;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;


/**
 * Superclass for interaction dialogs
 * 
 * @author Andrew, Natalie
 *
 */

public abstract class InteractionDialog extends ActionsDialog {
    private static final String COLON = ":";
    private static final String SPRITE2 = "sprite:";
    private static final String SIMPLE_RESOLVER = "SimpleResolver:";
    private static final int BOTTOM_SPACING = 31;
    static final String BLANK = "";
    // static final String SPRITE = "Sprite";

    private RightPane myParent;
    List<List<ComboBox<Action>>> myComboBoxes = new ArrayList<>();
    List<List<Label>> myDescriptions = new ArrayList<>();
    List<List<Label>> myChecks = new ArrayList<>();
    List<List<TextField>> myParams = new ArrayList<>();
    List<ButtonType> myAddButtons = new ArrayList<>();

    abstract void addToSprites (Sprite a, Sprite b, List<Map<Action, String>> mySpriteInteractions);

    abstract void populateDialog (Sprite a, Sprite b) throws IOException;

    private void collectProperties (Sprite a, Sprite b) {
        List<Map<Action, String>> mySpriteInteractions = new ArrayList<>();
        for (int i = 0; i < getNumGrids(); i++) {
            mySpriteInteractions.add(new HashMap<>());
        }

        List<String> totalInteractions = new ArrayList<>();
        loopThroughMaps(mySpriteInteractions, totalInteractions, a, b);
        addToSprites(a, b, mySpriteInteractions);
        myParent.getParent().getCenterPane().getActiveTab()
                .addCollision(new Collision_XML(a, b, totalInteractions), a, b);
    }

    void initializeInstanceVariables (RightPane parent, Sprite a, Sprite b) {
        myParent = parent;
        for (int k = 0; k < getNumGrids(); k++) {
            myComboBoxes.add(new ArrayList<>());
            myDescriptions.add(new ArrayList<>());
            myChecks.add(new ArrayList<>());
            myParams.add(new ArrayList<>());
            ButtonType bType = new ButtonType(getButtonLabel(k));
            this.getDialogPane().getButtonTypes().add(bType);
            myAddButtons.add(bType);
        }
    }

    abstract String getButtonLabel (int k);

    private void loopThroughMaps (List<Map<Action, String>> mySpriteInteractions,
                                  List<String> totalInteractions, Sprite ... s) {

        for (int k = 0; k < getNumGrids(); k++) {
            for (int i = 0; i < myDescriptions.get(k).size(); i++) {
                mySpriteInteractions.get(k).put(myComboBoxes.get(k).get(i).getValue(),
                                                myParams.get(k).get(i).getText());
                totalInteractions.add(SIMPLE_RESOLVER + SPRITE2 + s[k].getID()
                                      + COLON + myComboBoxes.get(k).get(i).getValue().getAction() +
                                      COLON
                                      + myParams.get(k).get(i).getText());
            }
        }
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite ... s) {
        return response -> collectProperties(s[0], s[1]);
    }

    void loopThroughInteractions (List<Map<Action, String>> mySpriteInteractions)
                                                                                 throws IOException {
        DialogGridOrganizer[] dialog = getGrid();
        for (int k = 0; k < getNumGrids(); k++) {
            for (Action act : mySpriteInteractions.get(k).keySet()) {
                ComboBox<Action> combo =
                        addActionComboBox(myComboBoxes.get(k), myDescriptions.get(k), getProperty());
                combo.setValue(act);
                TextField param =
                        addParamTextField(myParams.get(k), myComboBoxes.get(k), myChecks.get(k));
                param.setText(mySpriteInteractions.get(k).get(act));
                dialog[k].addRowEnd(combo, param, addLabel(BLANK, myDescriptions.get(k)),
                                    addLabel(BLANK, myChecks.get(k)));
            }
        }
    }

    @Override
    void addOtherComponents (DialogGridOrganizer ... grid) {
        for (int i = 0; i < getNumGrids(); i++) {
            int k = i;
            addButton(myAddButtons.get(i),
                      e -> {
                          this.setHeight(this.getHeight() + BOTTOM_SPACING);
                          try {
                              grid[k].addRowEnd(getRowEnd(k));
                          }
                          catch (Exception e1) {
                              e1.printStackTrace();
                          }
                          this.setWidth(1200);
                          e.consume();
                      });
        }
    }

    Node[] getRowEnd(int k) throws IOException {
        return new Node[] {(addActionComboBox(myComboBoxes.get(k),
                                            myDescriptions.get(k),
                                            getProperty())),
                          addParamTextField(myParams.get(k),
                                            myComboBoxes.get(k),
                                            myChecks.get(k)),
                          addLabel(InteractionDialog.BLANK,
                                   myDescriptions.get(k)),
                          addLabel(InteractionDialog.BLANK, myChecks.get(k)) };
    }

    abstract String getProperty ();

    abstract int getNumGrids ();

    private void addButton (ButtonType b, EventHandler<ActionEvent> e) {
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        addButton.addEventFilter(ActionEvent.ACTION, e);
    }
}
