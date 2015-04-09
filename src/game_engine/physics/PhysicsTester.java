package game_engine.physics;

import game_engine.Layer;
import game_engine.sprite.Player;
import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PhysicsTester extends Application {

	private static final int fps = 60;
	private static final int width = 400;
	private static final int height = 400;
	
	private Timeline gameLoop;
	private Scene myScene;
	private Group myGroup;
	
	Layer layer;
	PhysicsEngine globalPhysics;
	Sprite playerSprite;
	HashMap<Sprite, Node> displayMap;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		Duration framePeriod = Duration.millis(1000 / (float) fps);
		KeyFrame frame = new KeyFrame(framePeriod, e -> handleKeyFrame());

		gameLoop = new Timeline();
		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		
		stg.setTitle("Physics Demo");
		myGroup = new Group();
		myScene = new Scene(myGroup, width, height, Color.WHITE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e));
		stg.setScene(myScene);

		initBackend();
		initAndDrawNodes();
		
		stg.show();
		gameLoop.play();
	}
	
	public void stop() {
		gameLoop.stop();
	}

	private void handleKeyFrame() {
		globalPhysics.update(layer);
		updateNodes();
	}
	
	/* update node positioning to reflect sprite positioning */
	public void updateNodes() {
		for(Sprite sprite : layer.getSprites()) {
			PhysicsObject sPhysics = sprite.getPhysicsObject();
			Node sNode = displayMap.get(sprite);
			sNode.setTranslateX(sPhysics.getX());
			sNode.setTranslateY(sPhysics.getY());
			System.out.printf("(%d, %d)\n", (int)sPhysics.getX(), (int)sPhysics.getY());
		}
	}
	private void handleKeyInput(KeyEvent e) {
		if(e.getCode() == KeyCode.SPACE) {
			playerSprite.getPhysicsObject().applyImpulse(new Vector(0, 50));
		}
	}
	
	public void initBackend() {
		/* create global physics engine */
		globalPhysics = new PhysicsEngine(0, 1/(double)fps);
		
		/* create player sprite */
		Sprite player = new Player();
		
		/* create player sprite physics object */
		CircleBody playerShape = new CircleBody(5);
		Material playerMaterial = new Material(0.3, 0.2);
		PhysicsObject playerPhysics = new PhysicsObject(globalPhysics, playerShape, playerMaterial, 200, (int)playerShape.getRadius());

		/* set player physics */
		player.setPhysicsObject(playerPhysics);
		
		/* init layer and add player sprite */
		layer = new Layer();
		layer.addSprite(player);
	}
	
	public void initAndDrawNodes() {
		displayMap = new HashMap<>();
		for(Sprite sprite : layer.getSprites()) {
			Node node = createNodeFromSprite(sprite);
			myGroup.getChildren().add(node);
			displayMap.put(sprite, node);
		}
	}
	
	/* create a node representation of a sprite */
	public Node createNodeFromSprite(Sprite sprite) {
		PhysicsObject sPhysics = sprite.getPhysicsObject();
		double radius = ((CircleBody)sPhysics.getShape()).getRadius();
		Node circle = new Circle(radius, Color.BLACK); // right now only circles are supported
		circle.setTranslateX(sPhysics.getX());
		circle.setTranslateY(sPhysics.getY());
		return circle;
	}

}
