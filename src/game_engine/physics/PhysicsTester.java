package game_engine.physics;

import game_engine.Layer;
import game_engine.sprite.Enemy;
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

	private static int fps = 60;
	private static int width = 400;
	private static int height = 400;
	
	private static Vector upImpulse = new Vector(0, 0.1); 
	private static Vector rightImpulse = new Vector(0.1, 0);
	private static Vector leftImpulse = new Vector(-0.1, 0);
	
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
			setNodePosition(sNode, sPhysics.getPositionPixels());
		}
	}
	private void handleKeyInput(KeyEvent event) {
		PhysicsObject sPhysics = playerSprite.getPhysicsObject();
		
		switch(event.getCode()) {
		case UP :
			sPhysics.applyImpulse(upImpulse);
			break;
		case RIGHT :
			sPhysics.applyImpulse(rightImpulse);
			break;
		case LEFT :
			sPhysics.applyImpulse(leftImpulse);
			break;
		default:
			break; 
		}
	}
	
	public void initBackend() {
		/* create global physics engine */
		globalPhysics = new PhysicsEngine(0, 1/(double)fps);
		
		/* create player sprite */
		playerSprite = new Player();
		
		/* create player sprite physics object */
		int playerRadius = 15;
		Shape playerShape = new CircleBody(playerRadius);
		Material playerMaterial = new Material(0.3, 0.5);
		PhysicsObject playerPhysics = new PhysicsObject(globalPhysics, playerShape, playerMaterial, 200, 200);

		/* set player physics */
		playerSprite.setPhysicsObject(playerPhysics);
		
		/* create enemy sprite */
		Sprite enemySprite = new Enemy();
		
		/* create player sprite physics object */
		int enemyRadius = 30;
		Shape enemyShape = new CircleBody(enemyRadius);
		Material enemyMaterial = new Material(0.3, 0.2);
		PhysicsObject enemyPhysics = new PhysicsObject(globalPhysics, enemyShape, enemyMaterial, 50, 200);
		
		/* set player physics */
		enemySprite.setPhysicsObject(enemyPhysics);
		
		/* init layer and add sprites */
		layer = new Layer();
		layer.addSprite(playerSprite);
		layer.addSprite(enemySprite);
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
		double radius = sPhysics.getShape().getRadiusPixels();
		
		// just to make things prettier...
		Color sColor = Color.BLACK;
		String className = sprite.getClass().getName();
		if(className.contains("Player")) {
			sColor = Color.BLACK;
		} else if(className.contains("Enemy")) {
			sColor = Color.BURLYWOOD;
		}
		
		Node circle = new Circle(radius, sColor); // right now only circles are supported
		setNodePosition(circle, sPhysics.getPositionPixels());
		return circle;
	}
	
	/* set node position applying physics space to javaFX coordinate transform */
	private void setNodePosition(Node node, Vector positionPixels) {
		node.setTranslateX(positionPixels.getX());
		node.setTranslateY(height - positionPixels.getY());
	}
	
	/* debug function to print player sprite's location */
	private void printLoc() {
		PhysicsObject sPhysics = playerSprite.getPhysicsObject();
		System.out.printf("(%d, %d)\n", (int)sPhysics.getXPixels(), (int)sPhysics.getYPixels());
	}
}
