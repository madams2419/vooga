package authoring.testers;

import authoring.userInterface.AuthoringWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuthoringGUITester extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//BottomPane bp=new BottomPane();
		AuthoringWindow w = new AuthoringWindow();
		Scene s = w.GameCreateUI();
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
