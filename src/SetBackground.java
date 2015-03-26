import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;



public class SetBackground {
Group background = new Group();
Circle myBackground;

public Scene setImage(String s) {
	/*Create an ArrayList of background*/
	//myBackground=new ImageView(new Image(getClass().getResourceAsStream(String.format("images/%s", s)))); 
	myBackground=new Circle(40,40,50);
	background.getChildren().add(myBackground);

	// create a place to display the shapes and react to input
	Scene myScene=new Scene(background,500,500);
	myScene.setOnKeyPressed(e -> handleKeyInput(e));
	return myScene;
}

public void handleKeyInput(KeyEvent e){
	
}
}
