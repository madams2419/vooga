package game_player;

import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Observer;

/**
 * Defines the animations for each sprite
 * 
 * @author
 *
 */
public class Animation implements Observer {

    private ImageView myImageView;
    private Node myCurrentNode;
    private String myCurrentImage;
    Map<String, Node> myPathMap;

    public Animation() {
	myPathMap = new HashMap<>();
	myImageView = new ImageView();
    }

    public class Node {
	String image;
	Node next;

	public Node() {
	    image = "";
	    next = null;
	}

	public Node(String image, Node next) {
	    this.image = image;
	    this.next = next;
	}

    }

    public void setImage(String state, String ImagePath) {
	Node currImage = new Node(ImagePath, null);
	try {
	    myPathMap.get(state).next = currImage;
	} catch (Exception e) {
	    myPathMap.put(state, currImage);
	}
    }

    public void removeImage(String state) {
	myPathMap.remove(state);
    }

    public String getImage() {
	return myCurrentImage;
    }

    private void changeImage(String state) {

	try {
	    myCurrentNode = myCurrentNode.next;
	} catch (Exception e) {
	    try {
		myCurrentNode = myPathMap.get(state);

	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}

	myCurrentImage = myCurrentNode.image;
	myImageView.setImage(new Image(getClass().getResourceAsStream(
		myCurrentImage)));

    }

    public ImageView getImageView() {
	return myImageView;
    }

    public void update(Observable o, Object arg) {
	try {
	    Sprite sprite = (Sprite) o;
	    String state = (String) arg;
	    changeImage(state);
	}
	catch (Exception e) {
	    try {
		IPhysicsObject physObj = (IPhysicsObject) o;
		Vector position = (Vector) arg;
		myImageView.setTranslateX(position.getX());
		myImageView.setTranslateY(position.getY());
	    }
	    catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
    }

}
