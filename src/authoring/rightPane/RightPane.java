package authoring.rightPane;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import authoring.InteractionManager;
import authoring.Sprite;
import authoring.userInterface.AuthoringWindow;
import authoring.userInterface.ControlsDialog;


/**
 * This class represents the right pane on the screen. It will allow the user to
 * edit a particular character, to edit the interactions between characters, and
 * to create new characters.
 * 
 * @author Natalie Chanfreau, Daniel Luker, hojeannie Chung
 *
 */
public class RightPane extends VBox {

    private Scene myScene;
    private EditingPane myCurrentContent;

    private final static int SPACING = 20;
    private final static int PADDING = 10;
    private final static String CSS = "styles/right_pane.css";

    private static RightPane mInstance;

    public static RightPane getInstance () {
        if (mInstance == null)
            mInstance = new RightPane();
        return mInstance;
    }

    private RightPane () {
        super(SPACING);

        getStylesheets().add(CSS);
        setPadding(new Insets(PADDING));
        setAlignment(Pos.TOP_CENTER);

        // initializeCurrentContent(new DefaultEditingPane(scene));
        // initializeCurrentContent(new CharacterEditingPane(scene, null));
    }

    public void switchToCharacterEditingPane (Sprite sprite) {
        switchToPane(new CharacterEditingPane(myScene, sprite));
    }

    public void switchToCharacterCreationPane () {
        switchToPane(new CharacterCreationPane(myScene));
        System.out.println("Character Creation Pane");
    }

    public void switchToInteractionEditingPane (Sprite sprite1, Sprite sprite2) {
        switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2, getListOfInteractions()));
        printOutInteractions();
    }

    private void printOutInteractions () {
        InteractionManager.getInstance().printOut();
    }

    public void InteractionCreate () {
        System.out.println("Interaction Create");
    }

    public void switchToBlockCreationPane () {
        System.out.println("Block Created");
    }

    public void DecorationCreate () {
        System.out.println("Decoration Create");
    }

    public void switchtoGlobalSettingPane () {
        System.out.println("Global Create");
    }

    public void UIControlCreate () {
        new ControlsDialog();
    }

    public void switchToDefaultPane () {
        switchToPane(new DefaultEditingPane(myScene));
    }

	public void switchPane(Sprite s) {
	    System.out.println(AuthoringWindow.getCurrentlySelected());
	if (AuthoringWindow.getControl())
		switchToInteractionEditingPane(
				(Sprite) AuthoringWindow.getCurrentlySelected(), s);
	else {
	    AuthoringWindow.setCurrentlySelected(s);
		switchToCharacterEditingPane(s);
	}
}
    private void switchToPane (EditingPane newPane) {
        clearChildren();
        myCurrentContent = newPane;
        addFromCurrentContent();
    }

    public void switchToGlobalSettingPane () {
        // switchToPane(new CharacterEditingPane(myScene, new Sprite()));
        switchToPane(new GlobalCreationPane(myScene));
        System.out.println("Global Create");
    }

    public void switchToMapSettingPane () {
        switchToPane(new MapSettingPane(myScene));
        System.out.println("Map Created");
    }

    public void setScene (Scene scene) {
        myScene = scene;
        initializeCurrentContent(new CharacterCreationPane(scene));

        // temporary
        // initializeCurrentContent(new InteractionEditingPane(scene, null,
        // null,
        // getListOfInteractions()));

    }

    private void clearChildren () {
        getChildren().clear();
    }

    private void addFromCurrentContent () {
        getChildren().addAll(myCurrentContent.getChildren());
    }

    public void addContent(EditingPane p) {
    	myCurrentContent = p;
    	addFromCurrentContent();
    }
    
    private void initializeCurrentContent (EditingPane content) {
        myCurrentContent = content;
        this.getChildren().addAll(myCurrentContent.getChildren());
    }

    // TEMPORARY!!
    private List<String> getListOfInteractions () {
        return Arrays.asList(new String[] { "jump", "die", "go to new level" });
    }
}
