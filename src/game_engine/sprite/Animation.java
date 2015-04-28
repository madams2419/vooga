package game_engine.sprite;

import game_engine.physics.Vector;
import game_engine.physics.PhysicsObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Observer;
/**
 * 
 * @author Kevin Chang, Brian Lavalee
 * Class to hold images and deal with switching images in sprites
 */
public class Animation extends Observable implements Observer {

    private ImageView image;
    private Node current;
    Map<String, ImageLink> paths;
    private long timeElapsed;
    private double height;

    public Animation(double h) {
    	image = new ImageView();
    	paths = new HashMap<>();
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
    public void associateImage(String state, String imagePath, double delay, double height, double width) {
    	if (!paths.containsKey(state)) {
    		paths.put(state, new ImageLink());
    	}
    	
    	try {
			FileInputStream fis = new FileInputStream(imagePath);
			paths.get(state).add(new Image(fis, width, height, false, true), Duration.seconds(delay));
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
		private Node next;
		private Duration delay;
		private int index;
		
		public Node(Image i, Node n, Duration d, int ind) {
			image = i;
			next = n;
			delay = d;
			index = ind;
		}
	}

    private class ImageLink {
    	
    	private Node first;
    	
    	public void add(Image image, Duration delay) {
    		
    		if (first == null) {
    			first = new Node(image, first, delay, 0);
    			first.next = first;
    			return;
    		}
    		Node current = first;
    		while (current.next != first) {
    			current = current.next;
    		}
    		current.next = new Node(image, first, delay, current.index + 1);
    	}
    }
    
    public void update(long timeLapse) {
    	timeElapsed += timeLapse;
    	if (current.delay.toMillis() < timeElapsed) {
    		rotateImage();
    		timeElapsed = 0l;
    	}
    }
    
    public Vector getPosition() {
        return new Vector(image.getTranslateX(), -image.getTranslateY() + height - image.getImage().getHeight());
    }
    
    public int getIndex() {
    	return current.index;
    }
    
    private void rotateImage() {
    	current = current.next;
    	image.setImage(current.image);
    	setChanged();
    	notifyObservers();
    }
    
    /**
     * method getImageView
     * @return current ImageView set to be animated
     */
    public ImageView getImageView() {
    	return image;
    }
    
    /**
     * method update
     * observes and listens for a state change or position change in the sprite
     */
    public void update(Observable source, Object arg) {
    	changeState(source);
    	changePosition(source);
    }
    
    /**
     * method changeState
     * @param source the observable that is changed
     * sets state based on the changed sprite state
     */
    private void changeState(Observable source) {
    	try {
    		Sprite sprite = (Sprite) source;
    		changeImage(sprite.getState());
    	}
    	catch (Exception e) {
    		// do nothing
    	}
    }
  
    
    /**
     * method changePosition
     * @param source the observable that is changed
     * sets the position of the image based on the position of the 
     * sprite as contained in the physics object
     */
    private void changePosition(Observable source) {
    	try { 
    		PhysicsObject physicsObject = (PhysicsObject) source;
    		image.setTranslateX(physicsObject.getPositionPixels().getX());
    		image.setTranslateY(height - physicsObject.getPositionPixels().getY() - image.getImage().getHeight());
    	}
    	catch (Exception e) {
    		// do nothing
    	}
    }
    
    /**
     * method changeImage
     * @param state
     * changes the animation image based on the state set
     */
    private void changeImage(String state) {
    	current = paths.get(state).first;
    	System.out.println(current);
    	image.setImage(current.image);
    }
}