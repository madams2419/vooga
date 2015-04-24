package game_engine.scrolling;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public final class SceneUtil {
    
    private SceneUtil() {
        
    }
    
    public static boolean isOnScreen (Node node) {
        return isOnScreen (node, node.getScene().getWidth(), node.getScene().getHeight());
    }
    
    public static boolean isOnScreen (Node node, double width, double height) {
        Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
        return nodeBounds.intersects(0, 0, width, height);
    }
    
    public static Point2D getScenePos (Node node) {
        double posX = node.getLocalToSceneTransform().getTx();
        double posY = node.getLocalToSceneTransform().getTy();
        return new Point2D (posX, posY);
    }
    
    public static Bounds getBoundsInScene (Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }
}
