package authoring.rightEditingPane;

import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import authoring.Sprite;
import authoring.userInterface.SpriteCursor;


/**
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */

public class CharacterEditingPane extends EditingPane {

    CharacterEditingPane () {
        super();
        this.getChildren().add(
                               new TextArea(String
                                       .format("Information%n"
                                               + "when a drop down%n" + "item is selected, or%n"
                                               + "a current element on%n" + "the scroll pane is%n"
                                               + "selected (up to two%n"
                                               + "selections), its (their)%n"
                                               + "information will be%n" + "shown here.")));
        // ImageView sampleImage = new ImageView("/images/turtle.png");
        Sprite sampleImage = new Sprite(100, "/images/turtle.png");
        sampleImage.setOnMouseClicked(e -> imageClicked());
        sampleImage.setOnMouseDragged(e -> imageDragged(e));
        this.getChildren().add(sampleImage);
    }

    private void imageDragged (MouseEvent e) {
        
    }

    private void imageClicked () {
        // need to now set mouse cursor to the sprite image
        getScene().setCursor(new SpriteCursor(new Sprite(100, "/images/turtle.png")));
    }
}
