package game_engine.collision;

import game_engine.scrolling.SceneUtil;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
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
    private static Map<Image, boolean[][]> imageToBits = new HashMap<Image, boolean[][]>();

    public PixelPerfectDetector (ImageView a, ImageView b) {
        mySpriteA = a;
        mySpriteB = b;
    }

    public boolean isColliding () {
        if (isImageColliding()) {
            return isPixelColliding();
        }
        return false;
    }


    private boolean isImageColliding () {
        Bounds boundA = SceneUtil.getBoundsInScene(mySpriteA);
        Bounds boundB = SceneUtil.getBoundsInScene(mySpriteB);
        return boundA.intersects(boundB);
    }

    private boolean isPixelColliding () {
        Point xBounds = intersectX(mySpriteA, mySpriteB);
        Point yBounds = intersectY(mySpriteA, mySpriteB);
        
        boolean[][] bitMapA = getBitMap(mySpriteA);
        boolean[][] bitMapB = getBitMap(mySpriteB);
        
        int translateXA = (int) mySpriteA.getLocalToSceneTransform().getTx();
        int translateYA = (int) mySpriteA.getLocalToSceneTransform().getTy();
        int translateXB = (int) mySpriteB.getLocalToSceneTransform().getTx();
        int translateYB = (int) mySpriteB.getLocalToSceneTransform().getTy();
        for (int i = xBounds.x; i < (int) xBounds.y; i++) {
            for (int j = yBounds.x; j < yBounds.y; j++) {
                boolean a= bitMapA[j - translateYA][i - translateXA];
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
        
        double startAX = viewA.getLocalToSceneTransform().getTx();
        double endAX = startAX + imageA.getWidth();
        double startBX = viewB.getLocalToSceneTransform().getTx();
        double endBX = startBX + imageB.getWidth();
        return getMiddle(startAX, endAX, startBX, endBX);
    }
    
    private Point intersectY (ImageView viewA, ImageView viewB) {
        Image imageA = viewA.getImage();
        Image imageB = viewB.getImage();
        
        double startAY = viewA.getLocalToSceneTransform().getTy();
        double endAY = startAY + imageA.getHeight();
        double startBY = viewB.getLocalToSceneTransform().getTy();
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
