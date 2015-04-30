package game_engine.sprite;

import game_engine.Utilities;
import game_engine.physics.PhysicsObject;
import game_engine.physics.rigidbodies.RigidBody;
import game_engine.physics.utilities.Vector;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Observer;
import java.util.function.Supplier;
/**
 * 
 * @author Kevin Chang, Brian Lavalee
 * Class to hold images and deal with switching images in sprites
 */
public class Animation implements Observer{

	private ImageView image;
	private Rectangle rectangle;
    private Node current;
    Map<String, ImageLink> paths;
    private long timeElapsed;
    private double height;

    public Animation(double h) {
    	image = new ImageView();
    	paths = new HashMap<>();
    	current = null;
    	timeElapsed = 0;
    	height = h;
    }
    
    /**
     * method associateImage
     * @param state the state linked to the current image
     * @param imagePath the imagePath for the image
     * @param delay 
     * @param height the height to set the image to
     * @param width  the width to set the image to
     */
    public void associateImage(String state, String imagePath, RigidBody rBody, double delay, double height, double width) {
    	if (!paths.containsKey(state)) {
    		paths.put(state, new ImageLink());
    	}
    	
    	try {
			FileInputStream fis = new FileInputStream(imagePath);
			paths.get(state).add(new Image(fis, width, height, false, true), rBody, Duration.seconds(delay));
			if(current == null) {
				current = paths.get(state).first;
			}
		}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.exit(0);
		}
    }
    /**
     * 
     * @author Kevin Chang, Brian Lavallee
     *  Nodes to hold images in the linked list corresponding to a state
     */
    protected class Node {
		 
		private Image image;
		private RigidBody rigidBody;
		private Node next;
		private Duration delay;
		private int index;
		
		public Node(Image i, RigidBody rb, Node n, Duration d, int ind) {
			image = i;
			rigidBody = rb;
			next = n;
			delay = d;
			index = ind;
		}
	}

    private class ImageLink {
    	
    	private Node first;
    	
    	public void add(Image image, RigidBody rBody, Duration delay) {
    		
    		if (first == null) {
    			first = new Node(image, rBody, first, delay, 0);
    			first.next = first;
    			return;
    		}
    		Node current = first;
    		while (current.next != first) {
    			current = current.next;
    		}
    		current.next = new Node(image, rBody, first, delay, current.index + 1);
    	}
    }
    
    public void updateImage(long timeLapse) {
    	timeElapsed += timeLapse;
    	if (current.delay.toMillis() < timeElapsed) {
    		rotateImage();
    		timeElapsed = 0l;
    	}
    }
    
    public Supplier<Vector> getPositionSupplier() {
    	return () -> {
    		Vector imgTranslate = new Vector(image.getTranslateX(), image.getTranslateY());
    		double imgWidth = image.getImage().getWidth();
    		double imgHeight = image.getImage().getHeight();
    		return Utilities.nodeTranslationToPhysicsCenter(imgTranslate, imgWidth, imgHeight, height);
    	};
    }
    
    public int getIndex() {
    	return current.index;
    }
    
    private void rotateImage() {
    	current = current.next;
    	image.setImage(current.image);
    }
    
    /**
     * method getImageView
     * @return current ImageView set to be animated
     */
    public ImageView getImageView() {
    	return image;
    }
    
    public Rectangle getRect() {
    	return rectangle;
    }
    
    public RigidBody getRigidBody() {
    	return current.rigidBody;
    }
    
    /**
     * method changeState
     * @param the new state
     */
    public void setState(String state) {
    	changeImage(state);
    }
  
    
    /**s
     * method changePosition
     * @param source the observable that is changed
     * sets the position of the image based on the position of the 
     * sprite as contained in the physics object
     */
    public void updatePosition(Vector poCenter) {
    	double imgWidth = image.getImage().getWidth();
    	double imgHeight = image.getImage().getHeight();
    	Vector imgTranslate = Utilities.physicsCenterToNodeTranslation(poCenter, imgWidth, imgHeight, height);
    	image.setTranslateX(imgTranslate.getX());
    	image.setTranslateY(imgTranslate.getY());
    	rectangle.setTranslateX(imgTranslate.getX());
    	rectangle.setTranslateY(imgTranslate.getY());
    }
    
    /**
     * method changeImage
     * @param state
     * changes the animation image based on the state set
     */
    private void changeImage(String state) {
    	current = paths.get(state).first;
    	image.setImage(current.image);
    	image.setFitWidth(current.image.getWidth());
    	image.setFitHeight(current.image.getHeight());
    	rectangle = new Rectangle(current.image.getWidth(), current.image.getHeight());
    }

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        rotateImage();
        
    }
}