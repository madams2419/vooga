package authoring_UserInterface;

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
//    GameLoadUI loadScene=new GameLoadUI();
    Start_Screen splashScreen = new Start_Screen();
    Scene scene= splashScreen.Splash(primaryStage, createScene.GameCreateUI(), null);
    
    Scene myScene = createScene.GameCreateUI();
    primaryStage.setScene(myScene);
    
    
    primaryStage.setResizable(true);
//    primaryStage.setFullScreen(true);
    primaryStage.show();
  }
  

}

