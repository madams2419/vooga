package authoring.util;

import game_engine.objective.Objective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import org.w3c.dom.Element;

import authoring.XMLBuilder;
import authoring.userInterface.AuthoringWindow;
import authoring.centerPane.CenterCanvas;

/***
 * Class which contains methods that are reusable across front-end development
 * 
 * @author Daniel Luker
 *
 */
public class FrontEndUtils {

	public static Button makeButton(
			java.util.Map.Entry<String, EventHandler<Event>> entry) {
		Button mButton = new Button(entry.getKey());
		mButton.setOnMouseReleased(entry.getValue());
		return mButton;
	}

	public static Map<String, String> stringToMap(String s) {
		Map<String, String> result = new HashMap<>();
		if (s.charAt(0) == '{')
			s = s.substring(1, s.length() - 1);
		Arrays.asList(s.split(", ")).forEach(
				entry -> result.put(entry.split("=")[0], entry.split("=")[1]));
		return result;
	}

	public static void setKeyActions(Parent n) {
		System.out
				.printf("Setting key actions on %s%n", n.getClass().getName());
		n.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOn();
			System.out.println("detected key press");
		});
		n.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOff();
			System.out.println("detected key release");
		});
	}

	public static void setKeyActions(TabPane t) {
		setKeyActions((Parent) t);
	}

	public static HBox makeToggleGroup() {
		HBox hbox = new HBox(10);
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton button1 = new RadioButton("Sprite");
		button1.setToggleGroup(toggleGroup);
		button1.setSelected(true);
		RadioButton button2 = new RadioButton("Hit Box");
		button2.setToggleGroup(toggleGroup);
		hbox.getChildren().addAll(button1, button2);
		// toggleGroup.selectedToggleProperty().addListener(); edit this line to
		// add listener
		return hbox;
	}

	public static void buildXMLFile(AuthoringWindow parent, String filename) {
		// Adding the root element
		XMLBuilder xml = XMLBuilder.getInstance("game");

		// Adding title to root
		xml.addChildWithValue(xml.getRoot(), "title", "Simple_Game");

		// Adding the level tag
		Element level = xml.addToRoot("level");

		// Adding the properties of objective
		Element objective = xml.add(level, "objective");

		List<Objective> objectives = new ArrayList<>();
		Objective test = new Objective();
		Objective test1 = new Objective();
		objectives.add(test);
		objectives.add(test1);
		int a = 0;
		for (Objective o : objectives)
			xml.add(objective, String.format("objective_%d", a++));
		// more stuff here... perhaps modify xmlbuilder to have an addObjective
		// method

		// Adding sprites
		Element sprite = xml.add(level, "sprite");
		Iterator<List<CenterCanvas>> iter = parent.getMyCenterPane().getMaps();
		while (iter.hasNext()) {
			List<CenterCanvas> maps = iter.next();
			maps.forEach(c -> xml.addAllSprites(sprite, c.getSprites()));
			System.out.println("Outputting map");
		}
		// Adding physics
		xml.add(level, "physics");

		// Adding controls
		xml.add(level, "control");

		// Adding collision
		xml.add(level, "collision");

		// Streaming result
		xml.streamFile("output/test.xml");
	}
}
