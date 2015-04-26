package game_engine.sprite;

import game_engine.physics.objects.PhysicsObject;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Observer;

public class Animation extends Observable implements Observer {

    private ImageView image;
    private Node current;
    Map<String, ImageLink> paths;
    private double timeElapsed;
    private double lastUpdateTime;
    private double height;

    public Animation(double h) {
    	image = new ImageView();
    	paths = new HashMap<>();
    	timeElapsed = 0;
    	height = h;
    }
    
    public void associateImage(String state, FileInputStream imagePath, double delay, double height, double width) {
    	if (!paths.containsKey(state)) {
    		paths.put(state, new ImageLink());
    	}
    	paths.get(state).add(new Image(imagePath, width, height, false, true), Duration.seconds(delay));
    }
    
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
    			return;
    		}
    		Node current = first;
    		while (current.next != first) {
    			current = current.next;
    		}
    		current.next = new Node(image, first, delay, current.index + 1);
    	}
    }
    
    public void update(double currentTime) {
    	double timeLapse = currentTime < lastUpdateTime ? currentTime : currentTime - lastUpdateTime;
    	timeElapsed += timeLapse;
    	lastUpdateTime = currentTime;
    	if (current.delay.toMillis() < timeElapsed) {
    		//rotateImage();
    		timeElapsed = 0;
    	}
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
    
    public ImageView getImageView() {
    	return image;
    }
    
    public void update(Observable source, Object arg) {
    	changeState(source);
    	changePosition(source);
    }
    
    private void changeState(Observable source) {
    	try {
    		Sprite sprite = (Sprite) source;
    		changeImage(sprite.getState());
    	}
    	catch (Exception e) {
    		// do nothing
    	}
    }
    
    private void changePosition(Observable source) {
    	try {
    		PhysicsObject physicsObject = (PhysicsObject) source;
    		image.setTranslateX(physicsObject.getXPosition());
    		image.setTranslateY(height - physicsObject.getYPosition() - image.getImage().getHeight());
    	}
    	catch (Exception e) {
    		// do nothing
    	}
    }
    
    private void changeImage(String state) {
    	current = paths.get(state).first;
    	image.setImage(current.image);
    }
}