package authoring.userInterface;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteCreator {
	private static final int WIDTH =512;
    private static final int HEIGHT=512;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet=null;

    //Creating an ArrayList of Players based on spritesheets
    private BufferedImage player;
    private ArrayList<BufferedImage> Player=new ArrayList<>();
    private void init(){
    	BufferedImageLoader loader=new BufferedImageLoader();
    	try{
    		spriteSheet=loader.loadImage("/Users/hojeanniechung/Documents/workspace_308/voogasalad_HighScrollers/res/bouncy_ball.jpg");
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	SpriteSheet ss = new SpriteSheet(spriteSheet);
    	for(int i=0; i<WIDTH/60; i++){
    		for(int j=0; j<HEIGHT/60; j++){
    			Player.add(ss.grabImage(i,j,60,60));
    		}
    	}    
    	System.out.println(Player.toString());
    }
    
}
