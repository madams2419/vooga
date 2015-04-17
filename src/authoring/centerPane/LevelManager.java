package authoring.centerPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import authoring.userInterface.AuthoringWindow;

/**
 * Singleton class that keeps track of the levels and maps.
 * 
 * @author Mengchao & Daniel Luker
 *
 */
class LevelManager {

	private Map<Tab, List<CenterCanvas>> myLevels;
	private Scene myScene;
	private AuthoringWindow myParent;

	LevelManager(Scene scene, AuthoringWindow parent) {
		myScene = scene;
		myParent = parent;
		myLevels = new HashMap<Tab, List<CenterCanvas>>();
	}

	Map<Tab, List<CenterCanvas>> getLevels() {
		return myLevels;
	}

	CenterCanvas addMap(Tab level, int map) {
		CenterCanvas c = new CenterCanvas(myScene, myParent);
		myLevels.get(level).add(c);
		return c;
	}

	void deleteLevel(Tab level) {
		 myLevels.remove(level);
	}

	void deleteMap(Tab level, int map) {
		 myLevels.get(level).remove(map);
	}

	void addLevel(Tab container) {
		myLevels.put(container, new ArrayList<>());
	}
}
