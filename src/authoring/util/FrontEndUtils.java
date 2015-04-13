package authoring.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
import authoring.userInterface.CenterPane.CenterCanvas;

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
		XMLBuilder xml = XMLBuilder.getInstance("game");
		Element mapList = xml.addToRoot("list_of_maps");
		Iterator<CenterCanvas> iter = parent.getMyCenterPane().getMaps();
		while (iter.hasNext()) {
			CenterCanvas c = iter.next();
			Element map = xml.add(mapList, "map");
			xml.addAllSprites(map, c.getSprites());
			xml.addAllEnvironment(map, c.getEnvironment());
		}
		xml.streamFile("lib/test.xml");
	}
}
