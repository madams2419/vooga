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
 * Creates the interaction dialog between two sprites.
 * 
 * @author Andrew Sun
 *
 */
public class InteractionsDialog extends ActionsDialog {
    private RightPane myParent;

    List<List<ComboBox<Action>>> myComboBoxes = new ArrayList<>();
    List<List<Label>> myDescriptions = new ArrayList<>();
    List<List<Label>> myChecks = new ArrayList<>();
    List<List<TextField>> myParams = new ArrayList<>();
    List<ButtonType> myAddButtons = new ArrayList<>();

    private static final int BOTTOM_SPACING = 31;
    private static final int GRID_ORGANIZER_SIZE = 4;
    private static final int INITIAL_ROWS = 0;
    private static final int NUM_GRIDS = 2;

    // private int i = 0;

    public InteractionsDialog (RightPane parent, Sprite a, Sprite b)
        throws IOException {

        initializeInstanceVariables(parent, a, b);
        initialize(GRID_ORGANIZER_SIZE, INITIAL_ROWS, NUM_GRIDS,
                   new Node[] { new Label("Action"), new Label("Params"),
                               new Label("Description"), new Label("Check") },
                   new Node[] { new Label("Action"), new Label("Params"),
                               new Label("Description"), new Label("Check") });
        populateDialog(a, b);
        showBox(a, b);
    }

    private void initializeInstanceVariables (RightPane parent, Sprite a, Sprite b) {
        myParent = parent;
        for (int k = 0; k < NUM_GRIDS; k++) {
            myComboBoxes.add(new ArrayList<>());
            myDescriptions.add(new ArrayList<>());
            myChecks.add(new ArrayList<>());
            myParams.add(new ArrayList<>());
            ButtonType bType = new ButtonType("Add Sprite " +
                                              Integer.toString(k) + " Action");
            this.getDialogPane().getButtonTypes().add(bType);
            myAddButtons.add(bType);
        }
    }

    private void populateDialog (Sprite a, Sprite b) throws IOException {
        List<Map<Action, String>> mySpriteInteractions = new ArrayList<>();
        mySpriteInteractions.add(a.getInteractionMap().getOrDefault(b, new HashMap<>()));
        mySpriteInteractions.add(b.getInteractionMap().getOrDefault(a, new HashMap<>()));
        loopThroughInteractions(mySpriteInteractions);
    }

    private void loopThroughInteractions (List<Map<Action, String>> mySpriteInteractions) throws IOException {
        DialogGridOrganizer[] dialog = getGrid();
        for (int k = 0; k < NUM_GRIDS; k++) {
            for (Action act : mySpriteInteractions.get(k).keySet()) {
                ComboBox<Action> combo = addActionComboBox(myComboBoxes.get(k), myDescriptions.get(k));
                combo.setValue(act);
                TextField param = addParamTextField(myParams.get(k), myComboBoxes.get(k), myChecks.get(k));
                param.setText(mySpriteInteractions.get(k).get(act));
                dialog[k].addRowEnd(combo, param, addLabel("", myDescriptions.get(k)),
                                    addLabel("", myChecks.get(k)));
            }
        }
    }

    private void collectProperties (Sprite a, Sprite b) {
        List<Map<Action, String>> mySpriteInteractions = new ArrayList<>();
        for (int i = 0; i < NUM_GRIDS; i++) {
            mySpriteInteractions.add(new HashMap<>());
        }

        List<String> totalInteractions = new ArrayList<>();
        loopThroughMaps(mySpriteInteractions, totalInteractions, a, b);
        a.addInteraction(b, mySpriteInteractions.get(0));
        b.addInteraction(a, mySpriteInteractions.get(1));
        myParent.getParent().getCenterPane().getActiveTab()
                .addCollision(new Collision_XML(a, b, totalInteractions), a, b);
    }

    private void loopThroughMaps (List<Map<Action, String>> mySpriteInteractions,
                                  List<String> totalInteractions, Sprite ... s) {

        for (int k = 0; k < NUM_GRIDS; k++) {
            for (int i = 0; i < myDescriptions.get(k).size(); i++) {
                mySpriteInteractions.get(k).put(myComboBoxes.get(k).get(i).getValue(),
                                                myParams.get(k).get(i).getText());
                totalInteractions.add("SimpleResolver:" + "sprite:" + s[k].getID()
                                      + ":" + myComboBoxes.get(k).get(i).getValue().getAction() +
                                      ":"
                                      + myParams.get(k).get(i).getText());
            }
        }
    }

    private void addButton (ButtonType b, EventHandler<ActionEvent> e) {
        final Button addButton = (Button) this.getDialogPane().lookupButton(b);
        addButton.addEventFilter(ActionEvent.ACTION, e);
    }

    @Override
    String getMyTitle () {
        return "Interaction Editing Dialog";
    }

    @Override
    Consumer<ButtonType> getTodoOnOK (Sprite ... s) {
        return response -> collectProperties(s[0], s[1]);
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer ... grid) {
        // Not needed
    }

    @Override
    void addOtherComponents (DialogGridOrganizer ... grid) {
        // i = 0;
        for (int i = 0; i < NUM_GRIDS; i++) {
            int k = i;
            addButton(myAddButtons.get(i),
                      e -> {
                          this.setHeight(this.getHeight() + BOTTOM_SPACING);
                          try {
                              grid[k].addRowEnd(addActionComboBox(myComboBoxes.get(k),
                                                                  myDescriptions.get(k)),
                                                addParamTextField(myParams.get(k),
                                                                  myComboBoxes.get(k),
                                                                  myChecks.get(k)),
                                                addLabel("", myDescriptions.get(k)),
                                                addLabel("", myChecks.get(k)));
                          }
                          catch (Exception e1) {
                              e1.printStackTrace();
                          }
                          this.setWidth(1200);
                          e.consume();
                      });
        }
    }

}
