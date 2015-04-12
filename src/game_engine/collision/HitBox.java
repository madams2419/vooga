package game_engine.collision;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.Rectangle;
/**
 * Hitbox class that defines collisions between objects
 * @author Kevin, Michael
 *
 */
public class HitBox {

	private PhysicsBody myPhysicsBody;

    private boolean[][] bitMap;
    private ImageView node;
    private Image image;

	public HitBox(ImageView n) {
		node = n;
		image = node.getImage();
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
		return node.getBoundsInParent().intersects(compare.getBounds());
	}

	private boolean[][] createBitMap(Image src) {
		PixelReader reader = src.getPixelReader();
		int width = (int) src.getWidth();
		int height = (int) src.getHeight();
		boolean[][] bitMap = new boolean[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				bitMap[y][x] = reader.getArgb(x, y) != 0;
			}
		return bitMap;
	}

}
