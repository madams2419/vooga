package src.authoring.rightPane;

import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import authoring.AbstractSprite;
import authoring.SpecificSprite;
import authoring.SpriteType;
import authoring.userInterface.SpriteCursor;
import authoring.util.ImageEditor;


/**
 * This will allow the user to select from a number of images (or insert his/her own image) to
 * create a character that can be placed on the canvas.
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */

public class CharacterCreationPane extends EditingPane {

    CharacterCreationPane (Scene scene, Consumer<AbstractSprite> spriteClicked) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("Information%n"
                                               + "when a drop down%n" + "item is selected, or%n"
                                               + "a current element on%n" + "the scroll pane is%n"
                                               + "selected (up to two%n"
                                               + "selections), its (their)%n"
                                               + "information will be%n" + "shown here.")));
        addSpriteToPane(100, "/images/turtle.png", spriteClicked);
        addSpriteToPane(101, "/images/luigi.png", spriteClicked);
    }
    
    private void addSpriteToPane(int id, String imageURI, Consumer<AbstractSprite> spriteClicked) {
        SpriteType sampleImage = new SpriteType(id, imageURI);
        
        //these two aren't working for now when the copy is made in imageClicked (Consumer<Sprite> spriteClicked, Sprite sampleImage, int ID):
//        sampleImage.setOnMouseEntered(i -> ImageEditor.reduceOpacity(sampleImage, Sprite.OPACITY_REDUCTION_RATIO));
//        sampleImage.setOnMouseExited(i -> ImageEditor.restoreOpacity(sampleImage, Sprite.OPACITY_REDUCTION_RATIO));
        
        int ID = 100; //for now, it doesn't change, but this should eventually be unique for each sprite
        
        ImageView sampleImageIcon = sampleImage.getIcon();
        sampleImageIcon.setOnMouseClicked(e -> imageClicked(spriteClicked, sampleImage, ID));
        sampleImageIcon.setOnMouseDragged(e -> imageDragged(e));
        sampleImageIcon.setOnMouseEntered(i -> ImageEditor.reduceOpacity(sampleImageIcon, SpriteType.OPACITY_REDUCTION_RATIO));
        sampleImageIcon.setOnMouseExited(i -> ImageEditor.restoreOpacity(sampleImageIcon));
        
        this.getChildren().add(sampleImageIcon);
        
    }

    private void imageDragged (MouseEvent e) {
        
    }

    // an image in the right pane is clicked to be moved to the center pane
    private void imageClicked (Consumer<AbstractSprite> spriteClicked, SpriteType sampleImage, int ID) {
        // need to now set mouse cursor to the sprite image
        getMyScene().setCursor(new SpriteCursor(new SpecificSprite(sampleImage, ID, spriteClicked)));
        
    }
}
