package game_player;

import game_engine.IAction;
import game_engine.Level;
import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import com.sun.prism.paint.Color;

public class VoogaGame extends AnimationTimer {
    
    private List<Level> levels;
    private Level activeLevel;
    private Group root;
    
    public VoogaGame() {
	levels = new ArrayList<Level>();
    }
    
    public void addLevel(Level l) {
	levels.add(l);
    }
    
    public IAction getSetActiveLevelBehavior() {
	IAction setActiveLevel = (params) -> {
	    int index = Integer.parseInt(params[0]);
	    activeLevel = levels.get(index);
	};
	return setActiveLevel;
    }
    
    public void setActiveLevel(int index) {
	activeLevel = levels.get(index);
    }

    public void handle(long now) {
	activeLevel.update(now);
    }
    
    public Group getRoot() {
	root = new Group();
	return root;
    }
}