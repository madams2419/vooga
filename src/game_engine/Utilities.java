package game_engine;

import game_engine.physics.utilities.Vector;

public class Utilities {
	
	public static Vector physicsToJFXPoint(Vector point, double screenHeight) {
		return new Vector(point.getX(), screenHeight - point.getY());
	}
	
	public static Vector jfxToPhysicsPoint(Vector point, double screenHeight) {
		return physicsToJFXPoint(point, screenHeight);
	}
	
	public static double[] jfxToPhysicsPoint(double xVal, double yVal, double screenHeight) {
		return jfxToPhysicsPoint(new Vector(xVal, yVal), screenHeight).asArray();
	}
	
	public static Vector physicsCenterToUpperLeft(Vector center, double width, double height) {
		Vector translation = new Vector(-width/2, height/2);
		return center.plus(translation);
	}
	
	public static Vector physicsUpperLeftToCenter(Vector center, double width, double height) {
		Vector translation = new Vector(width/2, -height/2);
		return center.plus(translation);
	}
	
	public static double[] nodeTranslationToPhysicsCenter(double nxVal, double nyVal, double nWidth, double nHeight, double screenWidth, double screenHeight) {
		Vector nTranslate = new Vector(nxVal, nyVal);
		Vector pTranslate = nodeTranslationToPhysicsCenter(nTranslate, nWidth, nHeight, screenWidth, screenHeight);
		return pTranslate.asArray();
	}
	
	public static Vector nodeTranslationToPhysicsCenter(Vector nTranslate, double nWidth, double nHeight, double screenWidth, double screenHeight) {
		Vector pTranslate = jfxToPhysicsPoint(nTranslate, screenHeight);
		return physicsUpperLeftToCenter(pTranslate, nWidth, nHeight);
	}
	
	public static Vector physicsCenterToNodeTranslation(Vector pCenter, double nWidth, double nHeight, double screenHeight) {
		Vector pUpperLeft = physicsCenterToUpperLeft(pCenter, nWidth, nHeight);
		return physicsToJFXPoint(pUpperLeft, screenHeight);
	}

}
