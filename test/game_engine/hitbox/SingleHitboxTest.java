package game_engine.hitbox;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import game_engine.hitboxes.SingleHitbox;
import game_engine.physics.Vector;

import org.junit.Test;

public class SingleHitboxTest {

	@Test
	public void getBoundingBoxTest() {
		SingleHitbox s = new SingleHitbox();
		
		s.addPoint(new Vector(-5, -8));
		s.addPoint(new Vector(-7, 13));
		s.addPoint(new Vector(12, -3));
		s.addPoint(new Vector(19, 22));
		
		List<Vector> boundingBox = s.getBoundingBox();
		
		Vector lowerLeft = boundingBox.get(0);
		Vector upperRight = boundingBox.get(1);
		
		assertEquals(new Vector(-7, -8), lowerLeft);
		assertEquals(new Vector(19, 22), upperRight);
	}

}
