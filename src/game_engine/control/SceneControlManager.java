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
	
	
	public SceneControlManager(){
		myActiveKeyControl = ControlConstants.INITIAL_INDEX;		
		myActiveMouseControl = ControlConstants.INITIAL_INDEX;		
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
	//  ((SceneControl) control).addControlFactory(myControlFactory);
		myControlFactory.addControlType(control);
	}

	/**
	 * Method addKeyControl.
	 * @param newControl KeyControl
	 */
	public void addKeyControl(KeyControl newControl){
		myKeyControls.add(newControl);
		myActiveKeyControl++;
	}

	/**
	 * Method addMouseControl.
	 * @param newControl MouseControl
	 */
	public void addMouseControl(MouseControl newControl){
		myMouseControls.add(newControl);
		myActiveMouseControl++;
	}
	
	/**
	 * Method setActiveControl.
	 * @param indexArray String[]
	 * @return IAction
	 */
	@Override
	public IAction setActiveControl(String... indexArray){
		int keyIndex = Integer.parseInt(indexArray[0]);
		int mouseIndex = Integer.parseInt(indexArray[1]);
		if(validActiveIndex(mouseIndex, keyIndex)){			
			System.out.println(PrintMessage.INVALID_INDEX.getVal());
			return null;
		} else {
			IAction activeControl = (params) -> {
				myActiveKeyControl = keyIndex;
				myActiveMouseControl = mouseIndex;
			};
			return activeControl;
		}
	}

	/**
	 * Method validActiveIndex.
	 * @param mouseIndex int
	 * @param keyIndex int
	 * @return boolean
	 */
	private boolean validActiveIndex(int mouseIndex, int keyIndex){
		return (mouseIndex >= 0 && keyIndex >= 0 && myActiveKeyControl >= 0 && myActiveMouseControl >= 0 && myActiveKeyControl >= 0
				&& mouseIndex < myKeyControls.size() && keyIndex < myMouseControls.size());
	}
	
	/**
	 * Method handleEvent.
	 * @param obj Object
	 */
	@Override
	public void handleEvent(Object obj){
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

}
