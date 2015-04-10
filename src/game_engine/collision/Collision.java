package game_engine.collision;

import game_engine.IBehavior;
import game_engine.sprite.Sprite;

import java.util.List;
import java.util.Map;

/**
 * Defines behavior when collision occurs
 * @author Michael Lee
 *
 */

public class Collision {
	private Sprite spriteA;
	private List<Sprite> spriteList;
	private Map<Sprite, IBehavior> behaviorList;
	private Map<Sprite, String[]> paramList;
	private CollisionDirection direction;
	
	//enum
	public Collision(Sprite a, List<Sprite> list, Map<Sprite, IBehavior> behaviors, Map<Sprite, String[]> params, CollisionDirection d){
		spriteA = a;
		spriteList = list;
		behaviorList = behaviors;
		paramList = params;
		direction = d;
	}
	
	public List<Sprite> getColliding(){
		spriteList.stream().filter(sprite-> isColliding(sprite,spriteA)).forEach(sprite -> );
	}
	
	private void execute(Sprite sprite) {
		behaviorList.get(sprite).execute(paramList.get(sprite));
	}
	
	public boolean isColliding(Sprite a, Sprite b){
		return true;
	}
}
