package game_engine;

import java.util.ArrayList;
import com.sun.javafx.geom.Rectangle;
/**
 * Hitbox class that defines collisions between objects
 * @author Kevin
 *
 */
public class HitBox {
	
        ArrayList<Rectangle> hitList = new ArrayList<>();
	Rectangle rect;
	
	public HitBox(ArrayList<Rectangle> hitList) {
		this.hitList = hitList;
	}
	
	/**
	 * Checks if the hitboxes intersect
	 * @param compare
	 * @return true if a hitbox intersects another
	 */
	public boolean intersects(HitBox compare) {
		// TODO
		return false;
	}

}
