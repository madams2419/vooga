package authoring.userInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class WindowPane {

	private List<Pane> myFields;
	private List<Control> myControlsList;
	private boolean instantiated = false;
	protected Region myContainer;
	protected Scene myScene;
	protected AuthoringWindow myParent;

	WindowPane(Scene scene, Region container, AuthoringWindow parent) {
		myScene = scene;
		myContainer = container;
		myParent = parent;
	}

	WindowPane() {
		// this(myScene, myContainer);
	}

	Region getContainer() {
		return myContainer;
	}

	Iterator<Control> getControls() {
		return myControlsList.iterator();
	}

	Control[] getControlsArray() {
		return (Control[]) myControlsList.toArray();
	}
	
	public abstract Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values);

}
