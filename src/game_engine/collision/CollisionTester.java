package game_engine.collision;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CollisionTester extends Application {
	private Scene scene;
	private ImageView spriteA;
	private ImageView spriteB;
	private Group root = new Group();
	private PixelPerfectDetector ppc;

	public CollisionTester() {

	}

	@Override
	public void start(Stage stage) throws Exception {
//		System.out.println("hello");
		initialize();
		stage.setScene(scene);
		stage.show();

	}

	private void initialize() {
		// TODO Auto-generated method stub
		scene = new Scene(root, 800, 600);
		scene.setOnKeyPressed(event -> keyPress(event));

		Image image = new Image("./Resources/images/block.png");
		spriteA = new ImageView(image);
		spriteA.setTranslateX(400);
		spriteA.setTranslateY(300);

		Image image2 = new Image("./resources/images/smallerLuigi.png");
		spriteB = new ImageView(image2);
		spriteB.setTranslateX(200);
		spriteB.setTranslateY(0);
		root.getChildren().addAll(spriteA, spriteB);
		ppc = new PixelPerfectDetector(spriteA, spriteB);

	}

	private void keyPress(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getCode() == KeyCode.UP) {
			spriteB.setTranslateY(spriteB.getTranslateY()-5);
		} else if (event.getCode() == KeyCode.DOWN) {
			spriteB.setTranslateY(spriteB.getTranslateY()+5);
		} else if (event.getCode() == KeyCode.RIGHT) {
			spriteB.setTranslateX(spriteB.getTranslateX() + 5);
		} else if (event.getCode() == KeyCode.LEFT) {
			spriteB.setTranslateX(spriteB.getTranslateX() -5);
		};
		System.out.println(ppc.isColliding());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
