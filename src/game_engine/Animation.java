package game_engine;

import game_engine.sprite.Sprite;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.value.ObservableValue;
/**
 * Defines the animations for each sprite
 * @author 
 *
 */
public class Animation implements Observer{
        
	private String myCurrentImage;
	Map<String, String> myPathMap;
	Map<String, Image> myImageMap;
	
	public Animation(Observable sprite){
	   linkToSprite(sprite);
	   myPathMap = new HashMap<>();
	   myImageMap = new HashMap<>();
	}
	
	public void setImage(String state, String ImagePath){
	    myPathMap.put(state, ImagePath);
	}
	
	public void removeImage(String state){
	    myPathMap.remove(state);
	}
	
	public void linkToSprite(Observable sprite){
	    sprite.addObserver(this);
	}
	
	private void changeImage(String state){
	    myCurrentImage = myPathMap.get(state);
	}
	
	public String getImage(){
	    return myCurrentImage;
	}
    
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
       Sprite sprite = (Sprite) o;
       changeImage(sprite.getState());
    }

}
