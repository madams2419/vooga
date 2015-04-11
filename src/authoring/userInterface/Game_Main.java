 package authoring.userInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game_Main extends Application {

String backgroundName="randombackground";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    AuthoringWindow createScene = new AuthoringWindow();
    Scene myScene = createScene.GameCreateUI();
    primaryStage.setScene(myScene);
    primaryStage.setResizable(true);
//    primaryStage.setFullScreen(true);
    primaryStage.show();
  }
  
}
