package authoring.panes.rightPane;

import java.io.File;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
            DUPLICATE = "Duplicate",
            DELETE = "Delete",
            ADD_CONTROLS = "",
            PLAYABLE = "Playable",
            NAME = "Name",
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
    private TextField myDurationField, myDelayField;
    private TextField myName;

    CharacterEditingPane (Scene scene,
                          RightPane parent,
                          Sprite sprite,
                          List<String> miscellaneousImages) {
        super(scene, parent);
        initializeButtons(sprite, miscellaneousImages);
    }
    
    private void initializeButtons(Sprite sprite, List<String> miscellaneousImages){
      addSpriteIcon(sprite);
      addLabel(IMAGE_LABEL);
      setField(NAME, sprite.getName(), sprite);
      addAnimations(sprite, miscellaneousImages.get(ANIMATIONS));
      addPhysics(sprite, miscellaneousImages.get(PHYSICS));
      pathButtonContent = new String[] { miscellaneousImages.get(PATH1), miscellaneousImages.get(PATH2)};
      addCreatePathButton(sprite, miscellaneousImages.get(PATH1));
      myDurationField = addLabeledTextField("Path Duration", "5");
      myDelayField = addLabeledTextField("Path Delay", "0");
      myDurationField.setDisable(true);
      myDelayField.setDisable(true);
      addPlayableCheckBox(addControlsButton(sprite, miscellaneousImages.get(CONTROLS)), sprite);
//      addUpdateButton(sprite);
      addDuplicateButton(sprite);
      addDeleteButton(sprite);
    }

    private void addDuplicateButton (Sprite sprite) {
        createButton(DUPLICATE, e -> duplicateSprite(sprite));
    }

    private void duplicateSprite (Sprite sprite) {
        this.getMyScene().setCursor(new SpriteCursor(sprite.getCopy()));
    }

    private Button addAnimations (Sprite sprite, String image) {
        Button animationsButton = new Button(ADD_ANIMATIONS, new ImageView(image));
        animationsButton.setOnAction(e -> addAnimation(sprite));
        this.getChildren().add(animationsButton);
        return animationsButton;
    }

    private void addAnimation (Sprite sprite) {
        sprite.getAnimations().showBox(sprite);
    }

    private Button addPhysics (Sprite sprite, String image) {
        return createButton(sprite, ADD_PHYSICS, e -> addPhysics(sprite), image);
    }

    private void addPhysics (Sprite sprite) {
        sprite.getPhysics().showBox(sprite);
    }

    private void addDeleteButton (Sprite sprite) {
        createButton(DELETE, e -> getMyParent().deleteSprite(sprite));
    }

    private void addCreatePathButton (Sprite sprite, String image) {
        Button b = createButton(sprite, CREATE_PATH, null, image);
        b.setOnAction(e -> {
            getMyParent().toggleMode();
            myModeIndex = 1 - myModeIndex;
            b.setGraphic(new ImageView(pathButtonContent[myModeIndex]));
            myDurationField.setDisable(!myDurationField.isDisable());
            myDelayField.setDisable(!myDelayField.isDisable());
            // TODO: grab info and put into XML here
        });
    }

    private void addLabel (String string) {
        Label label = new Label(string);
        getChildren().add(label);
    }

    private Button addControlsButton (Sprite sprite, String image) {
        Button b = createButton(sprite, ADD_CONTROLS, 
                                e -> controlsClicked(sprite), image);
        b.setDisable(!sprite.getPlayable());
        return b;
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

    private void setField (String label, String value, Sprite sprite) {
        HBox h = new HBox(10);
        myName = new javafx.scene.control.TextField(value);
        myName.setOnKeyReleased(e -> sprite.setName(myName.getText()));
        h.getChildren().addAll(new Text(label), myName);
        getChildren().add(h);
    }
}
