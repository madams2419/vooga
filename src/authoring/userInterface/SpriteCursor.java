package authoring.userInterface;

import javafx.scene.ImageCursor;
import authoring.SpecificSprite;
import authoring.Sprite;

/**
 * Allows image to follow cursor. Due to operating system limitations, image size
 * bounds will change based on OS.
 * @author Andrew
 *
 */
public class SpriteCursor extends ImageCursor {

    private Sprite myCurrentSprite;

    public SpriteCursor (Sprite specificSprite) {
        super(specificSprite.getImage(), specificSprite.getImage().getWidth() / 2, specificSprite
                .getImage().getHeight() / 2);
        myCurrentSprite = specificSprite;
    }

    public Sprite getCurrentSprite () {
        return myCurrentSprite;
    }
}
