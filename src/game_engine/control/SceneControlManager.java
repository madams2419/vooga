package game_engine.control;

import game_engine.*;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import java.util.*;
import javafx.scene.input.*;

public class SceneControlManager extends ControlManager{
	private int myActiveKeyControl;
	private int myActiveMouseControl;
	private List<KeyControl> myKeyControls;
	private List<MouseControl> myMouseControls;
	private SceneControlFactory myControlFactory; 
	
	
	public SceneControlManager(){
		myActiveKeyControl = -1;		
		myActiveKeyControl = -1;		
		myKeyControls = new ArrayList<>();
		myMouseControls = new ArrayList<>();
		myControlFactory = new SceneControlFactory(this);
	}
	
	@Override
	public void addControl(Control control){
		myControlFactory.addControlType(control);
	}

	public void addKeyControl(KeyControl newControl){
		myKeyControls.add(newControl);
		myActiveKeyControl++;
	}

	public void addMouseControl(MouseControl newControl){
		myMouseControls.add(newControl);
		myActiveMouseControl++;
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
		myControlFactory.getControlType(event).executeEvent(myControlFactory.getEventType(event));
	}

	public MouseControl getActiveMouseControl(){
		return myMouseControls.get(myActiveMouseControl);
	}
	
	public KeyControl getActiveKeyControl(){
		return myKeyControls.get(myActiveKeyControl);
	}

}
