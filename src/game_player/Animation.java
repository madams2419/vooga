package game_player;

import game_engine.collision.HitBox;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import game_engine.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Defines the animations for each sprite
 * @author 
 *
 */
public class Animation implements Observer{
        
        private ImageView myImageView;
	private String myCurrentImage;
	private HitBox myHitBox;
	Map<String, String> myPathMap;

	
	public Animation(Observable sprite,PhysicsObject physics){
	   linkToSprite(sprite);
	   linkToSprite(physics);
	   myPathMap = new HashMap<>();
	   myImageView = new ImageView();
	   myHitBox = new HitBox(myImageView);
	}
	
	public void setImage(String state, String ImagePath){
	    myPathMap.put(state, ImagePath);
	}
	
	public void removeImage(String state){
	    myPathMap.remove(state);
	}
	
	public String getImage(){
	    return myCurrentImage;
	}
	
	private void linkToSprite(Observable sprite){
	    sprite.addObserver(this);
	}
	
	private void changeImage(String state){
	    if(myPathMap.containsKey(state)){
	    myCurrentImage = myPathMap.get(state);
	    myImageView.setImage(new Image(getClass().getResourceAsStream(
                                myCurrentImage))); 
	    }
	}
	
	public ImageView getImageView(){
	    return myImageView;
	}
	
	public HitBox getHitBox(){
	    return myHitBox;
	}
	
	
    
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
<<<<<<< HEAD
    try {
        Sprite sprite;
        sprite = (Sprite) o;
        changeImage(sprite.getState());
    }
    catch (Exception e) {
        // TODO Auto-generated catch block
        PhysicsObject physics;
        physics = (PhysicsObject) o;
        Vector jfxPosition = Utilities.normalToJFXCoords(physics.getPositionPixels());
        myImageView.setTranslateX(jfxPosition.getX());
        myImageView.setTranslateY(jfxPosition.getY());
    }
     
=======
       Sprite sprite = (Sprite) o;
       changeImage(sprite.getState());
       //myImageView.setTranslateX(sprite.getPhysicsObject().getX());
       //myImageView.setTranslateY(sprite.getPhysicsObject().getY());
>>>>>>> 7d5e946f9ea3ce34ddf23ca286c9b20436d7254d
    }

}
