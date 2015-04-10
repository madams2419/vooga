package game_engine.collision;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
/**
 * Hitbox class that defines collisions between objects
 * @author Kevin, Michael 
 *
 */
public class HitBox {
    
    private boolean[][] bitMap;
    private ImageView node;
    private Image image;
	
	public HitBox(ImageView n) {
		node = n;
		image = node.getImage();
		
	}
	
	public Bounds getBounds(){
		return node.getBoundsInParent();
	}
	
	/**
	 * method intersects
	 * Checks if the hitboxes intersect
	 * @param compare
	 * @return true if a hitbox intersects another
	 */
	public boolean intersects(HitBox compare) {
		return node.getBoundsInParent().intersects(compare.getBounds());
	}
}
