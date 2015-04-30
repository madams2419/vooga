package authoring.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;


/**
 * Creates the interaction dialog between two sprites.
 * 
 * @author Andrew Sun, Natalie
 *
 */
public class InteractionsSpritesDialog extends InteractionDialog {
    private static final String TITLE = "Interaction Editing Dialog";
    private static final String CHECK = "Check";
    private static final String DESCRIPTION2 = "Description";
    private static final String PARAMS = "Params";
    private static final String ACTION = "Action";
    private static final String ACTION2 = " Action";
    private static final String PROPERTY = "Sprite";
    private static final String ADD_SPRITE = "Add Sprite ";
    private static final int NUM_GRIDS = 2;
    private static final int GRID_ORGANIZER_SIZE = 4;
    private static final int INITIAL_ROWS = 0;

    public InteractionsSpritesDialog (RightPane parent, Sprite a, Sprite b)
        throws IOException {

        initializeInstanceVariables(parent, a, b);
        initialize(GRID_ORGANIZER_SIZE, INITIAL_ROWS, NUM_GRIDS,
                   createNewLabels());
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
        return new Node[] { new Label(ACTION), new Label(PARAMS),
                           new Label(DESCRIPTION2), new Label(CHECK) };
    }

    @Override
    void populateDialog (Sprite a, Sprite b) throws IOException {
        List<Map<Action, String>> mySpriteInteractions = new ArrayList<>();
        mySpriteInteractions.add(a.getInteractionMap().getOrDefault(b, new HashMap<>()));
        mySpriteInteractions.add(b.getInteractionMap().getOrDefault(a, new HashMap<>()));
        loopThroughInteractions(mySpriteInteractions);
    }

    @Override
    void addToSprites (Sprite a, Sprite b, List<Map<Action, String>> mySpriteInteractions) {
        a.addInteraction(b, mySpriteInteractions.get(0));
        b.addInteraction(a, mySpriteInteractions.get(1));
    }

    @Override
    String getMyTitle () {
        return TITLE;
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
    String getButtonLabel(int k) {
        return (ADD_SPRITE + Integer.toString(k) + ACTION2);
    }

    @Override
    String getSummary (int k, int i, Sprite [] s) {
        return
        InteractionDialog.SIMPLE_RESOLVER + getProperty().toLowerCase() + COLON + s[k].getID()
        + COLON + myComboBoxes.get(k).get(i).getValue().getAction() +
        COLON + myParams.get(k).get(i).getText();

    }

}
