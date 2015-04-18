package game_player;

import game_engine.physics.PhysicsObject;
import game_engine.physics_engine.complex.Vector;

public class Utilities {
	
	private static final int SCREEN_HEIGHT = 900;
	
	public static Vector physicsToJFXCoords(Vector pCoords) {
		return new Vector(pCoords.getX(), SCREEN_HEIGHT - pCoords.getY());
	}
	
	public static Vector physicsCenterToUpperLeft(PhysicsObject po) {
		Vector jfxPos = physicsToJFXCoords(po.getPositionPixels());
		//System.out.printf("Center : (%f, %f)\n", po.getXPixels(), po.getYPixels());
		double ulX = jfxPos.getX() - po.getRadiusPixels();
		double ulY = jfxPos.getY() - po.getRadiusPixels();
		//System.out.printf("UpperL : (%f, %f)\n", ulX, ulY);
		return new Vector(ulX, ulY);
	}

}
