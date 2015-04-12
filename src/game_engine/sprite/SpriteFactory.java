package game_engine.sprite;

import game_engine.physics.PhysicsObject;

import java.lang.reflect.InvocationTargetException;

public class SpriteFactory {
    
    public SpriteFactory(){
        
    }
    
    public Sprite createSprite(String spriteType, PhysicsObject physics) throws InstantiationException, IllegalAccessException{
       Class<?> runClass = null;
       Sprite classInstance = null;
       String className =  "game_engine.sprite." + spriteType;
       try {
        runClass = Class.forName(className);
    }
    catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
        try {
	    return classInstance =  (Sprite) runClass.getConstructor(PhysicsObject.class).newInstance(physics);
	} catch (IllegalArgumentException | SecurityException | InvocationTargetException | NoSuchMethodException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}
    }
}
