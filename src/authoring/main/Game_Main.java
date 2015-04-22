package authoring.main;

import authoring.userInterface.AuthoringWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game_Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		AuthoringWindow createScene = new AuthoringWindow();
		Scene myScene = createScene.GameCreateUI(new Scene(new VBox(), 1200,
				500));
		primaryStage.setScene(myScene);
		primaryStage.setResizable(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

}
