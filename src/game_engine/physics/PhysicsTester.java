package game_engine.physics;

import game_engine.collision.Collision;
import game_engine.collision.CollisionEngine;
import game_engine.physics.RigidBody.RBodyType;
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

	private static Vector upForce = new Vector(0, 10);
	private static Vector rightForce = new Vector(10, 0);
	private static Vector leftForce = new Vector(-10, 0);
	private static Vector downForce = new Vector(0, 10);

	private Timeline gameLoop;
	private Scene myScene;
	private Group myGroup;
	
	List<Sprite> sprites;

	private List<KeyCode> keyPressList;
	private List<KeyCode> keyHoldList;
	private List<KeyCode> keyReleaseList;

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
		cEngine.checkCollisions();
		globalPhysics.update(sprites);
		updateNodes();
	}

	/* update node positioning to reflect sprite positioning */
	public void updateNodes() {
		for(Sprite sprite : sprites) {
			PhysicsObject sPhysics = sprite.getPhysicsObject();
			if(displayMap.containsKey(sprite)) {
				Node sNode = displayMap.get(sprite);
				setNodePosition(sNode, sPhysics.getPositionPixels());
			}
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
		sprites = new ArrayList<Sprite>();
		
		/* init display map */
		displayMap = new HashMap<>();
		
		/* create global physics engine */
		globalPhysics = new PhysicsEngine(0, 1 / (double) fps);

		/* create player sprite */
		String defaultState = "walking";
		String defaultImage = "/Resources/images/standingMario.png";
		int height = 50;
		int width = 50;
		RBodyType rType = RBodyType.CIRCLE;
		Material material = Material.SUPER_BALL;
		int startX = 400;
		int startY = 400;
		playerSprite = new Player(defaultState, defaultImage, height, width, rType, globalPhysics, material, startX, startY);
		
		/* add player to layer */
		sprites.add(playerSprite);

		/* create and add enemy sprites */
		Sprite e1 = createCircleEnemy(300, 700, 200, Material.METAL);
		Sprite e2 = createRectangleEnemy(500, 700, 50, 100, Material.BOUNCY_BALL);
		//createAndAddEnemy(500, 700, 30, Material.BOUNCY_BALL);
		//createAndAddEnemy(700, 200, 200, Material.BOUNCY_BALL);
		
		/* create collisions list */
		Collision c1 = new Collision(playerSprite, e1, null, null, true);
		Collision c2 = new Collision(playerSprite, e2, null, null, true);
		Collision c3 = new Collision(e1, e2, null, null, true);
		
		ArrayList<Collision> cList = new ArrayList<>();
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		
		myGroup.getChildren().add(playerSprite.getImageView());
		
		/* create collision engine */
		cEngine = new CollisionEngine(cList);
	}


	public Sprite createCircleEnemy(int x, int y, double radius, Material material) {
		String defaultState = "existing";
		String defaultImage = "/Resources/images/bouncy_ball.jpg";
		int height = (int) radius;
		int width = (int) radius;
		RBodyType rType = RBodyType.CIRCLE;
		int startX = x;
		int startY = y;
		
		Sprite enemySprite = new Player(defaultState, defaultImage, height, width, rType, globalPhysics, material, startX, startY);
		myGroup.getChildren().add(enemySprite.getImageView());
		sprites.add(enemySprite);
		
		return enemySprite;
	}
	
	public Sprite createRectangleEnemy(int x, int y, int height, int width, Material material) {
		String defaultState = "existing";
		String defaultImage = "/Resources/images/brick.jpg";
		RBodyType rType = RBodyType.RECTANGLE;
		int startX = x;
		int startY = y;
		
		Sprite enemySprite = new Player(defaultState, defaultImage, height, width, rType, globalPhysics, material, startX, startY);
		
		// debug
		Node rect = new Rectangle(width, height, Color.RED);
		displayMap.put(enemySprite, rect);
		setNodePosition(rect, enemySprite.getPhysicsObject().getPositionPixels());
		myGroup.getChildren().add(rect);
		
		myGroup.getChildren().add(enemySprite.getImageView());
		sprites.add(enemySprite);
		
		return enemySprite;
	}

	public void initAndDrawNodes() {
		displayMap = new HashMap<>();
		for(Sprite sprite : sprites) {
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
