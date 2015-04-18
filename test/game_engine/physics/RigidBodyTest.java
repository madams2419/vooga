package game_engine.physics;

import static org.junit.Assert.*;

import org.junit.Test;

public class RigidBodyTest {
	
	private RectangleBody rb = new RectangleBody(50, 100);

	@Test
	public void testClampOutside() {
		Vector pointOutside = new Vector(30, 30);
		Vector clampedPoint = rb.clampPointToEdge(pointOutside);
		
		Vector expected = new Vector(25, 30);
		assertEquals(expected, clampedPoint);
	}
	
	@Test
	public void testClampInside() {
		Vector pointInside = new Vector(10, 15);
		Vector clampedPoint = rb.clampPointToEdge(pointInside);
		
		//System.out.println(clampedPoint);
		
		Vector expected = new Vector(25, 15);
		assertEquals(expected, clampedPoint);
	}

}
