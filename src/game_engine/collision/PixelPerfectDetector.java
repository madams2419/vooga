package game_engine.collision;

import game_player.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

/**
 * 
 * @author Michael Lee Pixel Perfect Detector returns boolean to check if
 *         precise Image collisions Occurred. Recommended for Player objects
 *
 */
public class PixelPerfectDetector {
	private Map<String, boolean[][]> myImageMap = new HashMap<>();
	private ImageView mySpriteA;
	private ImageView mySpriteB;
	private Animation myAnimationA;
	private Animation myAnimationB;
	private List<int[]> coordinateA;// [y,x] coordinates
	private List<int[]> coordinateB;

	public PixelPerfectDetector(ImageView a, ImageView b) {
		mySpriteA = a;
		mySpriteB = b;
		// myAnimationA = a.getAniamtion();
		// myAnimationB = b.getAnimation();
		coordinateA = createCoordinates(mySpriteA.getImage());
		coordinateB = createCoordinates(mySpriteB.getImage());

		// ArrayList<int[]> t = overlapCoordinates(coordinateA, a, coordinateB,
		// b);
		// for (int[] x : t) {
		// System.out.println(Arrays.toString(x));
		// }

	}

	private ArrayList<int[]> createCoordinates(Image src) {
		ArrayList<int[]> coordinate = new ArrayList<int[]>();

		int width = (int) src.getWidth();
		int height = (int) src.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int[] k = { i, j };
				coordinate.add(k);
				// System.out.println(Arrays.toString(k));
			}
		}
		return coordinate;
	}

	public ArrayList<int[]> overlapCoordinates(List<int[]> coordinateA,
			ImageView a, List<int[]> coordinateB, ImageView b) {
		// overlapA correct
		ArrayList<int[]> overlapA = new ArrayList<int[]>();
		for (int i = 0; i < coordinateA.size(); i++) {
			int[] tupleA = { (int) (coordinateA.get(i)[0] + a.getTranslateY()),
					(int) (coordinateA.get(i)[1] + a.getTranslateX()) };
			overlapA.add(tupleA);
		}
		// overlapB correct
		ArrayList<int[]> overlapB = new ArrayList<int[]>();
		for (int i = 0; i < coordinateB.size(); i++) {
			int[] tupleB = { (int) (coordinateB.get(i)[0] + b.getTranslateY()),
					(int) (coordinateB.get(i)[1] + b.getTranslateX()) };
			overlapB.add(tupleB);
		}

		ArrayList<int[]> s = new ArrayList<int[]>();
		for (int[] tupleA : overlapA) {
			for (int[] tupleB : overlapB) {
				if (tupleA[0] == tupleB[0] && tupleA[1] == tupleB[1]) {
					s.add(tupleA);
				}
			}
		}
		return s;
	}

	public boolean isColliding() {
		ArrayList<int[]> overlap = overlapCoordinates(coordinateA, mySpriteA,
				coordinateB, mySpriteB);

		boolean[][] bitMapA = getBitMap(mySpriteA);
		boolean[][] bitMapB = getBitMap(mySpriteB);
		Map<Integer, ArrayList<Integer>> bitOverlapMap = new HashMap<Integer, ArrayList<Integer>>();
		// overlap map A
		ArrayList<int[]> overlapA = new ArrayList<int[]>();
		ArrayList<int[]> overlapB = new ArrayList<int[]>();
		for (int[] tuple : overlap) {
			int[] i = { tuple[0], tuple[1] };
			int[] j = { tuple[0], tuple[1] };
			overlapA.add(i);
			overlapB.add(j);
		}

		for (int[] arr1 : overlapA) {
			int y1 = arr1[0] - (int) mySpriteA.getTranslateY();
			int x1 = arr1[1] - (int) mySpriteA.getTranslateX();
			// int[] tuple = { arr[0], arr[1] };
			boolean k = bitMapA[y1][x1];
			// System.out.println(10*arr1[0]+arr1[1]);
			bitOverlapMap.put(32768 * arr1[0] + arr1[1],
					new ArrayList<Integer>());
			if (k) {
				bitOverlapMap.get(32768 * arr1[0] + arr1[1]).add(1);
			} else {
				bitOverlapMap.get(32768 * arr1[0] + arr1[1]).add(0);
			}
		}

		for (int[] arr2 : overlapB) {

			int y2 = arr2[0] - (int) mySpriteB.getTranslateY();
			int x2 = arr2[1] - (int) mySpriteB.getTranslateX();
			// int[] tuple = { arr[0], arr[1] };
			// System.out.println(arr2[0]);
			// System.out.println(10*arr2[0]+arr2[1]);
			// System.out.println(10*arr2[0]+arr2[1]);
			boolean k = bitMapB[y2][x2];
			// // no put necessary
			if (k) {
				bitOverlapMap.get(32768 * arr2[0] + arr2[1]).add(1);
			} else {
				bitOverlapMap.get(32768 * arr2[0] + arr2[1]).add(0);
			}
		}

		for (int i : bitOverlapMap.keySet()) {
			if (bitOverlapMap.get(i).get(0) == 1
					&& bitOverlapMap.get(i).get(1) == 1) {
				return true;
			}
		}
		return false;
	}

	private boolean[][] getBitMap(ImageView animation) {
		boolean[][] bitMap;
		if (myImageMap.containsKey(animation.getImage())) {
			bitMap = myImageMap.get(myAnimationA.getImage());
		} else {
			bitMap = createBitMap(animation.getImage());
			// myImageMap.put(animation.getImage(), bitMap);
		}
		return bitMap;
	}

	// private boolean[][] spliceBitMap(boolean[][] bitMap, int startX, int
	// endX,
	// int startY, int endY) {
	//
	// boolean[][] bitSplice = new boolean[endY - startY][endX - startX];
	// for (int i = 0; i < endY - startY; i++) {
	// for (int j = 0; j < endX - startX; j++) {
	// bitSplice[i][j] = bitMap[i + startY][j + startX];
	//
	// }
	// }
	// return bitSplice;
	// }

	// private boolean[][] isColliding(boolean[][] mapA, boolean[][] mapB) {
	// boolean[][] bitMap = new boolean[mapA.length][mapA[0].length];
	// for (int i = 0; i < mapA.length; i++)
	// for (int j = 0; j < mapA[i].length; j++)
	// bitMap[i][j] = mapA[i][j] && mapB[i][j];
	//
	// return bitMap;
	// }

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

	// @Override
	// public boolean isColliding(Sprite a, Sprite b) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	// public boolean isColliding() {
	// // need test
	// // boolean[][] bitMapA= getBitMap(myAnimationA);
	// boolean[][] bitMapA = getBitMap(mySpriteA);
	// // boolean[][] bitMapB = getBitMap(myAnimationB);
	// boolean[][] bitMapB = getBitMap(mySpriteB);
	//
	// // ImageView s1 = myAnimationA.getImageView();
	// ImageView s1 = mySpriteA;
	// double aLeft = s1.getX();
	// double aTop = s1.getY();
	// double aRight = s1.getX() + s1.getImage().getWidth();
	// double aBot = s1.getY() + s1.getImage().getHeight();
	//
	// // ImageView s2 = myAnimationB.getImageView();
	// // ImageView s1 = myAnimationA.getImageView();
	// ImageView s2 = mySpriteB;
	// double bLeft = s2.getX();
	// double bTop = s2.getY();
	// double bRight = s2.getX() + s2.getImage().getWidth();
	// double bBot = s2.getY() + s2.getImage().getHeight();
	//
	// double highLeft = Math.max(aLeft, bLeft);
	// double highTop = Math.max(aLeft, bLeft);
	// double lowRight = Math.min(aRight, bRight);
	// double lowBot = Math.min(aBot, bBot);
	//
	// int startY = (int) (highTop - aTop);
	// int endY = (int) (lowBot - aTop);
	// int startX = (int) (highLeft - aLeft);
	// int endX = (int) (lowRight - aLeft);
	// System.out.println(mySpriteA.getTranslateY());
	// // boolean[][] mapA = spliceBitMap(bitMapA, startY, endY, startX, endX);
	// //
	// // int startY2 = (int) (highTop - bTop);
	// // int endY2 = (int) (lowBot - bTop);
	// // int startX2 = (int) (highLeft - bLeft);
	// // int endX2 = (int) (lowRight - bLeft);
	// //
	// // boolean[][] mapB = spliceBitMap(bitMapB, startY2, endY2, startX2,
	// // endX2);
	// //
	// // boolean[][] collisionMap = isColliding(mapA, mapB);
	// //
	// // for(boolean[] boolList: collisionMap){
	// // for(boolean bool: boolList){
	// // if(bool){
	// // return true;
	// // }
	// // }
	// // }
	// return false;
	// }
}
