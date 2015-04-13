package authoring.rightPane;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import authoring.InteractionManager;
import authoring.Sprite;
import authoring.userInterface.AuthoringWindow;
import authoring.userInterface.ControlsDialog;
import authoring.userInterface.WindowPane;

/**
 * This class represents the right pane on the screen. It will allow the user to
 * edit a particular character, to edit the interactions between characters, and
 */
public class RightPane extends WindowPane {

	private EditingPane myCurrentContent;

	private final static int SPACING = 20;
	private final static int PADDING = 10;
	private final static String CSS = "styles/right_pane.css";
	private List<String> availableCharacterTypeURIs;

	public RightPane(Scene scene, AuthoringWindow window) {
		super(scene, new VBox(SPACING), window);
		System.out.printf("Instantiated %s%n", this.getClass().getName());
		myContainer.getStylesheets().add(CSS);
		myContainer.setPadding(new Insets(PADDING));
		((VBox) myContainer).setAlignment(Pos.TOP_CENTER);
		initializeAvailableCharacterTypes();
		// switchToPane(new CharacterCreationPane(scene, this));
		// initializeCurrentContent(new DefaultEditingPane(scene));
		// initializeCurrentContent(new CharacterEditingPane(scene, null));
	}

	public void switchToCharacterEditingPane(Sprite sprite) {
		// if (!(myCurrentContent instanceof CharacterEditingPane))
		switchToPane(new CharacterEditingPane(myScene, this, sprite));
	}

	public void switchToCharacterCreationPane() {
		if (!(myCurrentContent instanceof CharacterCreationPane))
			switchToPane(new CharacterCreationPane(myScene, this,
					availableCharacterTypeURIs));
	}

	public void switchToInteractionEditingPane(Sprite sprite1, Sprite sprite2) {
		if (!(myCurrentContent instanceof InteractionEditingPane)
				&& (sprite1 != sprite2)) // checking memory address
			switchToPane(new InteractionEditingPane(myScene, this, sprite1,
					sprite2, getListOfInteractions()));
		printOutInteractions();
	}

	private void printOutInteractions() {
		InteractionManager.getInstance().printOut();
	}

	public void InteractionCreate() {
	}

	public void switchToBlockCreationPane() {
	}

	public void DecorationCreate() {
	}

	public void switchtoGlobalSettingPane() {
	}

	public void UIControlCreate() {
		
	}

	public void switchToDefaultPane() {
		switchToPane(new DefaultEditingPane(myScene, this));
	}

	public void switchPane(Sprite s) {
		if (AuthoringWindow.getControl())
			switchToInteractionEditingPane(
					(Sprite) AuthoringWindow.getCurrentlySelected(), s);
		else {
			AuthoringWindow.setCurrentlySelected(s);
			switchToCharacterEditingPane(s);
		}
	}

	private void switchToPane(EditingPane newPane) {
		clearChildren();
		myCurrentContent = newPane;
		addFromCurrentContent();
	}

	public void switchToGlobalSettingPane() {
		// switchToPane(new CharacterEditingPane(myScene, new Sprite()));
		switchToPane(new GlobalCreationPane(myScene, this));
	}

	public void switchToMapSettingPane() {
		switchToPane(new MapSettingPane(myScene, this));
	}

	public void switchToLevelSettingPane() {
		switchToPane(new LevelSettingPane(myScene, this));
	}

	// TODO is this method never called?
	public void setScene(Scene scene, List<String> availableSpriteURIs) {
		myScene = scene;
		initializeCurrentContent(new CharacterCreationPane(scene, this,
				availableSpriteURIs));
	}

	private void clearChildren() {
		((VBox) myContainer).getChildren().clear();
	}

	private void addFromCurrentContent() {
		((VBox) myContainer).getChildren().addAll(
				myCurrentContent.getChildren());
	}

	public void addContent(EditingPane p) {
		myCurrentContent = p;
		addFromCurrentContent();
	}

	private void initializeCurrentContent(EditingPane content) {
		myCurrentContent = content;
		((VBox) myContainer).getChildren().addAll(
				myCurrentContent.getChildren());
	}

	// TEMPORARY!!
	private List<String> getListOfInteractions() {
		return Arrays.asList(new String[] { "jump", "die", "go to new level",
				"hit box" });
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthoringWindow getParent() {
		return myParent;
	}

	private void initializeAvailableCharacterTypes() {
		availableCharacterTypeURIs = new ArrayList<>();

		availableCharacterTypeURIs.addAll(getImages());

	}

	/***
	 * Finds every file in the directory where images are stored and adds
	 * them to a list.
	 */
	private List<String> getImages() {
		File rootDirectory = new File("res");
		List<String> matchingFiles = new ArrayList<String>();

		for (File possible : rootDirectory.listFiles()) {
//			if (getGameType(possible).equals(type)) {
				try {
					matchingFiles.add(possible.toURI().toURL().toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		}

		return matchingFiles;
	}
}
