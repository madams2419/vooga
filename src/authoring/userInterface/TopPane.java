package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		for (int i = 0; i < values.size(); i++) {
			Map<String, Map<String, String>> m = values.get(i);
			for (String key : m.keySet()) {
				if (key.equals("Button")) {
					Button b;
					mButtonList
							.add(b = ButtonFactory.generateButton(m.get(key)));
					root.getChildren().add(b);
				}
				if (key.equals("Dropdown")) {
					DropdownFactory dFactory = new DropdownFactory();
					mButtonList.add(dFactory.generateDropdown(m.get(key)));
				}
			}
		}
		root.getChildren().addAll(mButtonList);
		((HBox) myContainer).getChildren().addAll(mButtonList);
		return root;
	}

}
