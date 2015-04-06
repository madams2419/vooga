package authoring.rightPane;

import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import authoring.Sprite;
import authoring.userInterface.SpriteCursor;


/**
 * This will allow the user to select from a number of images (or insert his/her own image) to
 * create a character that can be placed on the canvas.
 * 
 * ^^ Is this true? Or are we dragging and dropping from the "characters" drop-down menu?
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */

public class CharacterCreationPane extends EditingPane {

    CharacterCreationPane (Scene scene, Consumer<Sprite> spriteClicked) {
        super(scene);
        this.getChildren().add(
                               new TextArea(String
                                       .format("Information%n"
                                               + "when a drop down%n" + "item is selected, or%n"
                                               + "a current element on%n" + "the scroll pane is%n"
                                               + "selected (up to two%n"
                                               + "selections), its (their)%n"
                                               + "information will be%n" + "shown here.")));
        Sprite sampleImage = new Sprite(100, "/images/turtle.png");
        sampleImage.setOnMouseClicked(e -> imageClicked(spriteClicked));
        sampleImage.setOnMouseDragged(e -> imageDragged(e));
        this.getChildren().add(sampleImage);
    }

    private void imageDragged (MouseEvent e) {

    }

    private void imageClicked (Consumer<Sprite> spriteClicked) {
        // need to now set mouse cursor to the sprite image
        getScene().setCursor(new SpriteCursor(new Sprite(100, "/images/turtle.png", spriteClicked)));
        
    }
}
