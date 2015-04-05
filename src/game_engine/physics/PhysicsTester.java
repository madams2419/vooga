package game_engine.physics;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import game.SpriteManager;
import game_engine.*;
import game_engine.sprite.Sprite;

public class PhysicsTester extends Application {
	
	private final int fps; // animation frames per second
	private Timeline gameLoop; // main game loop
	private Scene myScene; // javaFX scene
	private Group myGroup; // group of nodes to be displayed in scene
	
	private List<DSprite> myDSprites;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		initDSprites();
		addSprites();
		initGameLoop();
		initGameStage(stg);
		stg.show();
	}
	
	private void handleKeyFrame() {
		
	}
	
	private void handleKeyInput(KeyEvent e) {
		
	}
	
	public void initDSprites() {
		myDSprites = new ArrayList<DSprite>();
		
		PhysicsObject playerPhys = new PhysicsObject(100, 3, 0, 0);
		Node playerNode = new Rectangle(100, 100, Color.BLUE);
		DSprite player = new DSprite(playerPhys, playerNode);
	}
	
	public void addSprites() {
		myDSprites.forEach(dSprite -> myGroup.getChildren().add(dSprite.myNode));
	}
	
	private class DSprite {
		PhysicsObject myPhys;
		Node myNode;	
		
		public DSprite(PhysicsObject phys, Node node) {
			myPhys = phys;
			myNode = node;
		}
		
		public void update() {
			myPhys.update();
			myNode
		}
	}
	
	private final void initGameLoop() {
		Duration framePeriod = Duration.millis(1000 / (float) fps);
		KeyFrame frame = new KeyFrame(framePeriod, e -> handleKeyFrame());

		gameLoop = new Timeline();
		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
	}
	
	public void initGameStage(Stage stg) {
		stg.setTitle("Physics Demo");
		myGroup = new Group();
		myScene = new Scene(myGroup, 400, 400, Color.WHITE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e));
		stg.setScene(myScene);
	}

}
