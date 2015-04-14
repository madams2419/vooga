package authoring.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import authoring.userInterface.AuthoringWindow;

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
	
	public static void setKeyActions(Node n) {
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
}
