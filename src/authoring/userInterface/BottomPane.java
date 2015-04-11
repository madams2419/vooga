package authoring.userInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import authoring.XMLBuilder;
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

//	BottomPane() {
//		this(myScene,myContainer);
//	}

	BottomPane(Scene s, AuthoringWindow parent) {
		super(s,new HBox(), parent);
		myScene = s;
		myContainer.getStylesheets().add("styles/top_pane.css");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		for (int i = 0; i < values.size(); i++) {
			Map<String, Map<String, String>> m = values.get(i);
			System.out.println(m);
			for (String key : m.keySet()) {
				if (key.equals("Button")) {
					mButtonList.add(ButtonFactory.generateButton(m.get(key)));
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
					CenterPane.class.getMethod("addTab"), myParent.getMyCenterPane()));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Button c = new Button("Output xml");
		c.setOnAction(e -> {
			XMLBuilder.getInstance("game").addAllSprites(
					myParent.getMyCenterPane().getActiveTab().getSprites());
			XMLBuilder.getInstance("game").addAllEnvironment(
					myParent.getMyCenterPane().getActiveTab()
							.getEnvironment());
			XMLBuilder.getInstance("game").streamFile("lib/test.xml");
		});
		mButtonList.add(b);
		mButtonList.add(c);
		((HBox)myContainer).getChildren().addAll(mButtonList);
		return new Group();
	}

	public Iterator<Button> getButtons() {
		return mButtonList.iterator();
	}

	public Button[] getButtonArray() {
		return (Button[]) mButtonList.toArray();
	}

}
