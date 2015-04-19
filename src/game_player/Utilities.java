package game_player;

import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;

public class Utilities {
	
	private static final int SCREEN_HEIGHT = 800;
	
	public static Vector physicsToJFXCoords(Vector pCoords) {
		return new Vector(pCoords.getX(), SCREEN_HEIGHT - pCoords.getY());
	}
	
	public static Vector physicsCenterToUpperLeft(PhysicsObject po) {
		Vector jfxPos = physicsToJFXCoords(po.getPositionPixels());
		//System.out.printf("Center : (%f, %f)\n", po.getXPixels(), po.getYPixels());
		double ulX = jfxPos.getX() - po.getWidthPixels()/2;
		double ulY = jfxPos.getY() - po.getHeightPixels()/2;
		//System.out.printf("UpperL : (%f, %f)\n", ulX, ulY);
		return new Vector(ulX, ulY);
	}

}
