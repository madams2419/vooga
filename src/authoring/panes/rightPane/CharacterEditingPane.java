package authoring.panes.rightPane;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import authoring.dataEditors.Sprite;
import authoring.dialogs.AnimationsDialog;
import authoring.dialogs.ControlsDialog;
import authoring.dialogs.StatesDialog;
import authoring.util.FrontEndUtils;
import authoring.util.ImageEditor;



/**
 * This will be for when a character already on the screen is clicked on. It
 * will allow the designer to edit the character.
 * 
 * @author Natalie Chanfreau, Daniel Luker, Andrew Sun
 *
 */

class CharacterEditingPane extends EditingPane {
    private static final String NAME = "name";
    private static final String POSITION = "position";
    private static final String imageChooserTitle = "Change Character Image";
    private static final String imageChooserDescription = "Image Files";
    private static final String[] imageChooserExtensions = { "*.png", "*.jpg",
                                                            "*.gif" };
    private static final String UPDATE = "Update";
    private static final String ADD_ANIMATIONS = "Add Animations";
    private static final String ADD_STATES = "Add States";
    private static final String DELETE = "Delete";
    private static final String CONTROLS = "Controls";
    private static final String PLAYABLE = "Playable";
    private static final String BACK = "Back";
    
    private static final String IMAGE_LABEL = "Click on the image to change it!";

    private List<HBox> myFields = new LinkedList<>();

    CharacterEditingPane (Scene scene, RightPane parent, Sprite sprite) {
        super(scene, parent);
        // ======================== New design in here ===================== //
        addSpriteIcon(sprite);
        addLabel(IMAGE_LABEL);
        addAnimations(sprite);
        setFields(this.getChildren(), sprite.getCharacteristics());

        addStatesButton(sprite);
        addPlayableCheckBox(addControlsButton(sprite), sprite);
        addUpdateButton(sprite);
        addDeleteButton(sprite);
//        addBackButton(); // I don't know if this is really necessary anymore...

        // ================================================================= //
    }

    private void addStatesButton (Sprite sprite) {
        Button statesButton = new Button(ADD_STATES);
        statesButton.setOnAction(e -> addState(sprite));
        this.getChildren().add(statesButton);
    }

    private void addState (Sprite sprite) {
        if (sprite.getStatesDialog() != null) {
            sprite.getStatesDialog().showBox(sprite);
        }
        else {
            StatesDialog statesDialog = new StatesDialog(sprite);
            sprite.setStates(statesDialog);
        }
    }

    private void addAnimations (Sprite sprite) {
        Button animationsButton = new Button(ADD_ANIMATIONS);
        animationsButton.setOnAction(e -> addAnimation(sprite));
        this.getChildren().add(animationsButton);
    }

    private void addAnimation (Sprite sprite) {
        if (sprite.getAnimations() != null) {
            sprite.getAnimations().showBox(sprite);
        }
        else {
            AnimationsDialog animationsDialog = new AnimationsDialog(sprite, sprite.getStates());
            sprite.setAnimations(animationsDialog);
        }
    }
    
    private void addDeleteButton (Sprite sprite) {
        Button deleteButton = new Button(DELETE);
        deleteButton.setOnAction(e -> getMyParent().deleteSprite(sprite));
        this.getChildren().add(deleteButton);        
    }

    private void addLabel (String string) {
        Label label = new Label(string);
        getChildren().add(label);
    }

    private void addBackButton () {
        addButtonToReturnToCreationPane(BACK);
    }

    private void addUpdateButton (Sprite sprite) {
        Button updateButton = new Button(UPDATE);
        updateButton.setOnAction(e -> updateSprite(sprite));
        this.getChildren().add(updateButton);
    }

    private Button addControlsButton (Sprite sprite) {
        Button controls = new Button(CONTROLS);
        controls.setDisable(!sprite.getPlayable());
        controls.setOnMouseClicked(e -> controlsClicked(sprite));
        getChildren().add(controls);
        return controls;
    }

    private void addPlayableCheckBox (Button controls, Sprite sprite) {
        CheckBox playable = new CheckBox(PLAYABLE);
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
        if (s.getControls() != null) {
            s.getControls().showBox(s);
        }
        else {
            ControlsDialog c = new ControlsDialog(s);
            s.setControls(c);
        }
    }

    private void updateSprite (Sprite sprite) {
        myFields.forEach(hbox -> {
            String s, t;
            sprite.setCharacteristic(
                                     (s = ((Text) hbox.getChildren().get(0)).getText()),
                                     (t = ((TextField) hbox.getChildren().get(1)).getText()));
            switch (s) {
                case POSITION:
                    sprite.setPosition(FrontEndUtils.stringToMap(t));
                    break;
                case NAME:
                    sprite.setName(t);
                    break;
                case Sprite.SCALE:
                    sprite.setScale(Double.parseDouble(t));
                    break;
            }

        });
    }

    private void addSpriteIcon (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        spriteIcon.setOnMouseClicked(i -> spriteIconClicked(sprite));
        spriteIcon.setOnMouseEntered(i -> reduceSpriteOpacity(spriteIcon));
        spriteIcon.setOnMouseExited(i -> restoreSpriteOpacity(spriteIcon));
        getChildren().add(spriteIcon);
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
                FrontEndUtils.selectFile(imageChooserTitle, imageChooserDescription, imageChooserExtensions);
        if (selectedImageFile != null) {
            sprite.changeImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    private void setFields (ObservableList<Node> parent,
                            Map<String, String> fields) {
        fields.forEach( (label, value) -> {
            HBox h = new HBox(5);
            h.getChildren().addAll(new Text(label),
                                   new javafx.scene.control.TextField(value));
            parent.add(h);
            myFields.add(h);
        });
    }

}
