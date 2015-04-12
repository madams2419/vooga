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
public class BottomPane extends HBox {

	private static Scene myScene;
	private static BottomPane mInstance;

	private List<Button> mButtonList = new ArrayList<>();

	public static BottomPane getInstance() {
		return mInstance == null ? mInstance = new BottomPane() : mInstance;
	}

	BottomPane() {
		this(myScene);
		mInstance = this;
	}

	BottomPane(Scene s) {
		myScene = s;
		this.getStylesheets().add("styles/top_pane.css");
	}

	@SuppressWarnings("unchecked")
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
					CenterPane.class.getMethod("addTab"), CenterPane
							.getInstance(myScene)));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		this.getChildren().add(b);
		Button c = new Button("Output xml");
		c.setOnAction(e -> {
			XMLBuilder.getInstance("game").addAllSprites(
					CenterPane.getInstance(null).getActiveTab().getSprites());
			XMLBuilder.getInstance("game").addAllEnvironment(
					CenterPane.getInstance(null).getActiveTab()
							.getEnvironment());
			XMLBuilder.getInstance("game").streamFile("lib/test.xml");
		});
		mButtonList.add(c);
		this.getChildren().addAll(mButtonList);
		return new Group();
	}

	public Iterator<Button> getButtons() {
		return mButtonList.iterator();
	}

	public Button[] getButtonArray() {
		return (Button[]) mButtonList.toArray();
	}

}
