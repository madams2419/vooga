package authoring.panes.rightPane;

import java.io.File;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Sprite;
import authoring.dialogs.DataDialog;
import authoring.panes.types.SafeHBox;
import authoring.userInterface.SpriteCursor;
import authoring.util.FrontEndUtils;
import authoring.util.GUIElementCreator;
import authoring.util.ImageEditor;


/**
 * This will be for when a character already on the screen is clicked on. It
 * will allow the designer to edit the character.
 * 
 * @author Natalie Chanfreau, Daniel Luker, Andrew Sun
 *
 */

class CharacterEditingPane extends EditingPane {

    private int myModeIndex;
    private String[] pathButtonContent;
    private TextField myName;

    CharacterEditingPane (Scene scene, RightPane parent, Sprite sprite,
                          Map<ButtonKind, String> miscellaneousImagePaths) {
        super(scene, parent);
        initializeButtons(sprite, miscellaneousImagePaths);
    }

    enum ButtonKind {
        ANIMATIONS, PHYSICS, CONTROLS, PATH_INITIAL, PATH_IN_PROCESS;
    }

    private void initializeButtons (Sprite sprite, Map<ButtonKind, String> miscellaneousImagePaths) {
        addSpriteIcon(sprite);
        addNameField(sprite);
        addAnimationsAndPhysicsHBox(sprite, miscellaneousImagePaths);
        addPathContent(sprite, miscellaneousImagePaths);
        addControls(sprite, miscellaneousImagePaths.get(ButtonKind.CONTROLS));
        addDuplicateButton(sprite);
        addDeleteButton(sprite);
    }

    private void addAnimationsAndPhysicsHBox (Sprite sprite,
                                              Map<ButtonKind, String> miscellaneousImagePaths) {
        SafeHBox hbox = new SafeHBox(Constants.ANIMATIONS_AND_PHYSICS_HBOX_SPACING);
        addAnimationsButton(sprite, miscellaneousImagePaths.get(ButtonKind.ANIMATIONS), hbox);
        addPhysicsButton(sprite, miscellaneousImagePaths.get(ButtonKind.PHYSICS), hbox);
        getChildren().add(hbox);
    }

    private void addDuplicateButton (Sprite sprite) {
        GUIElementCreator.addButton(Constants.DUPLICATE, e -> duplicateSprite(sprite), this);
    }

    private void duplicateSprite (Sprite sprite) {
        this.getMyScene().setCursor(new SpriteCursor(sprite.getCopy()));
    }

    private Button addAnimationsButton (Sprite sprite, String image, SafeHBox hbox) {
        return GUIElementCreator.addButton(Constants.ADD_ANIMATIONS,
                                           e -> showDialog(sprite.getAnimations(), sprite),
                                           image, hbox);
    }

    private Button addPhysicsButton (Sprite sprite, String image, SafeHBox hbox) {
        return GUIElementCreator.addButton(Constants.ADD_PHYSICS,
                                           e -> showDialog(sprite.getPhysics(), sprite),
                                           image, hbox);
    }

    private void showDialog (DataDialog dialog, Sprite sprite) {
        dialog.showBox(sprite);
    }

    private void addDeleteButton (Sprite sprite) {
        GUIElementCreator
                .addButton(Constants.DELETE, e -> getMyParent().deleteSprite(sprite), this);
    }

    private void addPathContent (Sprite sprite, Map<ButtonKind, String> miscellaneousImagePaths) {
        pathButtonContent =
                new String[] {
                              miscellaneousImagePaths.get(ButtonKind.PATH_INITIAL),
                              miscellaneousImagePaths.get(ButtonKind.PATH_IN_PROCESS)
                };
        TextField myDurationField = GUIElementCreator.
                addLabeledTextField(Constants.PATH_DURATION, Constants.DEFAULT_PATH_DURATION,
                                    this, 10, true);
        TextField myDelayField = GUIElementCreator.
                addLabeledTextField(Constants.PATH_DELAY, Constants.DEFAULT_PATH_DELAY,
                                    this, 10, true);
        addCreatePathButton(sprite, miscellaneousImagePaths.get(ButtonKind.PATH_INITIAL),
                            myDurationField, myDelayField);
    }

    private void addCreatePathButton (Sprite sprite, String image,
                                      TextField durationField, TextField delayField) {
        Button b = GUIElementCreator.addButton(Constants.CREATE_PATH, null, image, this);
        b.setOnAction(e -> {
            getMyParent().toggleMode();
            myModeIndex = 1 - myModeIndex;
            b.setGraphic(new ImageView(pathButtonContent[myModeIndex]));
            durationField.setDisable(!durationField.isDisable());
            delayField.setDisable(!delayField.isDisable());
        });
    }

    private void addControls (Sprite sprite, String controlsImagePath) {
        Button controls = addControlsButton(sprite, controlsImagePath);
        addPlayableCheckBox(controls, sprite);
    }

    private Button addControlsButton (Sprite sprite, String image) {
        Button button = GUIElementCreator.addButton(Constants.ADD_CONTROLS,
                                                    e -> controlsClicked(sprite), image, this);
        button.setDisable(!sprite.getPlayable());
        return button;
    }

    private void addPlayableCheckBox (Button controls, Sprite sprite) {
        CheckBox playable = new CheckBox(Constants.PLAYABLE);
        playable.setSelected(sprite.getPlayable());
        playable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed (ObservableValue<? extends Boolean> ov,
                                 Boolean prev, Boolean curr) {
                sprite.setPlayable(curr);
                controls.setDisable(prev);
            }
        });
        getChildren().add(playable);
    }

    private void controlsClicked (Sprite s) {
        s.getControls(getMyParent()).showBox(s);
    }

    private void addSpriteIcon (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        GUIElementCreator.addImageView(spriteIcon, i -> spriteIconClicked(sprite),
                                       i -> reduceSpriteOpacity(spriteIcon),
                                       i -> restoreSpriteOpacity(spriteIcon), this);
        GUIElementCreator.addLabel(Constants.IMAGE_LABEL, this);
    }

    private void spriteIconClicked (Sprite sprite) {
        changeCharacterImage(sprite);
    }

    private void reduceSpriteOpacity (ImageView imageView) {
        ImageEditor.reduceOpacity(imageView, Sprite.OPACITY_REDUCTION_RATIO);
    }

    private void restoreSpriteOpacity (ImageView imageView) {
        ImageEditor.restoreOpacity(imageView);
    }

    private void changeCharacterImage (Sprite sprite) {
        File selectedImageFile =
                FrontEndUtils.selectFile(Constants.IMAGE_CHOOSER_TITLE,
                                         Constants.IMAGE_CHOOSER_DESCRIPTION,
                                         Constants.IMAGE_CHOOSER_EXTENSIONS);
        if (selectedImageFile != null) {
            sprite.changeImage(selectedImageFile.toURI().toString());
        }
    }

    private void addNameField (Sprite sprite) {
        myName =
                GUIElementCreator.addLabeledTextField(Constants.NAME, sprite.getName(), this, null,
                                                      e -> sprite.setName(myName.getText()),
                                                      Constants.NAME_HBOX_SPACING);
    }

}
