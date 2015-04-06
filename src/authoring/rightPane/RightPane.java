package authoring.rightPane;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import authoring.Sprite;


/**
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class RightPane extends VBox implements IRightPane {
    private Scene myScene;
    private final String CSS = "styles/right_pane.css";
    private EditingPane myCurrentContent;

    public RightPane (Scene scene) {
        myScene = scene;
        this.getStylesheets().add(CSS);

//        initializeCurrentContent(new DefaultEditingPane(scene));

//        initializeCurrentContent(new CharacterEditingPane(scene, null));
        initializeCurrentContent(new CharacterCreationPane(scene, i -> switchToCharacterEditingPane(i)));
    }
    
    public void switchToCharacterEditingPane (Sprite sprite) {
        switchToPane(new CharacterEditingPane(myScene, sprite));
    }

    public void switchToCharacterCreationPane () {
        switchToPane(new CharacterCreationPane(myScene, i -> switchToCharacterEditingPane(i)));
    }
    
    public void switchToInteractionEditingPane (Sprite sprite1, Sprite sprite2) {
        switchToPane(new InteractionEditingPane(myScene, sprite1, sprite2));
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
    
    private void initializeCurrentContent(EditingPane content) {
        myCurrentContent = content;
        this.getChildren().addAll(myCurrentContent.getChildren());
    }

}
