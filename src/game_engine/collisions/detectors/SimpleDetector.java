package game_engine.collisions.detectors;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.ImageView;
import game_engine.sprite.Sprite;

/**
 * Detects a collision based solely on the ImageViews of the two Sprites.
 * 
 * @author Michael Lee
 * @since 21 April 2015
 */
public class SimpleDetector implements ICollisionDetector {
	
	private Map<Sprite, ImageView> optimization;
	
	/**
	 * Creates a new SimpleDetector and initializes the optimization map.
	 */
	public SimpleDetector() {
		optimization = new HashMap<>();
	}

	public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
		ImageView objectA = getImageView(spriteA);
		ImageView objectB = getImageView(spriteB);
		
		return objectA.intersects(objectB.getBoundsInParent());
	}
	
	/*
	 * Retrieves the correct ImageView from the map.
	 */
	private ImageView getImageView(Sprite sprite) {
		if (!optimization.containsKey(sprite)) {
			optimization.put(sprite, sprite.getImageView());
		}
		return optimization.get(sprite);
	}
}