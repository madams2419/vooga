package authoring_UserInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class Start_Screen {
	private Scene myScene;
	
	public Scene Splash(Stage s, Scene Game_Load, Scene Game_Create){
		Group Root = new Group();
		myScene=new Scene(Root,500,500,Color.CADETBLUE);
		
		myScene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				System.out.println("Width: " + newSceneWidth);
			}
		});
		myScene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				System.out.println("Height: " + newSceneHeight);
			}
		});
		
		myScene.setOnKeyPressed(e->handleKeyInput(s,Game_Create,Game_Load,e));
		return myScene;
	}

	public void handleKeyInput(Stage s, Scene game_Create, Scene game_Load, javafx.scene.input.KeyEvent e){
		KeyCode keyCode=e.getCode();
		if(keyCode==KeyCode.ENTER){
			s.setScene(game_Create);
		}
		if(keyCode==KeyCode.SPACE){
			s.setScene(game_Load);
		}
	}
}
