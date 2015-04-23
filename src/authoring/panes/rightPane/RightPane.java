package authoring.panes.rightPane;

import game_engine.objective.Objective;

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
import authoring.dataEditors.InteractionManager;
import authoring.dataEditors.Sprite;
import authoring.panes.WindowPane;
import authoring.userInterface.AuthoringWindow;


/**
 * This class represents the right pane on the screen. It will allow the user to
 * edit a particular character, to edit the interactions between characters, and
 * to create new characters.
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */

public class RightPane extends WindowPane {

    private static final String OBJECT_IMAGE_PATH = "authoring_images/objects";
    private static final String DECORATION_IMAGE_PATH = "authoring_images/decorations";
    private static final String BLOCK_IMAGE_PATH = "authoring_images/blocks";
    private static final String CHARACTER_IMAGE_PATH = "authoring_images/characters";

    private EditingPane myCurrentContent;

    private final static int SPACING = 20;
    private final static int PADDING = 10;
    private final static String CSS = "styles/right_pane.css";
    private List<String> availableCharacterTypeURIs;
    private List<String> availableBlockTypeURIs;
    private List<String> availableDecorationTypeURIs;
    private List<String> availableObjectTypeURIs;
    
    public RightPane (Scene scene, AuthoringWindow window) {
        super(scene, new VBox(SPACING), window);
        System.out.printf("Instantiated %s%n", this.getClass().getName());
        getContainer().getStylesheets().add(CSS);
        getContainer().setPadding(new Insets(PADDING));
        ((VBox) getContainer()).setAlignment(Pos.TOP_CENTER);
        initializeAvailableCreatables();
    }

    private void initializeAvailableCreatables () {
        // cannot make first line of initializeAvailableTypes(...) initialize the lists because that
        // changes the reference :(
        availableCharacterTypeURIs = new ArrayList<>();
        availableBlockTypeURIs = new ArrayList<>();
        availableDecorationTypeURIs = new ArrayList<>();
        availableObjectTypeURIs = new ArrayList<>();

        initializeAvailableTypes(availableCharacterTypeURIs, CHARACTER_IMAGE_PATH);
        initializeAvailableTypes(availableBlockTypeURIs, BLOCK_IMAGE_PATH);
        initializeAvailableTypes(availableDecorationTypeURIs, DECORATION_IMAGE_PATH);
        initializeAvailableTypes(availableObjectTypeURIs, OBJECT_IMAGE_PATH);
    }

    public void switchToCharacterEditingPane (Sprite sprite) {
        switchToPane(new CharacterEditingPane(myScene, this, sprite));
    }
    
    void deleteSprite (Sprite sprite) {
        getParent().getCenterPane().removeSprite(sprite);
        InteractionManager.getInstance().removeSpriteInteractions(sprite);
        switchToCharacterCreationPane();
    }

    public void switchToCharacterCreationPane () {
        switchToPane(new CreationPane(myScene, this, availableCharacterTypeURIs));
    }

    public void switchToBlockCreationPane () {
        switchToPane(new CreationPane(myScene, this, availableBlockTypeURIs));
    }

    public void switchToDecorationCreationPane () {
        switchToPane(new CreationPane(myScene, this, availableDecorationTypeURIs));
    }

    public void switchToObjectCreationPane () {
        switchToPane(new CreationPane(myScene, this, availableObjectTypeURIs));
    }

    public void switchToInteractionEditingPane (Sprite sprite1, Sprite sprite2) {
        if (!(myCurrentContent instanceof InteractionEditingPane)
            && (sprite1 != sprite2)) // checking memory address
            switchToPane(new InteractionEditingPane(myScene, this, sprite1,
                                                    sprite2, getListOfInteractions()));
        printOutInteractions();
    }
    
    public void switchToObjectivePane() {
    	switchToPane(new ObjectivePane(myScene, this));
    }

    private void printOutInteractions () {
        InteractionManager.getInstance().printOut();
    }

    public void UIControlCreate () {

    }

    public void switchToDefaultPane () {
        switchToPane(new DefaultEditingPane(myScene, this));
    }

    public void switchPane (Sprite s) {
        if (AuthoringWindow.getControl())
            switchToInteractionEditingPane((Sprite) AuthoringWindow.getCurrentlySelected(), s);
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
        switchToPane(new GlobalCreationPane(myScene, this));
    }

    public void switchToMapSettingPane () {
        switchToPane(new MapSettingPane(myScene, this));
    }

    public void setScene (Scene scene, List<String> availableSpriteURIs) {
        myScene = scene;
        initializeCurrentContent(new CreationPane(scene, this,
                                                  availableSpriteURIs));
    }

    private void clearChildren () {
        ((VBox) getContainer()).getChildren().clear();
    }

    private void addFromCurrentContent () {
        ((VBox) getContainer()).getChildren().addAll(
                                                     myCurrentContent.getChildren());
    }

    public void addContent (EditingPane p) {
        myCurrentContent = p;
        addFromCurrentContent();
    }

    private void initializeCurrentContent (EditingPane content) {
        myCurrentContent = content;
        ((VBox) getContainer()).getChildren().addAll(
                                                     myCurrentContent.getChildren());
    }

    // TEMPORARY!!
    private List<String> getListOfInteractions () {
        return Arrays.asList(new String[] { "jump", "die", "go to new level",
                                           "hit box" });
    }

    @Override
    public Group generateComponents (ArrayList<Map<String, Map<String, String>>> values) {
        return null;
    }

    public AuthoringWindow getParent () {
        return myParent;
    }

    private void initializeAvailableTypes (List<String> list, String filePath) {
        list.addAll(getImages(filePath));
    }

    /***
     * Finds every file in the directory where images are stored and adds
     * them to a list.
     */
    private List<String> getImages (String filePath) {
        File rootDirectory = new File(filePath);
        List<String> matchingFiles = new ArrayList<String>();

        for (File possible : rootDirectory.listFiles()) {
            // if (getGameType(possible).equals(type)) {
            try {
                matchingFiles.add(possible.toURI().toURL().toString());
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // }
        }

        return matchingFiles;
    }
}
