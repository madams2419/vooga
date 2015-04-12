package game_engine.collision;

import game_engine.physics.CircleBody;
import game_engine.physics.RectangleBody;
import game_engine.physics.RigidBody;
import game_engine.physics.RigidBody.RBodyType;
import game_engine.physics.Vector;
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
    
    private boolean[][] bitMap;
    private ImageView node;
    private Image image;
    private RigidBody rigidBody;
	
	public HitBox(ImageView n, RBodyType rbType) {
		node = n;
		image = node.getImage();
		boolean[][] bitMap = createBitMap(image);
		rigidBody = createRigidBody(rbType);
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
	
	public RigidBody getRigidBody() {
		return rigidBody;
	}
	
	private RigidBody createRigidBody(RBodyType rbType) {
		RigidBody rBody;
		// TODO move to factory class
		switch (rbType) {
		case CIRCLE:
			Vector center = new Vector(node.getX() + node.getFitWidth() / 2,
					node.getTranslateY() + node.getFitHeight() / 2);
			double radius = Math.max(node.getFitHeight(), node.getFitWidth());
			rBody = new CircleBody(radius, center);
			break;
		default:
			Vector upperLeft = new Vector(node.getX(), node.getY());
			rBody = new RectangleBody(upperLeft, node.getFitHeight(),
					node.getFitWidth());
			break;
		}
		return rBody;
	}

}
