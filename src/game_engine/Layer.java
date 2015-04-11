package game_engine;

import game_engine.sprite.*;
import game_engine.sprite.attributes.IMovement;
import java.util.ArrayList;
import java.util.List;
/**
 * Layer is an organizational class that contains groupings of specific sprites
 * @author Kevin Chang
 *
 */
public class Layer {
	
	List<Sprite> sprites;
	
	public Layer() {
		// TODO
	    sprites = new ArrayList<>();
	}

	/**
	 * method update
	 * updates each layer's contents
	 */
	public void update() {
		sprites.forEach(sprite -> sprite.update());
	}
	
	public void scrollX(int x){
	    sprites.forEach(sprite -> {
	        if(!sprite.getClass().isInstance(new Player()))
	       sprite.getPhysicsObject().setXPixels(sprite.getPhysicsObject().getXPixels()+x);
	    });
	}
	
	public void scrollY(int y){
	    sprites.forEach(sprite -> {
                if(!sprite.getClass().isInstance(new Player()))
               sprite.getPhysicsObject().setYPixels(sprite.getPhysicsObject().getYPixels()+y);
            });
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
	public void removeSprite(IMovement sprite) {
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
