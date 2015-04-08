package authoring.userInterface;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * 
 */

/**
 * @author hojeanniechung
 *
 */
public class GameLoadUI {
	private Scene myScene;
	
	public Scene GameLoadUI(){
		Group root = new Group();
		myScene=new Scene(root,500,500,Color.CORAL);
		return myScene;
	}
	
}
