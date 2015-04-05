package authoring_UserInterface;

import authoring_environment.Sprite;
import javafx.scene.ImageCursor;
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
}
