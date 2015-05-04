// This entire file is part of my masterpiece.
// Natalie Chanfreau

package authoring.panes.rightPane;

import java.io.File;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Sprite;
import authoring.dialogs.DataDialog;
import authoring.panes.types.ISafePane;
import authoring.panes.types.SafeHBox;
import authoring.userInterface.SpriteCursor;
import authoring.util.FrontEndUtils;
import authoring.util.GUIElementCreator;
import authoring.util.ImageEditor;


/**
 * This pane allows the designer to edit the character that has been clicked on.
 * 
 * @author Natalie Chanfreau, Daniel Luker, Andrew Sun
 *
 */

class CharacterEditingPane extends EditingPane {
    private static final String ADD_CONTROLS = "";
    private static final String CREATE_PATH = "";
    private static final String ADD_ANIMATIONS = "";
    private static final String ADD_PHYSICS = "";
    private static final String IMAGE_LABEL = "Click on the image to change it!";
    private static final String DEFAULT_PATH_DELAY = "0";
    private static final String DEFAULT_PATH_DURATION = "5";
    private static final int ANIMATIONS_AND_PHYSICS_HBOX_SPACING = 50;

    private String[] pathButtonContent;
    private TextField myName;
    private int myModeIndex;

    CharacterEditingPane (Scene scene, RightPane parent, Sprite sprite,
                          Map<ButtonKind, String> buttonImagePaths) {
        super(scene, parent);
        initializeGUI(sprite, buttonImagePaths);
    }

    enum ButtonKind {
        ANIMATIONS, PHYSICS, CONTROLS, PATH_INITIAL, PATH_IN_PROCESS;
    }

    /**
     * Initializes the GUI for the pane
     * 
     * @param sprite
     * @param buttonImagePaths
     */
    private void initializeGUI (Sprite sprite, Map<ButtonKind, String> buttonImagePaths) {
        addSpriteIcon(sprite);
        addNameField(sprite);
        addAnimationsAndPhysicsHBox(sprite, buttonImagePaths);
        addPathContent(sprite, buttonImagePaths);
        addControlsButton(sprite, buttonImagePaths.get(ButtonKind.CONTROLS));
        addDuplicateButton(sprite);
        addDeleteButton(sprite);
    }

    /**
     * Creates the icon preview of the sprite
     * 
     * @param sprite
     */
    private void addSpriteIcon (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        GUIElementCreator.addImageView(spriteIcon, i -> changeCharacterImage(sprite),
                                       i -> ImageEditor.reduceOpacity(spriteIcon),
                                       i -> ImageEditor.restoreOpacity(spriteIcon),
                                       this);
        GUIElementCreator.addLabel(IMAGE_LABEL, this);
    }

    /**
     * Allows the user to select an image upon clicking on the sprite preview
     * 
     * @param sprite
     */
    private void changeCharacterImage (Sprite sprite) {
        File selectedImageFile =
                FrontEndUtils.selectFile(Constants.IMAGE_CHOOSER_TITLE,
                                         Constants.IMAGE_CHOOSER_DESCRIPTION,
                                         Constants.IMAGE_CHOOSER_EXTENSIONS);
        if (selectedImageFile != null) {
            sprite.changeImage(selectedImageFile.toURI().toString());
        }
    }

    /**
     * Adds the name field for a sprite
     * 
     * @param sprite
     */
    private void addNameField (Sprite sprite) {
        myName =
                GUIElementCreator.addLabeledTextField(Constants.NAME, sprite.getName(), this, null,
                                                      e -> sprite.setName(myName.getText()),
                                                      Constants.DEFAULT_HBOX_SPACING);
    }

    /**
     * Adds an HBox of the animations and physics buttons
     * 
     * @param sprite
     * @param buttonImagePaths
     */
    private void addAnimationsAndPhysicsHBox (Sprite sprite,
                                              Map<ButtonKind, String> buttonImagePaths) {
        SafeHBox hbox = new SafeHBox(ANIMATIONS_AND_PHYSICS_HBOX_SPACING);
        addDialogButton(ADD_ANIMATIONS, sprite.getAnimations(), sprite,
                        buttonImagePaths.get(ButtonKind.ANIMATIONS), hbox);
        addDialogButton(ADD_PHYSICS, sprite.getPhysics(), sprite,
                        buttonImagePaths.get(ButtonKind.PHYSICS), hbox);
        getChildren().add(hbox);
    }

