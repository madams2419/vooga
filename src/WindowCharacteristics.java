import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;



public class WindowCharacteristics {
	Group background = new Group();
	Circle myBackground;

	public Scene setImage(String s) {
		/*Create an ArrayList of background*/
		//myBackground=new ImageView(new Image(getClass().getResourceAsStream(String.format("images/%s", s)))); 
		myBackground=new Circle(40,40,50);
		background.getChildren().add(myBackground);

		// create a place to display the shapes and react to input
		Scene myScene=new Scene(background,500,500);
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
		myScene.setOnKeyPressed(e -> handleKeyInput(e));
		return myScene;
	}

	public void handleKeyInput(KeyEvent e){

	}
}
