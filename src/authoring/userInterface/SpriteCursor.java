package authoring.userInterface;

import authoring.Sprite;
import javafx.scene.ImageCursor;
<<<<<<< HEAD
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
=======
import javafx.scene.image.Image;

public class SpriteCursor extends ImageCursor{

	private Sprite myCurrentSprite;
	
	public SpriteCursor(Sprite s){
		super(s.getImage(), s.getImage().getWidth()/2, s.getImage().getHeight()/2);
		myCurrentSprite = s;
	}
	
	public Sprite getCurrentSprite(){
		return myCurrentSprite;
	}
>>>>>>> 040310bf83d8f9d359743e47048a6fb27acca693
}
