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
    GameCreateUI Create_Scene = new GameCreateUI();
    GameLoadUI Load_Scene=new GameLoadUI();
    Start_Screen SplashScreen = new Start_Screen();
    Scene scene=SplashScreen.Splash(primaryStage, Load_Scene.GameLoadUI(), Create_Scene.GameCreateUI());
    primaryStage.setScene(scene);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
  

}

