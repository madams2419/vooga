package src.authoring.userInterface;

import javafx.scene.ImageCursor;
import authoring.SpecificSprite;


public class SpriteCursor extends ImageCursor {

    private SpecificSprite myCurrentSprite;

    public SpriteCursor (SpecificSprite specificSprite) {
        super(specificSprite.getImage(), specificSprite.getImage().getWidth() / 2, specificSprite
                .getImage().getHeight() / 2);
        myCurrentSprite = specificSprite;
    }

    public SpecificSprite getCurrentSprite () {
        return myCurrentSprite;
    }
}
