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
        
        private String currentImage = "";
	Map<String, String> pathMap;
	Map<String, Image> imageMap;
	
	public Animation(Observable sprite){
	   linkToSprite(sprite);
	   pathMap = new HashMap<>();
	   imageMap = new HashMap<>();
	}
	
	public void setImage(String state, String ImagePath){
	    pathMap.put(state, ImagePath);
	}
	
	public void removeImage(String state){
	    pathMap.remove(state);
	}
	
	private void linkToSprite(Observable sprite){
	    sprite.addObserver(this);
	}
	
	public void changeImage(String state){
	    currentImage = pathMap.get(state);
	}
	
	public String getImage(){
	    return currentImage;
	}
    
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
       Sprite sprite = (Sprite) o;
       System.out.println(sprite.getState());
       changeImage(sprite.getState());
    }

}
