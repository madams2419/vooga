package game_player;

import game_engine.IBehavior;
import game_engine.Level;
import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

public class VoogaGame extends AnimationTimer {

	private List<Level> levels;
	private Level activeLevel;

	public VoogaGame() {
		levels = new ArrayList<Level>();
	}

	public void addLevel(Level l) {
		levels.add(l);
	}

	public IBehavior getSetActiveLevelBehavior() {
		IBehavior setActiveLevel = (params) -> {
			int index = Integer.parseInt(params[0]);
			activeLevel = levels.get(index);
		};
		return setActiveLevel;
	}

	public void setActiveLevel(int index) {
		activeLevel = levels.get(index);
	}

	public void handle(long now) {
		activeLevel.update();
	}

	public Group getRoot() {
		Group root = new Group();
		for (Sprite sprite : activeLevel.getLayers().get(0).getSprites()) {
			root.getChildren().add(sprite.getImageView());
		}
		return root;
	}

	public void setFPS(double fps) {

	}

	public double getFPS(){
		return 0.000;
	}
}