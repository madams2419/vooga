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
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VoogaGame implements IActor {

	private List<Level> levels;
	private Level activeLevel;
	private Group root;
	private double width, height;
	private Timeline animation;
	private ControlsManager controlsManager;
	private long lastUpdateTime;

	public VoogaGame(double fps, double w, double h) {
		levels = new ArrayList<Level>();
		root = new Group();
		width = w;
		height = h;
		animation = new Timeline(fps, getFrame(fps));
		animation.setCycleCount(Timeline.INDEFINITE);
		lastUpdateTime = 0l;
	}
	
	private KeyFrame getFrame(double fps) {
		return new KeyFrame(Duration.millis(fps), (frame) -> update(System.currentTimeMillis()));
	}

	public void addLevel(Level l) {
		levels.add(l);
	}
	
	public double getHeight() {
		return height;
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
		controlsManager = activeLevel.getControlManager();

		if (!activeLevel.getSprites().isEmpty()) {
			Sprite sprite = activeLevel.getSprites().get(0);
			sprite.getImageView().toFront();
		}
	}
	
	public void update(long currentTime) {
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
		}
		
		activeLevel.update(currentTime - lastUpdateTime);
		lastUpdateTime = currentTime;
	}
	
	public void start() {
		Stage stage = new Stage();
		stage.setHeight(height);
		stage.setWidth(width);
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> controlsManager.handleInput(e));
		scene.setOnKeyReleased(e -> controlsManager.handleInput(e));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		animation.play();
	}
}