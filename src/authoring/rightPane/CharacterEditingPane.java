package authoring.rightPane;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.Sprite;
import authoring.util.ImageEditor;


/**
 * This will be for when a character already on the screen is clicked on. It will allow the
 * designer to edit the character.
 * 
 * @author Natalie Chanfreau
 *
 */

public class CharacterEditingPane extends EditingPane {

    CharacterEditingPane (Scene scene, Sprite sprite) {
        super(scene);
        getChildren().add(
                          new TextArea(String
                                  .format("Character editing pane")));

        ImageView spriteIcon = sprite.getIcon();
        getChildren().add(spriteIcon);
        spriteIcon.setOnMouseClicked(i -> changeCharacterImage(sprite));
        spriteIcon.setOnMouseEntered(i -> ImageEditor.reduceOpacity(spriteIcon, Sprite.OPACITY_REDUCTION_RATIO));
        spriteIcon.setOnMouseExited(i -> ImageEditor.restoreOpacity(spriteIcon, Sprite.OPACITY_REDUCTION_RATIO));
    }

    private void changeCharacterImage (Sprite s) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Change Character Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png",
                                                                  "*.jpg", "*.gif"));
        File selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            s.changeImage(new Image(selectedImageFile.toURI().toString()));
        }
    }
}
