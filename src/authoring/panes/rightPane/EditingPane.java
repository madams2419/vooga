package authoring.panes.rightPane;

import javafx.scene.Scene;
import authoring.panes.types.SafeVBox;
import authoring.userInterface.ClickHandler;
import authoring.util.GUIElementCreator;


/**
 * This is the superclass for any specific pane on the right side of the screen.
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public abstract class EditingPane extends SafeVBox {

    private static final String RETURN_TO_CREATION_METHOD = "switchToCharacterCreationPane";
    private Scene myScene;
    protected RightPane myParent;

    public EditingPane (Scene scene, RightPane parent) {
        assert (scene != null);
        myScene = scene;
        myParent = parent;
    }

    public Scene getMyScene () {
        return myScene;
    }

    public RightPane getMyParent () {
        return myParent;
    }

    @SuppressWarnings("unchecked")
    void addButtonToReturnToCreationPane (String label) {
        try {
            GUIElementCreator.
                    createButton(label,
                                 new ClickHandler(RightPane.class
                                         .getMethod(RETURN_TO_CREATION_METHOD), myParent), this);
        }
        catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

}
