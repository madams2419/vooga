package game_engine.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SpriteManager implements Observer{
	private List<Sprite> sprites = new ArrayList<Sprite>();
	
	@Override
	public void update(Observable o, Object arg) {
		Sprite sprite = (Sprite) o;
		String state = sprite.getState();
		Double[] params = {sprite.getX(),sprite.getY(),
				sprite.getTargetX(),sprite.getTargetY(),
				sprite.getVelocity(),sprite.getAcceleration()};
		// test
		for(double d: params){
			System.out.println(d);
		}
		
		// call physics engine on params
		
		
	}
	
	public void updateSprites(){
		
	}
	
	public void addSprite(Sprite sprite){
		sprite.addObserver(this);
		sprites.add(sprite);
	}
	

}
