package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

/***
 * 
 * @author daniel and Jeannie
 *
 */
public class TopPane extends WindowPane {
	static ArrayList<Node> mButtonList = new ArrayList<Node>();
	public static Group root = new Group();

	TopPane(Scene s, AuthoringWindow w) {
		super(s, new HBox(), w);
		// this.getStylesheets().add("styles/top_pane.css");
		System.out.printf("Instantiated %s%n", this.getClass().getName());
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		return null;
	}

}