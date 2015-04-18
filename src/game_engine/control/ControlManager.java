package game_engine.control;

import game_engine.*;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import java.util.*;
import javafx.scene.input.*;

public class ControlManager {
	private int myActiveControl;
	private List<KeyControl> myKeyControls;

	public ControlManager(){
		myActiveControl = -1;		
		myKeyControls = new ArrayList<>();
	}

	public void addControl(Map<KeyCode, IBehavior> keyPressedMap, Map<KeyCode, IBehavior> keyReleasedMap, Map<KeyCode, IBehavior> keyHeldMap){
		KeyControl newControl = new KeyControl(keyPressedMap, keyReleasedMap, keyHeldMap);
		myKeyControls.add(newControl);
		myActiveControl++;
	}
	
	public void addControl(KeyControl newControl){
		myKeyControls.add(newControl);
		myActiveControl++;
	}

	public IAction setActiveControl(String... indexArray){
		int index = Integer.parseInt(indexArray[0]);
		//needs to replace this error checking with something else
		if(myActiveControl < 0 || index > myKeyControls.size()){
			System.out.println("Invalid Active Control Index");
			return null;
		} else {
			IAction activeControl = (params) -> {
				int activeIndex = Integer.parseInt(params[0]);
				myActiveControl = activeIndex;
			};
			return activeControl;
		}
	}

	public void handleKeyEvent(KeyCode keycode, boolean pressed){
		if(myActiveControl < 0){
			System.out.println("No Control has been added yet!");
		} else{
			System.out.println("myActiveC is "+myActiveControl);
			myKeyControls.get(myActiveControl).executeKeyEvent(keycode, pressed);
		}
	}

}
