package game_engine.collisions.detectors;

import game_engine.sprite.Sprite;
import javafx.scene.image.ImageView;

/**
 * Detects a collision based solely on the ImageViews of the two Sprites.
 * 
 * @author Michael Lee
 * @since 21 April 2015
 */
public class SimpleDetector implements ICollisionDetector {
	
	private Sprite spriteA, spriteB;
	
	/**
	 * Creates a new SimpleDetector and initializes the optimization map.
	 */
	public SimpleDetector(Sprite a, Sprite b) {
		spriteA = a;
		spriteB = b;
	}

	public boolean detectCollision() {
		ImageView objectA = spriteA.getImageView();
		ImageView objectB = spriteB.getImageView();
		
		return objectA.getBoundsInParent().intersects(objectB.getBoundsInParent());
	}
}