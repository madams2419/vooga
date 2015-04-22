package game_engine.collisions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.sprite.Sprite;

public class SimpleCollision implements ICollision{
	private Sprite mySpriteA;
	private Sprite mySpriteB;
	private ICollisionDetector myDetector;
	

	public SimpleCollision(Sprite a, Sprite b, String detector, String resolver) {
		mySpriteA = a;
		mySpriteB = b;
		
		try {
			Class dClass = Class.forName(detector);
			Constructor constructor = dClass.getConstructor(new Class[]{Sprite.class,Sprite.class});
			myDetector = (ICollisionDetector)
			        constructor.newInstance(a, b);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Class rClass = Class.forName(resolver);
			Constructor constructor = rClass.getConstructor(new Class[]{Sprite.class,Sprite.class});
			myResolver = (ICollisionResolver)
			        constructor.newInstance(a, b);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setResolver(ICollisionResolver resolver) {
		
	}

	@Override
	public void setDetector(ICollisionDetector detector) {
		// TODO Auto-generated method stub
		
	}

	public void execute() {
		
	}

}
