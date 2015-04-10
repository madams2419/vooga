package authoring.rightPane;

import java.util.Arrays;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import authoring.InteractionManager;
import authoring.Sprite;
import authoring.userInterface.AuthoringWindow;


/**
 * This class represents the right pane on the screen. It will allow the user to
 * edit a particular character, to edit the interactions between characters, and
 * to create new characters.
 * 
 * @author Natalie Chanfreau, Daniel Luker, hojeannie Chung
 *
 */
public class RightPane extends VBox {

<<<<<<< HEAD
	private Scene myScene;
	private EditingPane myCurrentContent;

	private final static int SPACING = 20;
	private final static int PADDING = 10;
	private final static String CSS = "styles/right_pane.css";

	private static RightPane mInstance;

	public static RightPane getInstance() {
		if (mInstance == null)
			mInstance = new RightPane();
		return mInstance;
	}

	private RightPane() {
		super(SPACING);

		getStylesheets().add(CSS);
		setPadding(new Insets(PADDING));
		setAlignment(Pos.TOP_CENTER);

		// initializeCurrentContent(new DefaultEditingPane(scene));
		// initializeCurrentContent(new CharacterEditingPane(scene, null));
	}

	public void setScene(Scene scene) {
		myScene = scene;
		 initializeCurrentContent(new CharacterCreationPane(scene));

		// temporary
//		initializeCurrentContent(new InteractionEditingPane(scene, null, null,
//				getListOfInteractions()));
		
	}

	public void switchPane(Sprite s) {
		if (AuthoringWindow.getControl())
			switchToInteractionEditingPane(
					(Sprite) AuthoringWindow.getCurrentlySelected(), s);
		else
			switchToCharacterEditingPane(s);
	}

	public void switchToCharacterEditingPane(Sprite sprite) {
		switchToPane(new CharacterEditingPane(myScene, sprite));
	}

	public void switchToCharacterCreationPane() {
		switchToPane(new CharacterCreationPane(myScene));
		System.out.println("Character Creation Pane");
	}

	private void switchToInteractionEditingPane(Sprite sprite1, Sprite sprite2) {
		switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2,
				getListOfInteractions()));
	}
	
	public void InteractionCreate(){
		System.out.println("Interaction Create");
	}
	
	public void switchToBlockCreationPane(){
		System.out.println("Block Created");
	}
	
	public void DecorationCreate(){
		System.out.println("Decoration Create");
	}
	
	public void switchToGlobalSettingPane(){
//		switchToPane(new CharacterEditingPane(myScene, new Sprite()));
		switchToPane(new GlobalCreationPane(myScene));
		System.out.println("Global Create");
	}
	
	public void switchToMapSettingPane(){
		switchToPane(new MapSettingPane(myScene));
		System.out.println("Map Created");
	}
	
	public void UIControlCreate(){
		System.out.println("UI Control Create");
	}
	

	public void switchToDefaultPane() {
		switchToPane(new DefaultEditingPane(myScene));
	}

	private void switchToPane(EditingPane newPane) {
		clearChildren();
		myCurrentContent = newPane;
		addFromCurrentContent();
	}

	private void clearChildren() {
		getChildren().clear();
	}

	private void addFromCurrentContent() {
		getChildren().addAll(myCurrentContent.getChildren());
	}

	private void initializeCurrentContent(EditingPane content) {
		myCurrentContent = content;
		this.getChildren().addAll(myCurrentContent.getChildren());
	}

	// TEMPORARY!!
	private List<String> getListOfInteractions() {
		return Arrays.asList(new String[] { "jump", "die", "go to new level" });
	}
}
=======
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

    public void setScene (Scene scene) {
        myScene = scene;
        initializeCurrentContent(new CharacterCreationPane(scene));

        // temporary
        // initializeCurrentContent(new InteractionEditingPane(scene, null, null,
        // getListOfInteractions()));

    }

    public void switchPane (Sprite s) {
        if (AuthoringWindow.getControl())
            switchToInteractionEditingPane(
                                           (Sprite) AuthoringWindow.getCurrentlySelected(), s);
        else
            switchToCharacterEditingPane(s);
    }

    public void switchToCharacterEditingPane (Sprite sprite) {
        switchToPane(new CharacterEditingPane(myScene, sprite));
    }

    public void switchToCharacterCreationPane () {
        printOutInteractions();
        switchToPane(new CharacterCreationPane(myScene));
        System.out.println("Character Creation Pane");
    }

    private void printOutInteractions () {
        InteractionManager.getInstance().printOut();
    }

    private void switchToInteractionEditingPane (Sprite sprite1, Sprite sprite2) {
        switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2,
                                                getListOfInteractions()));
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

    public void switchToMapSettingPane () {
        switchToPane(new MapSettingPane(myScene));
        System.out.println("Map Created");
    }

    public void UIControlCreate () {
        System.out.println("UI Control Create");
    }

    public void switchToDefaultPane () {
        switchToPane(new DefaultEditingPane(myScene));
    }

    private void switchToPane (EditingPane newPane) {
        clearChildren();
        myCurrentContent = newPane;
        addFromCurrentContent();
    }

    private void clearChildren () {
        getChildren().clear();
    }

    private void addFromCurrentContent () {
        getChildren().addAll(myCurrentContent.getChildren());
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
>>>>>>> 961f48eb31e27ba515b6d3f2383f03dc35bf990c
