package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeftPane extends WindowPane {

	public static ArrayList<Button> mButtonList = new ArrayList<Button>();
	public static Group root = new Group();

	LeftPane(Scene scene, AuthoringWindow myParent) {
		super(scene, new VBox(), myParent);
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		for (int i = 0; i < values.size(); i++) {
			Map<String, Map<String, String>> m = values.get(i);
			// System.out.println(m);
			for (String key : m.keySet()) {
				if (key.equals("Button")) {
					mButtonList.add(ButtonFactory.generateButton(m.get(key)));
				}
				if (key.equals("Dropdown")) {
					// DropdownFactory dFactory=new DropdownFactory();
					// dFactory.generateDropdown(m.get(key));
				}
			}
		}
		return root;
	}

}
