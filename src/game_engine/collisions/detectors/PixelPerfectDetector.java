package game_engine.collisions.detectors;

import game_engine.sprite.Sprite;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

/**
 * 
 * @author Michael Lee Pixel Perfect Detector returns boolean to check if
 *         precise Image collisions Occurred. Recommended for Player objects
 *
 */

public class PixelPerfectDetector implements ICollisionDetector<Sprite> {

    private static Map<Image, boolean[][]> imageToBits = new HashMap<Image, boolean[][]>();

    public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
    	ImageView objectA = spriteA.getImageView();
    	ImageView objectB = spriteB.getImageView();
    	
    	Point xBounds = intersectX(objectA, objectB);
        Point yBounds = intersectY(objectA, objectB);
        
        boolean[][] bitMapA = getBitMap(objectA);
        boolean[][] bitMapB = getBitMap(objectB);
        

        int translateXA = (int) objectA.getTranslateX();
        int translateYA = (int) objectA.getTranslateY();
        int translateXB = (int) objectB.getTranslateX();
        int translateYB = (int) objectB.getTranslateY();
        
        for (int i = xBounds.x; i < xBounds.y && i >= translateXA && i >= translateXB; i++) {
            for (int j = yBounds.x; j < yBounds.y && j >= translateYA && j >= translateYB; j++) {
                boolean a = bitMapA[j - translateYA][i - translateXA];
                boolean b = bitMapB[j - translateYB][i - translateXB];
                if (a && b) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Point intersectX (ImageView viewA, ImageView viewB) {
        Image imageA = viewA.getImage();
        Image imageB = viewB.getImage();
        
        double startAX = viewA.getTranslateX();
        double endAX = startAX + (int) imageA.getWidth();
        double startBX = viewB.getTranslateX();
        double endBX = startBX + (int) imageB.getWidth();
        return getMiddle(startAX, endAX, startBX, endBX);
    }
    
    private Point intersectY (ImageView viewA, ImageView viewB) {
        Image imageA = viewA.getImage();
        Image imageB = viewB.getImage();
        
        double startAY = viewA.getTranslateY();
        double endAY = startAY + imageA.getHeight();
        double startBY = viewB.getTranslateY();
        double endBY = startBY + imageB.getHeight();
        return getMiddle(startAY, endAY, startBY, endBY);
    }
    
    private Point getMiddle (double x1, double x2, double x3, double x4) {
        double[] array = {x1, x2, x3, x4};
        Arrays.sort(array);
        return new Point ((int) array[1], (int) array[2]);
    }

    private boolean[][] getBitMap (ImageView animation) {
        boolean[][] bitMap;
        if (imageToBits.containsKey(animation.getImage())) {
            bitMap = imageToBits.get(animation.getImage());
        }
        else {
            bitMap = createBitMap(animation.getImage());
            imageToBits.put(animation.getImage(), bitMap);
        }
        return bitMap;
    }

    private boolean[][] createBitMap (Image src) {
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
