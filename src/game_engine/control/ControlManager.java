package game_engine.control;

import game_engine.*;

import java.util.*;

import javafx.scene.input.*;

public class ControlManager {
	private int myActiveControl;
	private List<KeyControl> myKeyControls;

	public ControlManager(){
		myActiveControl = -1;		
		myKeyControls = new ArrayList<>();
	}

	public void addControl(Map<KeyCode, Map<IBehavior, String[]>> keyPressMap, Map<KeyCode, Map<IBehavior, String[]>> keyReleaseMap){
		KeyControl newControl = new KeyControl(keyPressMap, keyReleaseMap);
		myKeyControls.add(newControl);
		myActiveControl++;
	}

	public void setActiveControl(int index){
		if(myActiveControl < 0 || index > myKeyControls.size()){
			System.out.println("Invalid Active Control Index");
		} else {
			myActiveControl = index;
		}
	}

	public void handleKeyEvent(KeyCode keycode, boolean pressed){
		if(myActiveControl < 0){
			System.out.println("No Control has been added yet!");
		} else{
			myKeyControls.get(myActiveControl).executeKeyEvent(keycode, pressed);
		}
	}

}
