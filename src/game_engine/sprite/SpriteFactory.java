package game_engine.sprite;

import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.RigidBody.RBodyType;

public class SpriteFactory {

    public Sprite createSprite(String spriteType, String name, String image, int height, int width, RBodyType type, 
	    PhysicsEngine engine, Material material, int startX, int startY) {
	try {
	    String className = "game_engine.sprite." + spriteType;
	    Class<?> runClass = Class.forName(className);
	    return (Sprite) runClass.getConstructor(String.class, String.class, int.class, int.class, RBodyType.class,
		    PhysicsEngine.class, Material.class, int.class, int.class)
		    .newInstance(name, image, height, width, RBodyType.RECTANGLE, engine, material, startX, startY);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
