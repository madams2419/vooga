package game_engine.physics;

import game_engine.Layer;
import game_engine.collision.Collision;
import game_engine.collision.CollisionEngine;
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
	private static int width = 800;
	private static int height = 800;

	private static Vector upImpulse = new Vector(0, 0.02);
	private static Vector rightImpulse = new Vector(0.01, 0);
	private static Vector leftImpulse = new Vector(-0.01, 0);
	private static Vector downImpulse = new Vector(0, -0.02);

	private static Vector upForce = new Vector(0, 0.1);
	private static Vector rightForce = new Vector(0.1, 0);
	private static Vector leftForce = new Vector(-0.1, 0);
	private static Vector downForce = new Vector(0, 0.1);

	private Timeline gameLoop;
	private Scene myScene;
	private Group myGroup;

	private List<KeyCode> keyPressList;
	private List<KeyCode> keyHoldList;
	private List<KeyCode> keyReleaseList;

	private Layer layer;
	private PhysicsEngine globalPhysics;
	private CollisionEngine cEngine;
	private Sprite playerSprite;
	private HashMap<Sprite, Node> displayMap;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		keyPressList = new ArrayList<>();
		keyHoldList = new ArrayList<>();
		keyReleaseList = new ArrayList<>();

		Duration framePeriod = Duration.millis(1000 / (float) fps);
		KeyFrame frame = new KeyFrame(framePeriod, e -> handleKeyFrame());

		gameLoop = new Timeline();
		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);

		stg.setTitle("Physics Demo");
		myGroup = new Group();
		myScene = new Scene(myGroup, width, height, Color.WHITE);
		myScene.setOnKeyPressed(e -> handleKeyPress(e));
		myScene.setOnKeyReleased(e -> handleKeyPress(e));
		stg.setScene(myScene);

		initBackend();
		//initAndDrawNodes();

		stg.show();
		gameLoop.play();
	}

	public void stop() {
		gameLoop.stop();
	}

	private void handleKeyFrame() {
		applyKeyPressBehavior();
		applyKeyReleaseBehavior();
		globalPhysics.update(layer.getSprites());
		updateNodes();
	}

	/* update node positioning to reflect sprite positioning */
	public void updateNodes() {
		for (Sprite sprite : layer.getSprites()) {
			PhysicsObject sPhysics = sprite.getPhysicsObject();
			Node sNode = displayMap.get(sprite);
			setNodePosition(sNode, sPhysics.getPositionPixels());
		}
	}

	private void handleKeyPress(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (!keyHoldList.contains(event.getCode())) {
				keyHoldList.add(event.getCode());
				keyPressList.add(event.getCode());
			}

		} else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
			keyHoldList.remove(event.getCode());
			keyReleaseList.add(event.getCode());
		}
	}

	private void applyKeyPressBehavior() {
		PhysicsObject sPhysics = playerSprite.getPhysicsObject();
		for (KeyCode keycode : keyPressList) {
			switch (keycode) {
			case UP:
				// sPhysics.applyImpulse(upImpulse);
				sPhysics.addForce(upForce);
				break;
			case DOWN:
				// sPhysics.applyImpulse(downImpulse);
				sPhysics.addForce(downForce);
				break;
			case RIGHT:
				// sPhysics.applyImpulse(rightImpulse);
				sPhysics.addForce(rightForce);
				break;
			case LEFT:
				// sPhysics.applyImpulse(leftImpulse);
				sPhysics.addForce(leftForce);
				break;
			default:
				break;
			}
		}
		keyPressList.clear();
	}

	private void applyKeyReleaseBehavior() {
		PhysicsObject sPhysics = playerSprite.getPhysicsObject();
		for (KeyCode keycode : keyReleaseList) {
			switch (keycode) {
			case UP:
				sPhysics.removeForce(upForce);
				break;
			case DOWN:
				sPhysics.removeForce(downForce);
				break;
			case RIGHT:
				sPhysics.removeForce(rightForce);
				break;
			case LEFT:
				sPhysics.removeForce(leftForce);
				break;
			default:
				break;
			}
		}
		keyReleaseList.clear();
	}

	public void initBackend() {
		/* init layer */
		layer = new Layer();

		/* create global physics engine */
		globalPhysics = new PhysicsEngine(0, 1 / (double) fps);

		/* create player sprite */
		playerSprite = new Player(null);

		/* set image behavior */
		playerSprite.addImage("walking", "/Resources/images/standingMario.png");
		playerSprite.setState("walking");
		playerSprite.getImageView().setFitHeight(50);
		playerSprite.getImageView().setFitWidth(50);
		myGroup.getChildren().add(playerSprite.getImageView());
		
		/* create player sprite physics object */
		Material playerMaterial = Material.SUPER_BALL;
		PhysicsObject playerPhysics = new PhysicsObject(playerSprite, globalPhysics, playerMaterial, 400, 400);
		playerSprite.setPhysicsObject(playerPhysics);

		/* add player to layer */
		layer.addSprite(playerSprite);

		/* create and add enemy sprites */
		Sprite e1 = createAndAddEnemy(300, 700, 50, Material.BOUNCY_BALL);
		//createAndAddEnemy(500, 700, 30, Material.BOUNCY_BALL);
		//createAndAddEnemy(700, 200, 200, Material.BOUNCY_BALL);
		
		/* create collisions list */
		Collision c1 = new Collision(playerSprite, e1, null, null, true, globalPhysics);
		
		ArrayList<Collision> cList = new ArrayList<>();
		cList.add(c1);
		
		/* create collision engine */
		cEngine = new CollisionEngine(cList);
	}

	public Sprite createAndAddEnemy(int x, int y, double radius, Material material) {
		
		/* create player sprite */
		Sprite enemySprite = new Enemy(null);

		/* set image behavior */
		enemySprite.addImage("existing", "/Resources/images/bouncy_ball.jpg");
		enemySprite.setState("existing");
		enemySprite.getImageView().setFitHeight(radius);
		enemySprite.getImageView().setFitWidth(radius);
		myGroup.getChildren().add(enemySprite.getImageView());
		
		/* create player sprite physics object */
		Material enemyMaterial = Material.BOUNCY_BALL;
		PhysicsObject enemyPhysics = new PhysicsObject(playerSprite, globalPhysics, enemyMaterial, x, y);
		playerSprite.setPhysicsObject(enemyPhysics);

		/* add player to layer */
		layer.addSprite(playerSprite);
		
		return enemySprite;
		
	}

	public void initAndDrawNodes() {
		displayMap = new HashMap<>();
		for (Sprite sprite : layer.getSprites()) {
			Node node = createNodeFromSprite(sprite);
			myGroup.getChildren().add(node);
			displayMap.put(sprite, node);
		}

	}

	/* create a node representation of a sprite */
	public Node createNodeFromSprite(Sprite sprite) {
		PhysicsObject sPhysics = sprite.getPhysicsObject();
		double radius = sPhysics.getRigidBody().getRadius();

		// just to make things prettier...
		Color sColor = Color.BLACK;
		String className = sprite.getClass().getName();
		if (className.contains("Player")) {
			sColor = Color.BLACK;
		} else if (className.contains("Enemy")) {
			sColor = Color.BURLYWOOD;
		}

		Node circle = new Circle(radius, sColor); // right now only circles are
													// supported
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
		System.out.printf("(%d, %d)\n", (int) sPhysics.getXPixels(),
				(int) sPhysics.getYPixels());
	}
}
