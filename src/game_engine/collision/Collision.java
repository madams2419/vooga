package game_engine.collision;

import game_engine.behaviors.IBehavior;
import game_engine.sprite.Sprite;
import javafx.scene.image.ImageView;

/**
 * Defines behavior when collision occurs
 * 
 * @author Michael Lee
 *
 */

public class Collision {
	private Sprite spriteA;
	private Sprite spriteB;
	private IBehavior behaviorList;
	private CollisionDirection direction;

	
	public Collision(Sprite a, Sprite b,
			IBehavior behaviors, CollisionDirection d) {
		spriteA = a;
		spriteB = b;
		behaviorList = behaviors;
		direction = d;
	}

	public void getColliding() {
		if (collidingHitBox (spriteA, spriteB)) {
		    behaviorList.perform();
		}
	}
	
	private boolean collidingHitBox(Sprite spriteA, Sprite spriteB){
		return spriteA.getHitBox().intersects(spriteB.getHitBox());
	}

	private boolean collide(Sprite spriteA, Sprite spriteB) {
		boolean[][] bitMapA = spriteA.getHitBox().getBitMap();
		boolean[][] bitMapB = spriteB.getHitBox().getBitMap();

		ImageView s1 = spriteA.getImageView();
		double aLeft = s1.getX();
		double aTop = s1.getY();
		double aRight = s1.getX() + s1.getImage().getWidth();
		double aBot = s1.getY() + s1.getImage().getHeight();

		ImageView s2 = spriteB.getImageView();
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

		for(boolean[] bList: collisionMap){
			for(boolean b: bList){
				if(b){
					return true;
				}
			}
		}
		return false;
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
}
