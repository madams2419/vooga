package game_player;

import game_engine.Level;
import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.control.*;
import game_engine.controls.ControlsManager;
import game_engine.sprite.TransitionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class VoogaGame implements IActor {
	private List<Level> levels;
	private Level activeLevel;
	private Group root;
	private double width, height;
	private Timeline animation;
	private ControlsManager controlsManager;
	private ControlManagerFactory controlFactory;
	private TransitionManager transitionManager;
	private Map<String, IAction> actions;
	private final String KEYBOARD = "keyboard";

	public VoogaGame(double fps, double w, double h) {
		levels = new ArrayList<Level>();
		root = new Group();
		width = w;
		height = h;
		animation = new Timeline(fps, getFrame(fps));
		animation.setCycleCount(Timeline.INDEFINITE);
		actions = buildActionMap();
	}
	
	private KeyFrame getFrame(double fps) {
		double framePeriod = 1/fps;
		return new KeyFrame(Duration.seconds(framePeriod), (frame) -> update(framePeriod));
	}
	
    @IActionAnnotation(description = "Sets the active level to the parameter", numParams = 1, paramDetails = "level's id")	
    private IAction setActiveLevel = (params) -> {
        int index = Integer.parseInt(params[0]);
        setActiveLevel(index);
    };
    
    public void addLevel (Level level) {
        levels.add(level);
    }

    public void setActiveLevel (int index) {
        root.getChildren().clear();
        activeLevel = levels.get(index);
        controlsManager = activeLevel.getControlManager();
        controlFactory = activeLevel.getControlFactory();
        activeLevel.start(width, height);
        root.getChildren().add(activeLevel.getRoot());
        transitionManager.playTransitions();
    }

	
	public void setTransitionManager(TransitionManager manager){
	    transitionManager =manager; 
	}
	
	public TransitionManager getTransitionManager(){
	    return transitionManager;
	}

	public void update(double framePeriod) {
		activeLevel.update(framePeriod);
	}
	
	protected Group getRoot() {
		return root;
	}
	
	public void start() {
		Stage stage = new Stage();
		Scene scene = new Scene(root, width, height);
		//scene.setOnKeyPressed(e -> controlsManager.handleInput(e));
		//scene.setOnKeyReleased(e -> controlsManager.handleInput(e));
		scene.setOnKeyPressed(e -> controlFactory.getControlManager(KEYBOARD).handleEvent(e));
		scene.setOnKeyReleased(e -> controlFactory.getControlManager(KEYBOARD).handleEvent(e));
		System.out.println("Num of key control is "+((SceneControlManager)controlFactory.getControlManager(KEYBOARD)).getKeyCount());
		stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
		stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
		root.requestFocus();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(e -> animation.stop());
		animation.play();
	}

    @Override
    public IAction getAction (String name) {
        return actions.get(name);
    }

    public double getHeight () {
        return height;
    }
}
