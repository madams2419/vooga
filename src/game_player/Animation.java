package game_player;

import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import game_engine.sprite.Sprite;
import game_player.Utilities;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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
	private Sprite mySprite;
	Map<String, Node> myPathMap;

	public Animation(Sprite sprite, PhysicsObject physics) {
	     
		linkToSprite(sprite);
//		linkToSprite(physics);
		mySprite = sprite;
		myPathMap = new HashMap<>();
		myImageView = new ImageView();
//		updatePosition(physics);
	}
	
	public class Node{
	    String image;
	    Node next;
	    
	    public Node(){
	        image = "";
	        next = null;
	    }
	    
	    public Node(String image, Node next){
	        this.image = image;
	        this.next = next;
	    }
	    
	}
//TODO implement linked list image transition
    public void setImage (String state, String ImagePath) {
        Node currImage = new Node(ImagePath,null);
        System.out.println(ImagePath);
        try {
            myPathMap.get(state).next = currImage;
            System.out.println("added");
        }
        catch (Exception e) {
            myPathMap.put(state, currImage);
            System.out.println("added");
        }
        System.out.println(myPathMap.size());
    }

    public void removeImage (String state) {
        myPathMap.remove(state);
    }

    public String getImage () {
        return myCurrentImage;
    }

    private void linkToSprite (Observable sprite) {
        sprite.addObserver(this);
    }

    private void changeImage (String state) {
       
            try {
                myCurrentNode = myCurrentNode.next;
            }
            catch (Exception e) {
                 try { 
                    myCurrentNode = myPathMap.get(state);
                   
                }
                catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            myCurrentImage = myCurrentNode.image;
            myImageView.setImage(new Image(getClass().getResourceAsStream(
                                                                          myCurrentImage)));
        
    }

    public ImageView getImageView () {
        return myImageView;
    }

	public void update(Observable o, Object arg) {
		try {
			Sprite sprite;
			sprite = (Sprite) o;
			changeImage(sprite.getState());
		} catch (Exception e) {
//			 TODO Auto-generated catch block
			PhysicsObject physics;
			physics = (PhysicsObject) o;
//			updatePosition(physics);
		    e.printStackTrace();
		}
	}
	
//	public void updatePosition(PhysicsObject physics) {
//		Vector jfxPosition = Utilities.physicsCenterToUpperLeft(physics);
//		myImageView.setTranslateX(jfxPosition.getX());
//		myImageView.setTranslateY(jfxPosition.getY());
//	}

}
