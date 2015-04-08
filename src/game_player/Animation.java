package game_player;

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
	Map<String, String> myPathMap;

	
	public Animation(Observable sprite){
	   linkToSprite(sprite);
	   myPathMap = new HashMap<>();
	   myImageView = new ImageView();
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
	    myCurrentImage = myPathMap.get(state);
	    myImageView.setImage(new Image(getClass().getResourceAsStream(
                                myCurrentImage))); 
	}
	
	public ImageView getImageView(){
	    return myImageView;
	}
	
	
    
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
       Sprite sprite = (Sprite) o;
       changeImage(sprite.getState());
       myImageView.setTranslateX(sprite.getPhysicsObject().getX());
       myImageView.setTranslateY(sprite.getPhysicsObject().getY());
    }

}
