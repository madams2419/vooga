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
    private static final String imageChooserTitle = "Change Character Image";
    private static final String imageChooserDescription = "Image Files";
    private static final String[] imageChooserExtensions = { "*.png", "*.jpg", "*.gif" };

    CharacterEditingPane (Scene scene, Sprite sprite) {
        super(scene);
        //======================== New design in here ===================== //
        getChildren().add(
                          new TextArea(String
                                  .format("Character editing pane")));
        //=================================================================
        addSpriteIcon(sprite);
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
        FileChooser imageChooser = new FileChooser();
        imageChooser.setTitle(imageChooserTitle);
        imageChooser.getExtensionFilters().add(new ExtensionFilter(imageChooserDescription,
                                                                  imageChooserExtensions));
        File selectedImageFile = imageChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            sprite.changeImage(new Image(selectedImageFile.toURI().toString()));
        }
    }
}
