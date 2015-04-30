package game_engine.control;

import game_engine.*;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;

import java.util.*;

import javafx.scene.input.*;

/**
 * This class servers to manage the different scenecontrol objects
 * including initialization and event handling
 * @author Yancheng Zeng
 */
public class SceneControlManager extends ControlManager{
	private int myActiveKeyControl;
	private int myActiveMouseControl;
	private List<KeyControl> myKeyControls;
	private List<MouseControl> myMouseControls;
	private SceneControlFactory myControlFactory; 
	private int myKeyControlCount;
	private int myMouseControlCount;
	
	
	public SceneControlManager(){
		myActiveKeyControl = ControlConstants.INITIAL_INDEX;		
		myActiveMouseControl = ControlConstants.INITIAL_INDEX;		
		myKeyControlCount = ControlConstants.INITIAL_INDEX;
		myMouseControlCount = ControlConstants.INITIAL_INDEX;
		myKeyControls = new ArrayList<>();
		myMouseControls = new ArrayList<>();
		myControlFactory = new SceneControlFactory(this);
	}
	
	/**
	 * Method addControl.
	 * @param control Control
	 */
	@Override
	public void addControl(Control control){
		myControlFactory.addControlType(control);
	}

	/**
	 * Method addKeyControl.
	 * @param newControl KeyControl
	 */
	public void addKeyControl(KeyControl newControl){
		System.out.println("add control");
		myKeyControls.add(newControl);
		myKeyControlCount++;
	}

	/**
	 * Method addMouseControl.
	 * @param newControl MouseControl
	 */
	public void addMouseControl(MouseControl newControl){
		myMouseControls.add(newControl);
		myMouseControlCount++;
	}
	
	/**
	 * Method setActiveControl.
	 * @param indexArray String[]
	 * @return IAction
	 */
	@Override
	public void switchControl(String... params){
		int keyIndex = Integer.parseInt(params[0]);
		int mouseIndex = ControlConstants.INITIAL_INDEX;
		if(validActiveIndex(mouseIndex, keyIndex)){			
			System.out.println(PrintMessage.INVALID_INDEX.getVal());
		} else {
				myActiveKeyControl = keyIndex;
				myActiveMouseControl = mouseIndex;
		}
	}
	
	/**
	 * Method validActiveIndex.
	 * @param mouseIndex int
	 * @param keyIndex int
	 * @return boolean
	 */
	private boolean validActiveIndex(int mouseIndex, int keyIndex){
		return (mouseIndex >= 0 && keyIndex >= 0 && myActiveKeyControl >= 0 && myActiveMouseControl >= 0 
				&& mouseIndex < myKeyControls.size() && keyIndex < myMouseControls.size());
	}
	
	/**
	 * Method handleEvent.
	 * @param obj Object
	 */
	@Override
	public void handleEvent(Object obj){
		//System.out.println("Currently the Active Control Index is "+myActiveKeyControl);
		myControlFactory.getControlType((InputEvent) obj).executeEvent(myControlFactory.getEventType((InputEvent) obj));
	}

	/**
	 * Method getActiveMouseControl.
	 * @return MouseControl
	 */
	public MouseControl getActiveMouseControl(){
		return myMouseControls.get(myActiveMouseControl);
	}
	
	/**
	 * Method getActiveKeyControl.
	 * @return KeyControl
	 */
	public KeyControl getActiveKeyControl(){
		return myKeyControls.get(myActiveKeyControl);
	}
	
	public int getKeyCount(){
		return myKeyControlCount;
	}
	
	public int getMouseCount(){
		return myMouseControlCount;
	}

}
