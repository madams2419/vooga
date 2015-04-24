package authoring.util;

import game_engine.objective.Objective;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.w3c.dom.Element;

import authoring.fileBuilders.XMLBuilder;
import authoring.panes.centerPane.CenterCanvas;
import authoring.userInterface.AuthoringWindow;

/***
 * Class which contains methods that are reusable across front-end development
 * 
 * @author Daniel Luker, Natalie
 *
 */
public class FrontEndUtils {

	public static File selectFile(String imageChooserTitle,
			String imageChooserDescription, String imageChooserExtensions[]) {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle(imageChooserTitle);
		imageChooser.getExtensionFilters().add(
				new ExtensionFilter(imageChooserDescription,
						imageChooserExtensions));
		return imageChooser.showOpenDialog(null);
	}

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

		// Adding start
		xml.addChildWithValue(level, "start", "0");

		List<CenterCanvas> allMaps = new ArrayList<>();
		for (List<CenterCanvas> l : parent.getCenterPane().getMaps())
			allMaps.addAll(l);

		int i = 0;
		for (CenterCanvas c : allMaps) {

			// Adding the properties of objective
			Element currentLevel = xml.add(level, "level_" + i++);

			Element objective = xml.add(currentLevel, "objective");

			for (Integer i1 : c.getObjectives().keySet()) {
				Element currentObjective = xml.add(objective,
						String.format("objective_%d", i1));

				Map<String, List<String>> obj = c.getObjectives().get(i1);

				xml.addChildWithValue(currentObjective, "prereqs",
						obj.get("prereqs").toString());

				Element onComplete = xml.add(currentObjective, "onComplete");
				Element behaviour = xml.add(onComplete, "behaviours");
				int i2 = 0;
				for (String s : obj.get("onComplete")) {
					Element currentBehaviour = xml.add(behaviour, "behaviour"
							+ i2++);
					xml.addChildWithValue(currentBehaviour, "targetType", "sprite");
					xml.addChildWithValue(currentBehaviour, "targetIndex", "0");
					String[] t = s.split(":");
					xml.addChildWithValue(currentBehaviour, "name", t[1]);
				}

				Element onFailure = xml.add(currentObjective, "onFailure");
				behaviour = xml.add(onFailure, "behaviours");
				i2 = 0;
				for (String s : obj.get("onFailure")) {
					Element currentBehaviour = xml.add(behaviour, "behaviour"
							+ i2++);
					xml.addChildWithValue(currentBehaviour, "targetType", "sprite");
					xml.addChildWithValue(currentBehaviour, "targetIndex", "0");
					String[] t = s.split(":");
					xml.addChildWithValue(currentBehaviour, "name", t[1]);
				}

			}

			// Adding sprites
			Element sprite = xml.add(currentLevel, "sprite");
			xml.addAllSprites(sprite, c.getSprites());

			// Adding physics
			xml.add(currentLevel, "physics");

			// Adding controls
			xml.add(currentLevel, "control");

			// Adding collision
			xml.add(currentLevel, "collision");

			// Streaming result
			xml.streamFile("output/test.xml");
		}
	}
}
