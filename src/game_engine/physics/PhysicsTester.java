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
	private static int width = 800;
	private static int height = 800;
	
	private static Vector upImpulse = new Vector(0, 0.02); 
	private static Vector rightImpulse = new Vector(0.005, 0);
	private static Vector leftImpulse = new Vector(-0.005, 0);
	private static Vector downImpulse = new Vector(0, -0.02);
	
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
			playerSprite.setState("jump");
			break;
		case DOWN :
			sPhysics.applyImpulse(downImpulse);
			playerSprite.setState("standing");
			break;
		case RIGHT :
			sPhysics.applyImpulse(rightImpulse);
			if(playerSprite.getImageView().getScaleX()>0)
	                        playerSprite.getImageView().setScaleX(-1);
			
			if(!playerSprite.getState().equals("standing")){
                            playerSprite.setState("standing");
                             }
                             else if(!playerSprite.getState().equals("walking")){
                             playerSprite.setState("walking");
                             }
			break;
		case LEFT :
			sPhysics.applyImpulse(leftImpulse);
			System.out.println(playerSprite.getImageView().getScaleX());
			if(playerSprite.getImageView().getScaleX()<0)
			playerSprite.getImageView().setScaleX(1);
			
			if(!playerSprite.getState().equals("standing")){
			       playerSprite.setState("standing");
			        }
			        else if(!playerSprite.getState().equals("walking")){
			        playerSprite.setState("walking");
			        }
			 
			break; 
		}
	}
	
	public void initBackend() {
		/* init layer */
		layer = new Layer();
		
		/* create global physics engine */
		globalPhysics = new PhysicsEngine(200, 1/(double)fps);
		
		/* create player sprite physics object */
		int playerRadius = 15;
		Shape playerShape = new CircleBody(playerRadius);
		Material playerMaterial = new Material(0.3, 0.8);
		PhysicsObject playerPhysics = new PhysicsObject(globalPhysics, playerShape, playerMaterial, 400, 400);

		/* create player sprite */
                playerSprite = new Player(playerPhysics);
                
		/* set player physics */
		playerSprite.setPhysicsObject(playerPhysics);
		
		playerSprite.addImage("standing", "/Resources/images/standingMario.png");
		playerSprite.addImage("jump", "/Resources/images/jumpingMario.png");
		playerSprite.addImage("walking", "/Resources/images/walkingMario.png");
                playerSprite.setState("standing");
                playerSprite.getImageView().setFitHeight(50);
                playerSprite.getImageView().setFitWidth(50);
                myGroup.getChildren().add(playerSprite.getImageView());
		/* add player to layer */
		layer.addSprite(playerSprite);
		
		/* create and add enemy sprites */
		createAndAddEnemy(300, 700, 50, 0.4, 0.3);
		createAndAddEnemy(500, 700, 30, 0.3, 0.2);
		createAndAddEnemy(700, 200, 200, 0.3, 0.2);
	}
	
	public void createAndAddEnemy(int x, int y, double radius, double density, double restitution) {
		
		/* create enemy sprite physics object */
		Shape enemyShape = new CircleBody(radius);
		Material enemyMaterial = new Material(density, restitution);
		PhysicsObject enemyPhysics = new PhysicsObject(globalPhysics, enemyShape, enemyMaterial, x, y);
		
		/* create enemy sprite */
                Sprite enemySprite = new Enemy(enemyPhysics);
		/* set player physics */
		enemySprite.setPhysicsObject(enemyPhysics);
		
		/* add to layer */
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
