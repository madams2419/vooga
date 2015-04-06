package authoring.util;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/***
 * Class which contains methods that are reusable across front-end development
 * 
 * @author Daniel Luker
 *
 */
public class FrontEndUtils {

	public static Button makeButton(java.util.Map.Entry<String, EventHandler<Event>> entry) {
		Button mButton = new Button(entry.getKey());
		mButton.setOnMouseReleased(entry.getValue());
		return mButton;
	}
}
