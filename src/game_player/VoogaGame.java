package game_player;

import game_engine.BasicScroller;
import game_engine.Level;
import game_engine.ScrollTracker;
import game_engine.behaviors.IAction;
import game_engine.control.ControlManager;
import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

public class VoogaGame extends AnimationTimer {
    
    private List<Level> levels;
    private Level activeLevel;
    private Group root;
    
    public VoogaGame() {
	levels = new ArrayList<Level>();
	root = new Group();
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
        root.getChildren().clear();
	activeLevel = levels.get(index);
	activeLevel.getSprites().forEach(sprite -> {
	    root.getChildren().add(sprite.getImageView());
	});
	root.requestFocus();
	ControlManager controlManager = activeLevel.getControlManager();
	root.setOnKeyPressed(e -> controlManager.handleKeyEvent(e.getCode(), true));
        root.setOnKeyReleased(e -> controlManager.handleKeyEvent(e.getCode(), false));

        if (!activeLevel.getSprites().isEmpty()){
            Sprite sprite = activeLevel.getSprites().get(0);
            sprite.getImageView().toFront();
            BasicScroller scroller = new BasicScroller (root, sprite.getImageView().getTranslateX(), sprite.getImageView().getTranslateY());
            ScrollTracker tracker = new ScrollTracker(scroller);
            tracker.setXTracker(sprite.getImageView().translateXProperty());
            tracker.setYTracker(new SimpleDoubleProperty(sprite.getImageView().getTranslateY()));
        }
    }
    
    

    public void handle(long now) {
	activeLevel.update(now);
    }
    
    public Group getRoot() {
	return root;
    }

    public IAction getAction (String name) {
        if (name.equals("activeLevel")){
            return getSetActiveLevelBehavior();
        }
        return (params) -> {};
    }
}