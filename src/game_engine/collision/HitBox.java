package game_engine.collision;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

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
		image = n.getImage();
		boolean[][] bitMap = createBitMap(image);
		
	}
	
	public Bounds getBounds(){
		return node.getBoundsInParent();
	}
	
	public boolean[][] getBitMap(){
		return bitMap;
	}
	
	/**
	 * method intersects
	 * Checks if the hitboxes intersect
	 * @param compare
	 * @return true if a hitbox intersects another
	 */
	public boolean intersects(HitBox compare) {
		return getBounds().intersects(compare.getBounds());
	}
	
	private boolean[][] createBitMap(Image src) {
	        return null;
//		PixelReader reader = src.getPixelReader();
//		int width = (int) src.getWidth();
//		int height = (int) src.getHeight();
//		boolean[][] bitMap = new boolean[height][width];
//		for (int y = 0; y < height; y++)
//			for (int x = 0; x < width; x++) {
//				bitMap[y][x] = reader.getArgb(x, y) != 0;
//			}
//		return null;
		//return bitMap;
	}

}
