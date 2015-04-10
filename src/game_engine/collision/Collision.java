package game_engine.collision;

import game_engine.IBehavior;
import game_engine.physics.PhysicsEngine;
import game_engine.sprite.Sprite;

import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;

/**
 * Defines behavior when collision occurs
 * 
 * @author Michael Lee
 *
 */

public class Collision {
	private Sprite spriteA;
	private List<Sprite> spriteList;
	private Map<Sprite, Map<IBehavior, String[]>> behaviorList;
	private CollisionDirection direction;

	
	public Collision(Sprite a, List<Sprite> list,
			Map<Sprite, Map<IBehavior, String[]>> b, CollisionDirection d) {
		spriteA = a;
		spriteList = list;
		behaviorList = b;
		direction = d;
	}

	public void getColliding() {
		spriteList.stream().filter(sprite -> collidingHitBox(sprite, spriteA)).filter(sprite->collide(sprite,spriteA))
				.forEach(this::execute);
	}

	private void execute(Sprite sprite) {
		behaviorList.get(sprite).forEach((behavior, params) -> behavior.execute(params) );
	}
	
	private boolean collidingHitBox(Sprite spriteA, Sprite spriteB){
		return spriteA.getHitBox().intersects(spriteB.getHitBox());
	}

	private boolean collide(Sprite spriteA, Sprite spriteB) {
		// change parameters back to sprites

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

		double highLeft;
		double lowRight;
		double highTop;
		double lowBot;

		if (aLeft > bLeft)
			highLeft = aLeft;
		else
			highLeft = bLeft;
		if (aTop > bTop)
			highTop = aTop;
		else
			highTop = bTop;
		if (aRight > bRight)
			lowRight = bRight;
		else
			lowRight = aRight;
		if (aBot > bBot)
			lowBot = bBot;
		else
			lowBot = aBot;

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
