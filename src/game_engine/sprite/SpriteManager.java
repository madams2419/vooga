
package game_engine.sprite;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

/**
 * observes individual Sprites and calls Physics Engine
 * @author emresonmez
 *
 */

public class SpriteManager implements Observer{
	private HashSet<Sprite> sprites = new HashSet<>();
	
	@Override
	public void update(Observable o, Object arg) {
		Sprite sprite = (Sprite) o;
		sprite.getPhysicsObject().update();
	}
	
	public void addSprite(Sprite sprite){
		sprite.addObserver(this);
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite){
		if(sprites.contains(sprite)){
			sprites.remove(sprite);
		}
	}
	
	// testing
	public static void main(String[] args){
//		Sprite tester = new Collectible();
//		Sprite tester2 = new Collectible();
//	
//		SpriteManager manager = new SpriteManager();
//		manager.addSprite(tester);
//		manager.addSprite(tester2);
////		tester.addObserver(manager);
//		
//		tester.setID(10);

	}
	
}

