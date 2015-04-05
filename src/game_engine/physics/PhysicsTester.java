package game_engine.physics;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import game_engine.*;

public class PhysicsTester extends Application {

	Group myGroup;
	Scene myScene ;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Physics Demo");
		myGroup = new Group();
		myScene = new Scene(myGroup, 400, 400, Color.WHITE);

		//
		myGroup.getChildren().add(text);
		text.setFont(new Font(20));

		s.setScene(scene);
		keyManipulation();
		scene.setOnKeyPressed(e -> handleKeyInput(e));

		s.show();
	}

	public void addSprites() {
		Sprite

	}



}
