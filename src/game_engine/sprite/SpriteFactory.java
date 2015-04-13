package game_engine.sprite;

import game_engine.physics.PhysicsObject;

public class SpriteFactory {

    public Sprite createSprite(String spriteType, PhysicsObject physics) {
	try {
	    String className = "game_engine.sprite." + spriteType;
	    Class<?> runClass = Class.forName(className);
	    return (Sprite) runClass.getConstructor(PhysicsObject.class)
		    .newInstance(physics);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
