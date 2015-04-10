package game_engine.collision;

import java.util.ArrayList;
import javafx.scene.shape.*;
/**
 * Hitbox class that defines collisions between objects
 * @author Kevin, Michael 
 *
 */
public class HitBox {
	
    private ArrayList<Shape> hitList = new ArrayList<>();
	
	public HitBox(ArrayList<Shape> hitList) {
		this.hitList = hitList;
	}
	
	/**
	 * method intersects
	 * Checks if the hitboxes intersect
	 * @param compare
	 * @return true if a hitbox intersects another
	 */
	public boolean intersects(HitBox compare) {
		// TODO
		
	}

//	public double getVolume() {
//		//TODO
//		return 0.0;
//	}

}
