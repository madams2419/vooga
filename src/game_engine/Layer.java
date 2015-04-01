package game_engine;

import game_engine.sprite.Sprite;

import java.util.List;
/**
 * 
 * @author 
 *
 */
public class Layer {
	
	List<Sprite> sprites;
	
	public Layer() {
		// TODO
	}
	/**
	 * method update
	 * updates each layer's contents
	 */
	public void update() {
		sprites.forEach(sprite -> sprite.update());
	}
	/**
	 * method addSprite
	 * adds Sprite to a layer
	 * @param sprite to be added
	 */
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	/**
	 * method removeSprite
	 * removes a sprite from a layer
	 * @param sprite to be removed
	 */
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	/**
	 * method getSprite
	 * @return list of sprites in a layer
	 */
	public List<Sprite> getSprites() {
		return sprites;
	}
}
