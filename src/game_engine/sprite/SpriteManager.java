package game_engine.sprite;

import static org.junit.Assert.*;
import org.junit.Test;
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
		
		Double[] params = {sprite.getX(),sprite.getY(),
				sprite.getTargetX(),sprite.getTargetY(),
				sprite.getVelocity(),sprite.getAcceleration()};
		// test
		for(double d: params){
			System.out.println(d);
		}
		
		// TODO call physics engine on params
		
		
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
		Sprite tester = new Collectible();
		Sprite tester2 = new Collectible();
	
		SpriteManager manager = new SpriteManager();
		manager.addSprite(tester);
		manager.addSprite(tester2);
//		tester.addObserver(manager);
		
		tester.setAcceleration(40);
		tester.setID(10);
		tester.setTargetX(100);
		System.out.println("");
		tester2.setTargetY(10);
		
		
	}
	
	@Test
	public void checkParams(){
	    Sprite tester = new Collectible();
    
            SpriteManager manager = new SpriteManager();
            manager.addSprite(tester);
            tester.setID(10);
            tester.setTargetX(100);
 
            assertEquals(10,tester.getID(), .1);
	}
	

	
	

}
