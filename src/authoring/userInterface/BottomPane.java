package authoring.userInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import authoring.XMLBuilder;
import authoring.util.FrontEndUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/***
 * 
 * @author Daniel Luker and Jeannie
 *
 */
public class BottomPane extends WindowPane {

	private List<Button> mButtonList = new ArrayList<>();
	public static final String XML_FILE_OUTPUT = "res/game.xml";

	// BottomPane() {
	// this(myScene,myContainer);
	// }

	BottomPane(Scene s, AuthoringWindow parent) {
		super(s, new HBox(), parent);
		System.out.printf("Instantiated %s%n", this.getClass().getName());
		myScene = s;
		myContainer.getStylesheets().add("styles/top_pane.css");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		for (int i = 0; i < values.size(); i++) {
			Map<String, Map<String, String>> m = values.get(i);
			for (String key : m.keySet()) {
				if (key.equals("Button")) {
					mButtonList.add(ButtonFactory.generateButton(
							myParent.getMyRightPane(), m.get(key)));
				}
				if (key.equals("Dropdown")) {
					DropdownFactory dFactory = new DropdownFactory();
					dFactory.generateDropdown(m.get(key));
				}
			}
		}
		Button b = new Button("+");
		try {
			b.setOnAction(new ClickHandler(
					CenterPane.class.getMethod("addTab"), myParent
					.getMyCenterPane()));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Button c = new Button("Output xml");
		c.setOnAction(e -> {
			FrontEndUtils.buildXMLFile(myParent, XML_FILE_OUTPUT);
		});
		mButtonList.add(b);
		mButtonList.add(c);
		((HBox) myContainer).getChildren().addAll(mButtonList);
		return null;
	}

	public Iterator<Button> getButtons() {
		return mButtonList.iterator();
	}

	public Button[] getButtonArray() {
		return (Button[]) mButtonList.toArray();
	}

}
