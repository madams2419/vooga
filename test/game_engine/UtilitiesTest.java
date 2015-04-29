package game_engine;

import static org.junit.Assert.*;
import game_engine.physics.Vector;

import org.junit.Test;

public class UtilitiesTest {
	
	@Test
	public void testPhysicsToJFXPoint() {
		Vector point = new Vector(300, 50);
		double screenHeight = 800;
		
		Vector expected = new Vector(300, 750);
		Vector actual = Utilities.physicsToJFXPoint(point, screenHeight);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testJFXToPhysicsPoint() {
		Vector point = new Vector(300, 750);
		double screenHeight = 800;
		
		Vector expected = new Vector(300, 50);
		Vector actual = Utilities.jfxToPhysicsPoint(point, screenHeight);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPhysicsCenterToUpperLeft() {
		Vector center = new Vector(30, 30);
		double width = 10.0;
		double height = 20.0;
		
		Vector expected = new Vector(25, 40);
		Vector actual = Utilities.physicsCenterToUpperLeft(center, width, height);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPhysicsUpperLeftToCenter() {
		Vector upperLeft = new Vector(25, 40);
		double width = 10.0;
		double height = 20.0;
		
		Vector expected = new Vector(30, 30);
		Vector actual = Utilities.physicsUpperLeftToCenter(upperLeft, width, height);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testNodeTranslationToPhysicsCenter() {
		Vector nTranslate = new Vector(300, 50);
		double width = 10.0;
		double height = 30.0;
		double screenHeight = 800;
		
		Vector expected = new Vector(305, 735);
		Vector actual = Utilities.nodeTranslationToPhysicsCenter(nTranslate, width, height, screenHeight);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPhysicsCenterToNodeTranslation() {
		Vector pCenter = new Vector(45, 90);
		double width = 50;
		double height = 20;
		double screenHeight = 800;
		
		Vector expected = new Vector(20, 700);
		Vector actual = Utilities.physicsCenterToNodeTranslation(pCenter, width, height, screenHeight);
		
		assertEquals(expected, actual);
	}
	
	@Test 
	public void testConversionInvertibility() {
		Vector pCenter = new Vector(45, 90);
		double width = 50;
		double height = 20;
		double screenHeight = 800;
		
		Vector intermediate1 = Utilities.physicsCenterToNodeTranslation(pCenter, width, height, screenHeight);
		Vector actual1 = Utilities.nodeTranslationToPhysicsCenter(intermediate1, width, height, screenHeight);
		
		assertEquals(pCenter, actual1);
		
		Vector intermediate2 = Utilities.nodeTranslationToPhysicsCenter(pCenter, width, height, screenHeight);
		Vector actual2 = Utilities.physicsCenterToNodeTranslation(intermediate2, width, height, screenHeight);
		
		assertEquals(pCenter, actual2);
	}
}
