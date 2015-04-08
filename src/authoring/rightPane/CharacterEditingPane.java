package authoring.rightPane;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.AbstractSprite;
import authoring.SpecificSprite;
import authoring.util.ImageEditor;


/**
 * This will be for when a character already on the screen is clicked on. It will allow the
 * designer to edit the character.
 * 
 * @author Natalie Chanfreau
 *
 */

public class CharacterEditingPane extends EditingPane {
    private static final String imageChooserTitle = "Change Character Image";
    private static final String imageChooserDescription = "Image Files";
    private static final String[] imageChooserExtensions = { "*.png", "*.jpg", "*.gif" };

    CharacterEditingPane (Scene scene,
                          AbstractSprite s,
                          EventHandler<? super MouseEvent> returnToCreationPane) {
        super(scene);
        // ======================== New design in here ===================== //
        getChildren().add(
                          new TextArea(String
                                  .format("Character editing pane")));
        // =================================================================
        addSpriteIcon(s);
        addDoneButton(returnToCreationPane);
    }

    // this should be changed so that it uses the button maker in order to remove hard coding
    private void addDoneButton (EventHandler<? super MouseEvent> returnToCreationPane) {
        Button doneButton = new Button("Done");
        doneButton.setOnMouseClicked(returnToCreationPane);
        getChildren().add(doneButton);
    }

    private void addSpriteIcon (AbstractSprite s) {
        ImageView spriteIcon = s.getIcon();
        spriteIcon.setOnMouseClicked(i -> spriteIconClicked(s));
        spriteIcon.setOnMouseEntered(i -> reduceSpriteOpacity(spriteIcon));
        spriteIcon.setOnMouseExited(i -> restoreSpriteOpacity(spriteIcon));
        getChildren().add(spriteIcon);
    }

    private void spriteIconClicked (AbstractSprite s) {
        changeCharacterImage(s);
    }

    private void reduceSpriteOpacity (ImageView imageView) {
        ImageEditor.reduceOpacity(imageView, SpecificSprite.OPACITY_REDUCTION_RATIO);
    }

    private void restoreSpriteOpacity (ImageView imageView) {
        ImageEditor.restoreOpacity(imageView);
    }

    private void changeCharacterImage (AbstractSprite s) {
        FileChooser imageChooser = new FileChooser();
        imageChooser.setTitle(imageChooserTitle);
        imageChooser.getExtensionFilters().add(new ExtensionFilter(imageChooserDescription,
                                                                   imageChooserExtensions));
        File selectedImageFile = imageChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            s.changeImage(new Image(selectedImageFile.toURI().toString()));
        }
    }
}
