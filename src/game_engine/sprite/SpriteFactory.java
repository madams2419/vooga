package game_engine.sprite;

import java.lang.reflect.InvocationTargetException;

public class SpriteFactory {

	public static Sprite createSprite(String spriteType) throws InstantiationException, IllegalAccessException{
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
			return classInstance =  (Sprite) runClass.getConstructor().newInstance();
		} catch (IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
