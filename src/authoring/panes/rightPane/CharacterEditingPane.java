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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import authoring.dataEditors.Sprite;
import authoring.dialogs.AnimationsDialog;
import authoring.dialogs.ControlsDialog;
import authoring.dialogs.CharacterPhysicsDialog;
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

    private static final String[] IMAGE_CHOOSER_EXTENSIONS = { "*.png", "*.jpg", "*.gif" };
    private static final String
            UPDATE = "Update",
            DELETE = "Delete",
            CONTROLS = "Controls",
            PLAYABLE = "Playable",
            NAME = "name",
            POSITION = "position",
            ADD_ANIMATIONS = "Add Animations",
            ADD_PHYSICS = "Add Physics",
            IMAGE_LABEL = "Click on the image to change it!",
            IMAGE_CHOOSER_DESCRIPTION = "Image Files",
            IMAGE_CHOOSER_TITLE = "Change Character Image";
    private static final int BUTTON_WIDTH = 220;

    private List<HBox> myFields = new LinkedList<>();

    CharacterEditingPane (Scene scene,
                          RightPane parent,
                          Sprite sprite,
                          List<String> miscellaneousImages) {
        super(scene, parent);
        // ======================== New design in here ===================== //
        addSpriteIcon(sprite);
        addLabel(IMAGE_LABEL);
        addAnimations(sprite, miscellaneousImages.get(0));
        addPhysics(sprite, miscellaneousImages.get(1));
//        addPhysics(sprite, miscellaneousImages.get(1));
        setFields(this.getChildren(), sprite.getCharacteristics());

        addPlayableCheckBox(addControlsButton(sprite), sprite);
        addUpdateButton(sprite);
        addDeleteButton(sprite);

        // ================================================================= //
    }

    private Button addAnimations(Sprite sprite, String image) {
		Button animationsButton = new Button(ADD_ANIMATIONS, new ImageView(image));
		animationsButton.setOnAction(e -> addAnimation(sprite));
		animationsButton.setPrefWidth(BUTTON_WIDTH);
		this.getChildren().add(animationsButton);
		return animationsButton;
	}

    private void addAnimation (Sprite sprite) {
        if (sprite.getAnimations() != null) {
            sprite.getAnimations().showBox(sprite);
        }
        else {
            AnimationsDialog animationsDialog = new AnimationsDialog(sprite);
            sprite.setAnimations(animationsDialog);
        }
    }

    private Button addPhysics(Sprite sprite, String image) {
            Button physicsButton = new Button(ADD_PHYSICS, new ImageView(image));
            physicsButton.setOnAction(e -> addPhysics(sprite));
            physicsButton.setPrefWidth(BUTTON_WIDTH);
            this.getChildren().add(physicsButton);
            return physicsButton;
    }

    private void addPhysics (Sprite sprite) {
        if (sprite.getPhysics() != null) {
            sprite.getPhysics().showBox(sprite);
        }
        else {
            CharacterPhysicsDialog physicsDialog = new CharacterPhysicsDialog(sprite);
            sprite.setPhysics(physicsDialog);
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
            ControlsDialog c = new ControlsDialog(s, myParent);
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
                FrontEndUtils.selectFile(IMAGE_CHOOSER_TITLE,
                                         IMAGE_CHOOSER_DESCRIPTION, IMAGE_CHOOSER_EXTENSIONS);
        if (selectedImageFile != null) {
            sprite.changeImage(selectedImageFile.toURI().toString());
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
