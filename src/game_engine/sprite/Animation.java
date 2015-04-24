package game_engine.sprite;

import game_engine.physics.objects.PhysicsObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Observer;

public class Animation extends Observable implements Observer {

    private ImageView image;
    private Node current;
    Map<String, ImageLink> paths;
    private double lastUpdateTime;

    public Animation() {
    	image = new ImageView();
    	paths = new HashMap<>();
    	lastUpdateTime = System.currentTimeMillis();
    }
    
    public void associateImage(String state, String imagePath, double delay, double height, double width) {
    	if (!paths.containsKey(state)) {
    		paths.put(state, new ImageLink());
    	}
    	paths.get(state).add(new Image(imagePath, width, height, false, true), delay);
    }
    
    protected class Node {
		 
		private Image image;
		private Node next;
		private double delay;
		private int index;
		
		public Node(Image i, Node n, double d, int ind) {
			image = i;
			next = n;
			delay = d;
			index = ind;
		}
	}

    private class ImageLink {
    	
    	private Node first;
    	
    	public void add(Image image, double delay) {
    		
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
    
    public void update() {
    	double currentTime = System.currentTimeMillis();
    	if (current.delay == currentTime - lastUpdateTime) {
    		rotateImage();
    		lastUpdateTime = currentTime;
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
    		image.setTranslateY(physicsObject.getYPosition());
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