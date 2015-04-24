package game_player;

import game_engine.Level;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.controls.ControlsManager;
import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class VoogaGame implements IActor {

	private List<Level> levels;
	private Level activeLevel;
	private Group root;
	private Timeline timeline;

	public VoogaGame(double frameRate) {
		levels = new ArrayList<Level>();
		root = new Group();
		timeline = new Timeline(getFrame(frameRate));
	}
	
	private KeyFrame getFrame(double frameRate) {
		return new KeyFrame(Duration.millis(frameRate), (frame) -> update(System.currentTimeMillis()));
	}

	public void addLevel(Level l) {
		levels.add(l);
	}
	
	public IAction getAction(String name) {
		return setActiveLevel;
	}

	private IAction setActiveLevel = (params) -> {
		int index = Integer.parseInt(params[0]);
		activeLevel = levels.get(index);
	};

	public void setActiveLevel(int index) {
		root.getChildren().clear();
		activeLevel = levels.get(index);
		activeLevel.getSprites().forEach(sprite -> {
			root.getChildren().add(sprite.getImageView());
		});
		root.requestFocus();
		ControlsManager controlManager = activeLevel.getControlManager();
		root.setOnKeyPressed(e -> controlManager.handleInput(e));
		root.setOnKeyReleased(e -> controlManager.handleInput(e));

		if (!activeLevel.getSprites().isEmpty()) {
			Sprite sprite = activeLevel.getSprites().get(0);
			sprite.getImageView().toFront();
		}
	}

	public void update(double now) {
		activeLevel.update(now);
	}
	
	public void start() {
		timeline.play();
	}

	public Group getRoot() {
		
		return root;
	}
}