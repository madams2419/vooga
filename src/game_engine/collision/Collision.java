package game_engine.collision;

import game_engine.behaviors.IBehavior;
import game_engine.physics.PhysicsCollision;
import game_engine.physics.PhysicsCollisionFactory;
import game_engine.sprite.Sprite;
import javafx.scene.image.ImageView;

/**
 * 
 * 
 * @author Michael Lee
 *
 */

public class Collision {
	private Sprite spriteA;
	private Sprite spriteB;
//	private Map<Sprite, Map<IBehavior, String[]>> behaviorList;
	private IBehavior behavior;
	private boolean isRealistic;
	private PhysicsCollision pCollision;

	/**
	 * 
	 * @param a
	 * @param b
	 * @param behave
	 * @param d
	 * @param real
	 * @param p
	 */
	public Collision(Sprite a, Sprite b, IBehavior behave, boolean real) {
		spriteA = a;
		spriteB = b;
		behavior = behave;
		isRealistic = real;
		pCollision = null;
	}

	public void getColliding() {
//		spriteList.stream().filter(sprite -> collidingHitBox(sprite, spriteA)).filter(sprite->collide(sprite,spriteA))
//				.forEach(this::execute);
//		if(collidingHitBox() && collide()){ //TODO collide() throws null pointer exceptions
		if(collidingHitBox()){
			execute();
			
			if(isRealistic){
				pCollision.resolve();
			}
		}
		
	}

	private void execute() {
		behavior.perform();
	}
	
	private boolean collidingHitBox(){
		pCollision = PhysicsCollisionFactory.getCollision(spriteA, spriteB);
		return pCollision.collide();
	}

	private boolean collide() {

////		boolean[][] bitMapA = spriteA.getHitBox().getBitMap();
//		boolean[][] bitMapB = spriteB.getHitBox().getBitMap();
//
//		ImageView s1 = spriteA.getImageView();
//		double aLeft = s1.getX();
//		double aTop = s1.getY();
//		double aRight = s1.getX() + s1.getImage().getWidth();
//		double aBot = s1.getY() + s1.getImage().getHeight();
//
//		ImageView s2 = spriteB.getImageView();
//		double bLeft = s2.getX();
//		double bTop = s2.getY();
//		double bRight = s2.getX() + s2.getImage().getWidth();
//		double bBot = s2.getY() + s2.getImage().getHeight();
//
//		double highLeft = Math.max(aLeft, bLeft);
//		double highTop = Math.max(aLeft, bLeft);
//		double lowRight = Math.min(aRight, bRight);
//		double lowBot = Math.min(aBot, bBot);
//		
//
//		int startY = (int) (highTop - aTop);
//		int endY = (int) (lowBot - aTop);
//		int startX = (int) (highLeft - aLeft);
//		int endX = (int) (lowRight - aLeft);
//		
//		boolean[][] mapA = spliceBitMap(bitMapA, startY, endY, startX, endX);
//		
//		int startY2 = (int) (highTop - bTop);
//		int endY2 = (int) (lowBot - bTop);
//		int startX2 = (int) (highLeft - bLeft);
//		int endX2 = (int) (lowRight - bLeft);
//		
//		boolean[][] mapB = spliceBitMap(bitMapB, startY2, endY2, startX2, endX2);
//
//		boolean[][] collisionMap = isColliding(mapA, mapB);
//
//		for(boolean[] bList: collisionMap){
//			for(boolean b: bList){
//				if(b){
//					return true;
//				}
//			}
//		}
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
