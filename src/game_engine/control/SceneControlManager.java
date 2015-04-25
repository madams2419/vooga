package game_engine.control;

import game_engine.*;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import java.util.*;
import javafx.scene.input.*;

public class SceneControlManager {
	private int myActiveKeyControl;
	private int myActiveMouseControl;
	private List<KeyControl> myKeyControls;
	private List<KeyControl> myMouseControls;
	private SceneControlFactory myControlFactory; 
	
	
	public SceneControlManager(){
		myActiveKeyControl = -1;		
		myActiveKeyControl = -1;		
		myKeyControls = new ArrayList<>();
		myMouseControls = new ArrayList<>();
		myControlFactory = new SceneControlFactory(this);
	}

	public void addKeyControl(Map<KeyCode, IBehavior> keyPressedMap, Map<KeyCode, IBehavior> keyReleasedMap, Map<KeyCode, IBehavior> keyHeldMap){
		KeyControl newControl = new KeyControl(keyPressedMap, keyReleasedMap, keyHeldMap);
		myKeyControls.add(newControl);
		myActiveKeyControl++;
	}

	public void addKeyControl(KeyControl newControl){
		myKeyControls.add(newControl);
		myActiveKeyControl++;
	}

	public IAction setActiveControl(String... indexArray){
		int keyIndex = Integer.parseInt(indexArray[0]);
		int mouseIndex = Integer.parseInt(indexArray[1]);
		if(validActiveIndex(mouseIndex, keyIndex)){			
			System.out.println("Invalid Active Control Index");
			return null;
		} else {
			IAction activeControl = (params) -> {
				myActiveKeyControl = keyIndex;
				myActiveMouseControl = mouseIndex;
			};
			return activeControl;
		}
	}

	private boolean validActiveIndex(int mouseIndex, int keyIndex){
		return (mouseIndex >= 0 && keyIndex >= 0 && myActiveKeyControl >= 0 && myActiveMouseControl >= 0 && myActiveKeyControl >= 0
				&& mouseIndex < myKeyControls.size() && keyIndex < myMouseControls.size());
	}
	
	public void handleEvent(InputEvent event){
		myControlFactory.getControlType(myActiveMouseControl, myActiveKeyControl, event).executeEvent(myControlFactory.getEventType(event));
	}

	public void handleKeyEvent(KeyCode keycode, boolean pressed){
		if(myActiveKeyControl < 0){
			System.out.println("No Control has been added yet!");
		} else{
			System.out.println("myActiveC is "+myActiveKeyControl);
			myKeyControls.get(myActiveKeyControl).executeKeyEvent(keycode, pressed);
		}
	}
	
	public SceneControl getActiveMouseControl(int index){
		return myMouseControls.get(index);
	}
	
	public SceneControl getActiveKeyControl(int index){
		return myKeyControls.get(index);
	}

}
