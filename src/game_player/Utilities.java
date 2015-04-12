package game_player;

import game_engine.physics.Vector;

public class Utilities {
	
	private static final int SCREEN_HEIGHT = 800;
	
	public static Vector normalToJFXCoords(Vector normal) {
		return new Vector(normal.getX(), SCREEN_HEIGHT - normal.getY());
	}

}
