import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * 
 */

/**
 * @author hojeanniechung
 *
 */
public class GameCreateUI {
	private Scene myScene;

	public Scene GameCreateUI(){
		Group root = new Group();
		BorderPane canvas = new BorderPane();
		myScene=new Scene(root,500,500,Color.ALICEBLUE);
		
		//Setting up borderPane
		canvas.setBottom(setupBottomPane(myScene.getWidth()));
		canvas.setTop(setupTopPane(myScene.getWidth()));
		canvas.setLeft(setupLeftPane());
		canvas.setRight(setupRightPane());
		canvas.setCenter(setupCenterPane());
		
		
		
		root.getChildren().add(canvas);
		
		// create a place to display the shapes and react to input


		return myScene;
	}
	
	private HBox setupBottomPane(double width) {
		HBox bottomPane = new HBox();
		return bottomPane;
	}

	private HBox setupTopPane(double width){
		HBox TopPane = new HBox();
		return TopPane;
	}

	private VBox setupRightPane() {
		VBox rightPane = new VBox();
		//rightPane.getChildren().addAll();
		return rightPane;
	}

	private VBox setupLeftPane() {
		VBox leftPane = new VBox();
		//leftPane.getChildren().addAll();
		return leftPane;
	}
	
	private Pane setupCenterPane(){
		Pane GameDisplay = new Pane();
		return GameDisplay;
	}

}
