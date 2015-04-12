package game_player;

import game_engine.physics.Vector;

public class Utilities {
	
	private static final int SCREEN_HEIGHT = 400;
	
	public static Vector normalToJFXCoords(Vector normal) {
		Vector jfxVect = new Vector();
		jfxVect.setX(normal.getX());
		jfxVect.setY(SCREEN_HEIGHT - normal.getY());
		return jfxVect;
	}

}
