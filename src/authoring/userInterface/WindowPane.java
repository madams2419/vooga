package authoring.userInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;

public abstract class WindowPane {

	private List<Control> myControlsList;
	public Region myContainer;
	protected Scene myScene;
	protected AuthoringWindow myParent;

	protected WindowPane(Scene scene, Region container, AuthoringWindow parent) {
		myScene = scene;
		myContainer = container;
		myParent = parent;
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
	
	public AuthoringWindow getParent() {
		return myParent;
	}
	
	public abstract Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values);

}
