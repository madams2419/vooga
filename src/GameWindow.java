import java.security.acl.Group;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow extends Application {
private WindowCharacteristics myBackground;
String backgroundName="randombackground";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    myBackground = new WindowCharacteristics();
    Scene scene=myBackground.setImage(backgroundName);
    
    scene.widthProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            System.out.println("Width: " + newSceneWidth);
        }
    });
    scene.heightProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            System.out.println("Height: " + newSceneHeight);
        }
    });
    
    primaryStage.setScene(scene);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
  

}

