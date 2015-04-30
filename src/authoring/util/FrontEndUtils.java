package authoring.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import authoring.dataEditors.Sprite;
import authoring.fileBuilders.Level_XML;
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

    private static final String EQUALS = "=";
    private static final String SPLITBY = ", ";
    private static final String SPACE = " ";
    private static final String DEFAULT_FIRST_LEVEL = "0";
    private static final String FIRST_LEVEL = "first_level";
    private static final String LEVEL = "level";
    private static final String ROOT_ELEMENT = "game";
    private static final char OPEN_BRACKET = '{';

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
		if (s.charAt(0) == OPEN_BRACKET)
			s = s.substring(1, s.length() - 1);
		Arrays.asList(s.split(SPLITBY)).forEach(
				entry -> result.put(entry.split(EQUALS)[0], entry.split(EQUALS)[1]));
		return result;
	}

	public static void setKeyActions(Parent n) {
//		System.out.printf("Setting key actions on %s%n", n.getClass().getName());
		n.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOn();
//			System.out.println("detected key press");
		});
		n.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOff();
//			System.out.println("detected key release");
		});
	}

	public static void setKeyActions(TabPane t) {
		setKeyActions((Parent) t);
	}

	public static HBox makeToggleGroup(String label1, String label2) {
		HBox hbox = new HBox(10);
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton button1 = new RadioButton(label1);
		button1.setToggleGroup(toggleGroup);
		button1.setSelected(true);
		RadioButton button2 = new RadioButton(label2);
		button2.setToggleGroup(toggleGroup);
		hbox.getChildren().addAll(button1, button2);
		// toggleGroup.selectedToggleProperty().addListener(); edit this line to
		// add listener
		return hbox;
	}

	public static String getSpritesIDSorted(Sprite... sprites){
		StringBuilder s = new StringBuilder();
		List<Integer> elements = Arrays.asList(sprites).stream().map(sprite -> {
			return sprite.getID();
		}).collect(Collectors.toList());
		Collections.sort(elements);
		elements.forEach(num -> s.append(num + SPACE));
		return s.toString();
	}
	
	public static void buildXMLFile(AuthoringWindow parent, String filename) {
		// Adding the root element
		XMLBuilder xml = XMLBuilder.getInstance(ROOT_ELEMENT);
		
		parent.getGlobalProperties().forEach(
				(label, value) -> xml.addChildWithValue(xml.getRoot(), label,
						value));

		// Adding the level tag
		Element level = xml.addToRoot(LEVEL);


		List<CenterCanvas> allMaps = new ArrayList<>();
		for (List<CenterCanvas> l : parent.getCenterPane().getMaps())
			allMaps.addAll(l);

		int i = 0;
		// Adding start
		xml.addChildWithValue(level, FIRST_LEVEL, DEFAULT_FIRST_LEVEL);
		for (CenterCanvas c : allMaps) {

			Level_XML currentLevel = new Level_XML(c);
			currentLevel.writeToXML(level, i++, xml);

		}

		// Streaming result
		xml.streamFile(filename);

	}
}
