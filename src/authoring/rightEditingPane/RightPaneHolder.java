package authoring.rightEditingPane;

import javafx.scene.layout.VBox;


/**
 * 
 * @author Natalie Chanfreau
 *
 */
public class RightPaneHolder {
    private EditingPane myCurrentContent;
    private DefaultEditingPane myDefaultRightPane;
    private CharacterEditingPane myCharacterEditingPane;
    private InteractionEditingPane myInteractionEditingPane;

    public RightPaneHolder () {
        myDefaultRightPane = new DefaultEditingPane();
        myCharacterEditingPane = new CharacterEditingPane();
        myInteractionEditingPane = new InteractionEditingPane();

        // currentContent = myBlankRightPane;
        // ^ this is what it actually should be, but for now, it'll be replaced by the current
        // content below

        myCurrentContent = myCharacterEditingPane;
    }

    public VBox getCurrentContent () {
        return myCurrentContent;
    }

    public void switchToCharacterEditingPane () {
        myCurrentContent = myCharacterEditingPane;
    }

    public void switchToInteractionEditingPane () {
        myCurrentContent = myInteractionEditingPane;
    }
    
    public void switchToDefaultPane() {
        myCurrentContent = myDefaultRightPane;
    }

}
