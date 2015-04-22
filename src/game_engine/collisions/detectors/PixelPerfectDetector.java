package game_engine.collisions.detectors;


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
	// private Map<String, boolean[][]> myImageMap = new HashMap<>();
	private ImageView mySpriteA;
	private ImageView mySpriteB;
	private List<int[]> coordinateA;// [y,x] coordinates
	private List<int[]> coordinateB;
	private Map<Image, boolean[][]> imageToBits = new HashMap<Image, boolean[][]>();

	public PixelPerfectDetector(ImageView a, ImageView b) {
		mySpriteA = a;
		mySpriteB = b;
		// myAnimationA = a.getAniamtion();
		// myAnimationB = b.getAnimation();
		coordinateA = createCoordinates(mySpriteA.getImage());
		coordinateB = createCoordinates(mySpriteB.getImage());
		

	}
	
	public boolean isColliding(){
		if(isImageColliding()){
			return isPixelColliding();
		}
		return false;
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

	private ArrayList<int[]> overlapCoordinates(List<int[]> coordinateA,
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

	private boolean isImageColliding(){
		return mySpriteA.getBoundsInParent().intersects(mySpriteB.getBoundsInParent());

	}
	
	private boolean isPixelColliding() {
		
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
		if (imageToBits.containsKey(animation.getImage())) {
			bitMap = imageToBits.get(animation.getImage());
		} else {
			bitMap = createBitMap(animation.getImage());
			imageToBits.put(animation.getImage(), bitMap);
		}
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
