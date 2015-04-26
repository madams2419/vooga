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
import authoring.dialogs.ControlsDialog;
import authoring.userInterface.SpriteCursor;
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
            CREATE_PATH = "",
            UPDATE = "Update",
            DUPLICATE = "Duplicate",
            DELETE = "Delete",
            ADD_CONTROLS = "",
            PLAYABLE = "Playable",
            NAME = "name",
            POSITION = "position",
            ADD_ANIMATIONS = "",
            ADD_PHYSICS = "",
            IMAGE_LABEL = "Click on the image to change it!",
            IMAGE_CHOOSER_DESCRIPTION = "Image Files",
            IMAGE_CHOOSER_TITLE = "Change Character Image";
    private int myModeIndex;
    private static final int
            ANIMATIONS = 0,
            PHYSICS = 1,
            CONTROLS = 2,
            PATH1 = 3,
            PATH2 = 4;
    private String[] pathButtonContent;

    private List<HBox> myFields = new LinkedList<>();

    CharacterEditingPane (Scene scene,
                          RightPane parent,
                          Sprite sprite,
                          List<String> miscellaneousImages) {
        super(scene, parent);
        // ======================== New design in here ===================== //
        addSpriteIcon(sprite);
        addLabel(IMAGE_LABEL);
        addAnimations(sprite, miscellaneousImages.get(ANIMATIONS));
        addPhysics(sprite, miscellaneousImages.get(PHYSICS));
        // setFields(this.getChildren(), sprite.getCharacteristics());

        pathButtonContent = new String[] { miscellaneousImages.get(PATH1), miscellaneousImages.get(PATH2)};
        addCreatePathButton(sprite, miscellaneousImages.get(PATH1));
        addPlayableCheckBox(addControlsButton(sprite, miscellaneousImages.get(CONTROLS)), sprite);
//        addUpdateButton(sprite);
        addDuplicateButton(sprite);
        addDeleteButton(sprite);

        // ================================================================= //
    }

    private void addDuplicateButton (Sprite sprite) {
        Button duplicateButton = new Button(DUPLICATE);
        duplicateButton.setOnAction(e -> duplicateSprite(sprite));
        this.getChildren().add(duplicateButton);
    }

    private void duplicateSprite (Sprite sprite) {
        this.getMyScene().setCursor(new SpriteCursor(sprite.getCopy()));
    }

    private Button addAnimations (Sprite sprite, String image) {
        Button animationsButton = new Button(ADD_ANIMATIONS, new ImageView(image));
        animationsButton.setOnAction(e -> addAnimation(sprite));
//        animationsButton.setPrefWidth(BUTTON_WIDTH);
        this.getChildren().add(animationsButton);
        return animationsButton;
    }

    private void addAnimation (Sprite sprite) {
        sprite.getAnimations().showBox(sprite);
    }

    private Button addPhysics (Sprite sprite, String image) {
        Button physicsButton = new Button(ADD_PHYSICS, new ImageView(image));
        physicsButton.setOnAction(e -> addPhysics(sprite));
//        physicsButton.setPrefWidth(BUTTON_WIDTH);
        this.getChildren().add(physicsButton);
        return physicsButton;
    }

    private void addPhysics (Sprite sprite) {
        sprite.getPhysics().showBox(sprite);
    }

    private void addDeleteButton (Sprite sprite) {
        Button deleteButton = new Button(DELETE);
        deleteButton.setOnAction(e -> getMyParent().deleteSprite(sprite));
        this.getChildren().add(deleteButton);
    }

    private void addCreatePathButton (Sprite sprite, String image) {
        Button createPathButton = new Button(CREATE_PATH, new ImageView(image));
        createPathButton.setOnAction(e -> {
            getMyParent().toggleMode();
            myModeIndex = 1 - myModeIndex;
            createPathButton.setGraphic(new ImageView(pathButtonContent[myModeIndex]));
        });
        this.getChildren().add(createPathButton);
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

    private Button addControlsButton (Sprite sprite, String image) {
        Button controls = new Button(ADD_CONTROLS, new ImageView(image));
        controls.setDisable(!sprite.getPlayable());
        controls.setOnMouseClicked(e -> controlsClicked(sprite));
//        controls.setPrefWidth(BUTTON_WIDTH);
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
