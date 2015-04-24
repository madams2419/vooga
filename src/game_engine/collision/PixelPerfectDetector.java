package game_engine.collision;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import game_engine.sprite.Sprite;
import game_player.Animation;

/**
 * 
 * @author Michael Lee
 * Pixel Perfect Detector returns boolean to check if precise Image collisions
 * Occurred. Recommended for Player objects
 *
 */
public class PixelPerfectDetector implements ICollisionDetector {
	private Map<String, boolean[][]> myImageMap = new HashMap<>();
	private Sprite mySpriteA;
	private Sprite mySpriteB;
	private Animation myAnimationA;
	private Animation myAnimationB;

	public PixelPerfectDetector(Sprite a, Sprite b) {
		mySpriteA = a;
		mySpriteB = b;
		// myAnimationA = a.getAniamtion();
		// myAnimationB = b.getAnimation();
	}

	public boolean isColliding(Sprite a, Sprite b) {
		//need test
		boolean[][] bitMapA= getBitMap(myAnimationA);
		boolean[][] bitMapB = getBitMap(myAnimationB);

		ImageView s1 = myAnimationA.getImageView();
		double aLeft = s1.getX();
		double aTop = s1.getY();
		double aRight = s1.getX() + s1.getImage().getWidth();
		double aBot = s1.getY() + s1.getImage().getHeight();

		ImageView s2 = myAnimationB.getImageView();
		double bLeft = s2.getX();
		double bTop = s2.getY();
		double bRight = s2.getX() + s2.getImage().getWidth();
		double bBot = s2.getY() + s2.getImage().getHeight();

		double highLeft = Math.max(aLeft, bLeft);
		double highTop = Math.max(aLeft, bLeft);
		double lowRight = Math.min(aRight, bRight);
		double lowBot = Math.min(aBot, bBot);
		

		int startY = (int) (highTop - aTop);
		int endY = (int) (lowBot - aTop);
		int startX = (int) (highLeft - aLeft);
		int endX = (int) (lowRight - aLeft);
		
		boolean[][] mapA = spliceBitMap(bitMapA, startY, endY, startX, endX);
		
		int startY2 = (int) (highTop - bTop);
		int endY2 = (int) (lowBot - bTop);
		int startX2 = (int) (highLeft - bLeft);
		int endX2 = (int) (lowRight - bLeft);
		
		boolean[][] mapB = spliceBitMap(bitMapB, startY2, endY2, startX2, endX2);

		boolean[][] collisionMap = isColliding(mapA, mapB);

		for(boolean[] boolList: collisionMap){
			for(boolean bool: boolList){
				if(bool){
					return true;
				}
			}
		}
		return false;
	}

	private boolean[][] getBitMap(Animation animation) {
		boolean[][] bitMap;
		if(myImageMap.containsKey(animation.getImage())){
			bitMap = myImageMap.get(myAnimationA.getImage());
		}
		else{
			bitMap = createBitMap(myAnimationA.getImageView().getImage());
			myImageMap.put(animation.getImage(), bitMap);
		}
		return bitMap;
	}

	private boolean[][] spliceBitMap(boolean[][] bitMap, int startX, int endX,
			int startY, int endY) {

		boolean[][] bitSplice = new boolean[endY - startY][endX - startX];
		for (int i = 0; i < endY - startY; i++) {
			for (int j = 0; j < endX - startX; j++) {
				bitSplice[i][j] = bitMap[i + startY][j + startX];

			}
		}
		return bitSplice;
	}

	private boolean[][] isColliding(boolean[][] mapA, boolean[][] mapB) {
		boolean[][] bitMap = new boolean[mapA.length][mapA[0].length];
		for (int i = 0; i < mapA.length; i++)
			for (int j = 0; j < mapA[i].length; j++)
				bitMap[i][j] = mapA[i][j] && mapB[i][j];

		return bitMap;
	}

	private boolean[][] createBitMap(Image src) {
		PixelReader reader = src.getPixelReader();
		int width = (int) src.getWidth();
		int height = (int) src.getHeight();
		boolean[][] bitMap = new boolean[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				bitMap[y][x] = reader.getArgb(x, y) != 0;
			}
		return bitMap;
	}

}
