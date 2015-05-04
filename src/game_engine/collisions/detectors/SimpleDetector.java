package game_engine.collisions.detectors;

import game_engine.sprite.Sprite;
import javafx.scene.image.ImageView;

/**
 * Detects a collision based solely on the ImageViews of the two Sprites.
 * 
 * @author Michael Lee
 * @since 21 April 2015
 */
public class SimpleDetector implements ICollisionDetector<Sprite> {

	public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
		ImageView objectA = spriteA.getImageView();
		ImageView objectB = spriteB.getImageView();
		
		return objectA.getBoundsInParent().intersects(objectB.getBoundsInParent());
	}
}