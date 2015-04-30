package authoring.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;


public class InteractionObjectiveDialog extends InteractionDialog {

    private static final String OBJECTIVE_ID = "Objective ID";
    private static final String TITLE = "Objective Editing Dialog";
    private static final String CHECK = "Check";
    private static final String DESCRIPTION2 = "Description";
    private static final String PARAMS = "Params";
    private static final String ACTION = "Action";
    private static final String ACTION2 = " Action";
    private static final String ADD_OBJECTIVE = "Add Objective "; 
    private static final int NUM_GRIDS = 1;
    private static final int GRID_ORGANIZER_SIZE = 5;
    private static final int INITIAL_ROWS = 0;
    private static final String PROPERTY = "Objective";
    private List<ComboBox<String>> myObjectiveComboBoxes;
    private List<String> myObjectiveStates;

    public InteractionObjectiveDialog (RightPane parent, Sprite a, Sprite b)
        throws IOException {

        initializeInstanceVariables(parent, a, b);
        initialize(GRID_ORGANIZER_SIZE, INITIAL_ROWS, NUM_GRIDS,
                   createNewLabels());
        myObjectiveStates = new ArrayList<>();
        myObjectiveComboBoxes = new ArrayList<>();
        for (Action action : grabActions(PROPERTY)) {
            myObjectiveStates.addAll(Arrays.asList(action.getParamDescription().split(", ")));
        }
        populateDialog(a, b);
        showBox(a, b);
    }

    public Node[][] createNewLabels () {
        Node[][] ret = new Node[NUM_GRIDS][createNewLabel().length];
        for (int i = 0; i < NUM_GRIDS; i++) {
            ret[i] = createNewLabel();
        }
        return ret;
    }

    public Node[] createNewLabel () {
        return new Node[] { new Label(ACTION), new Label(OBJECTIVE_ID), new Label(PARAMS),
                           new Label(DESCRIPTION2), new Label(CHECK) };
    }

    @Override
    void populateDialog (Sprite a, Sprite b) throws IOException {
        List<Map<Action, String>> mySpriteInteractions = new ArrayList<>();
        mySpriteInteractions.add(a.getObjectivesMap().getOrDefault(b, new HashMap<>()));
        mySpriteInteractions.add(b.getObjectivesMap().getOrDefault(a, new HashMap<>()));
        loopThroughInteractions(mySpriteInteractions);
    }

    @Override
    void addToSprites (Sprite a, Sprite b, List<Map<Action, String>> mySpriteInteractions) {
        a.addObjective(b, mySpriteInteractions.get(0));
        b.addObjective(a, mySpriteInteractions.get(0));
    }

    @Override
    String getMyTitle () {
        return TITLE;
    }

    @Override
    Node[] getRowEnd (int k) throws IOException {
        return new Node[] { (addActionComboBox(myComboBoxes.get(k),
                                               myDescriptions.get(k),
                                               getProperty())),
                           addParamTextField(myParams.get(k),
                                             myComboBoxes.get(k),
                                             myChecks.get(k)),
                           addComboBox(myObjectiveComboBoxes, myObjectiveStates),
                           addLabel(InteractionDialog.BLANK,
                                    myDescriptions.get(k)),
                           addLabel(InteractionDialog.BLANK, myChecks.get(k)) };
    }

    @Override
    void addBlankRow (int index, DialogGridOrganizer ... grid) {
        // Not needed
    }

    @Override
    int getNumGrids () {
        return NUM_GRIDS;
    }

    @Override
    String getProperty () {
        return PROPERTY;
    }

    @Override
    String getButtonLabel (int k) {
        return (ADD_OBJECTIVE + Integer.toString(k) + ACTION2);
    }

}