    /**
     * Adds a dialog button to an ISafePane
     * 
     * @param label
     * @param dialog
     * @param sprite
     * @param image
     * @param pane
     * @return
     */
    private Button addDialogButton (String label, DataDialog dialog, Sprite sprite,
                                    String image, ISafePane pane) {
        return GUIElementCreator.addButton(label, e -> dialog.showBox(sprite), image, pane);
    }

    /**
     * Creates a button to duplicate a sprite
     * 
     * @param sprite
     */
    private void addDuplicateButton (Sprite sprite) {
        EventHandler<ActionEvent> duplicateSprite =
                e -> getMyScene().setCursor(new SpriteCursor(sprite.getCopy()));
        GUIElementCreator.addButton(Constants.DUPLICATE, duplicateSprite, this);
    }

    /**
     * Creates a button to delete a sprite
     * 
     * @param sprite
     */
    private void addDeleteButton (Sprite sprite) {
        GUIElementCreator
                .addButton(Constants.DELETE, e -> getMyParent().deleteSprite(sprite), this);
    }

    /**
     * Adds duration and delay fields as well as a button for creating paths.
     * 
     * @param sprite
     * @param buttonImagePaths
     */
    private void addPathContent (Sprite sprite, Map<ButtonKind, String> buttonImagePaths) {
        pathButtonContent =
                new String[] {
                              buttonImagePaths.get(ButtonKind.PATH_INITIAL),
                              buttonImagePaths.get(ButtonKind.PATH_IN_PROCESS)
                };

        TextField myDurationField = GUIElementCreator.
                addLabeledTextField(Constants.PATH_DURATION, DEFAULT_PATH_DURATION,
                                    this, Constants.DEFAULT_HBOX_SPACING, true);
        TextField myDelayField = GUIElementCreator.
                addLabeledTextField(Constants.PATH_DELAY, DEFAULT_PATH_DELAY,
                                    this, Constants.DEFAULT_HBOX_SPACING, true);
        addCreatePathButton(sprite, buttonImagePaths.get(ButtonKind.PATH_INITIAL),
                            myDurationField, myDelayField);
    }

    /**
     * Creates the path button
     * 
     * @param sprite
     * @param image
     * @param durationField
     * @param delayField
     */
    private void addCreatePathButton (Sprite sprite, String image,
                                      TextField durationField, TextField delayField) {
        Button button = GUIElementCreator.addButton(CREATE_PATH, null, image, this);
        button.setOnAction(e -> {
            getMyParent().toggleMode();
            myModeIndex = 1 - myModeIndex;
            button.setGraphic(new ImageView(pathButtonContent[myModeIndex]));
            durationField.setDisable(!durationField.isDisable());
            delayField.setDisable(!delayField.isDisable());
        });
    }

    /**
     * Creates the controls button
     * 
     * @param sprite
     * @param controlsImagePath
     */
    private void addControlsButton (Sprite sprite, String controlsImagePath) {
        Button controls =
                GUIElementCreator.addButton(ADD_CONTROLS,
                                            e -> sprite.getControls(getMyParent()).showBox(sprite),
                                            controlsImagePath, this);
        controls.setDisable(!sprite.getPlayable());
        addPlayableCheckBox(controls, sprite);
    }

    /**
     * Creates the playable checkbox
     * 
     * @param controls
     * @param sprite
     */
    private void addPlayableCheckBox (Button controls, Sprite sprite) {
        ChangeListener<? super Boolean> playableChangeListener = (observable, prev, curr) -> {
            sprite.setPlayable(curr);
            controls.setDisable(prev);
        };
        GUIElementCreator.addCheckBox(controls, Constants.PLAYABLE, sprite.getPlayable(),
                                      playableChangeListener, this);
    }

}
