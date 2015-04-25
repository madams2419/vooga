package game_engine.hitbox;

import static org.junit.Assert.*;
import game_engine.hitboxes.MultipleHitbox;
import game_engine.hitboxes.SingleHitbox;
import game_engine.physics.Vector;

import java.util.List;
import java.util.Random;

import org.junit.Test;

public class MultipleHitboxTest {
	
	private Random random = new Random();

	@Test
	public void getBoundingBoxTest() {
		SingleHitbox sHitbox1 = new SingleHitbox();
		
		sHitbox1.addPoint(new Vector(-5, -8));
		sHitbox1.addPoint(new Vector(-7, 65));
		sHitbox1.addPoint(new Vector(12, -3));
		sHitbox1.addPoint(new Vector(19, 22));
		
		SingleHitbox sHitbox2 = new SingleHitbox();
		
		sHitbox2.addPoint(new Vector(-90, -8));
		sHitbox2.addPoint(new Vector(-7, 13));
		sHitbox2.addPoint(new Vector(50, -3));
		sHitbox2.addPoint(new Vector(19, 22));
		
		MultipleHitbox mHitbox = new MultipleHitbox();
		mHitbox.addHitbox(sHitbox1);
		mHitbox.addHitbox(sHitbox2);
	
		List<Vector> boundingBox = mHitbox.getBoundingBox();
		
		Vector lowerLeft = boundingBox.get(0);
		Vector upperRight = boundingBox.get(1);
		
		assertEquals(new Vector(-90, -8), lowerLeft);
		assertEquals(new Vector(50, 65), upperRight);
	}
	
	private SingleHitbox genRandomSingleHitbox(double min, double max, int numPoints) {
		SingleHitbox sHitbox = new SingleHitbox();
		
		for(int i = 0; i < numPoints; i++) {
			sHitbox.addPoint(randPoint(min, max));
		}
		return sHitbox;
	}
	
	private Vector randPoint(double min, double max) {
		return new Vector(rand(min, max), rand(min, max));
	}
	
	private double rand(double min, double max) {
		return random.nextInt((int)max - (int)min + 1) + min;
	}

}
