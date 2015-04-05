package game_engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import game_engine.sprite.Sprite;
/**
 * Class checks for collision occurrences between sprites
 * @author Michael Lee
 *
 */

public class CollisionEngine {
	boolean[][] bitMap;
	
	public void executeCollision(Sprite spriteA, Sprite spriteB){
		
	}
	
	public static void main (String[] args){
		CollisionEngine engine = new CollisionEngine();
		 InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/bluedevilmike/Documents/workspace/"
			 		+ "voogasalad_HighScrollers/src/game_engine/images/lightbulb.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image bulb=new Image(inputStream);
		boolean[][] bitmap = engine.createBitMap(bulb);
		//position for 1st image centered (0,0) size 32x32
		int aLeft = -16;
		int aTop =-16;
		int aRight =16 ;
		int aBot = 16;
		int bLeft = 15;
		int bTop = 15;
		int bRight = 47;
		int bBot = 47;
		//position for 2nd image centered (31,31) size 32x32
		int highLeft;
		int lowRight;
		int highTop;
		int lowBot;
		if(aLeft > bLeft)
			highLeft = aLeft;
		else
			highLeft = bLeft;
		if(aTop > bTop)
			highTop = aTop;
		else
			highTop = bTop;
		if(aRight > bRight)
			lowRight = bRight;
		else
			lowRight = aRight;
		if(aBot > bBot)
			lowBot = bBot;
		else
			lowBot = aBot;
		System.out.println(highLeft);
		System.out.println(lowRight);
		System.out.println(highTop);
		System.out.println(lowBot);
		boolean[][] map = new boolean[lowBot-highTop][lowRight - highLeft];
		//spriteA loop
//		int startY = highTop - aTop;
//		int endY = lowBot - aTop;
//		int startX = highLeft - aLeft;
//		int endX = lowRight - aLeft;
//		System.out.println(startY);
//		System.out.println(endY);
//		System.out.println(startX);
//		System.out.println(endX);
		int startY = highTop - bTop;
		int endY = lowBot - bTop;
		int startX = highLeft - bLeft;
		int endX = lowRight - bLeft;
		System.out.println(startY);
		System.out.println(endY);
		System.out.println(startX);
		System.out.println(endX);

	}
	//two sprites with positions
	//figure out collision box -> return to physics engine
	//get relevant portion of bitmap 
	
	

	private boolean isColliding(boolean[][] mapA, boolean[][] mapB){
		for()
	}
	
	
	private boolean[][] createBitMap(Image src){
		PixelReader reader = src.getPixelReader();
		int width = (int)src.getWidth();
		int height = (int)src.getHeight();
		boolean[][] bitMap = new boolean[height][width];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x ++){
				bitMap[y][x] = reader.getArgb(x, y) == 0;
			}
		return bitMap;
	}
}
