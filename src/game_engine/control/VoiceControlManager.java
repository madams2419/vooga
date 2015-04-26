package game_engine.control;

import java.util.*;

/**
 * This class manages the voicecontrol objects and extends the 
 * controlmanager class
 * @author Yancheng Zeng
 */
public class VoiceControlManager extends ControlManager{
	private List<VoiceControl> myVoiceControls;
	private int myActiveVoiceControl;
	
	public VoiceControlManager(){
		myVoiceControls = new ArrayList<>();
		myActiveVoiceControl = ControlConstants.INITIAL_INDEX;
	}
	
	/**
	 * Method addControl.
	 * @param control Control
	 */
	@Override
	public void addControl(Control control){
		myVoiceControls.add((VoiceControl) control);
		myActiveVoiceControl++;
	}
	
	/**
	 * Method handleEvent.
	 * @param obj Object
	 */
	@Override
	public void handleEvent(Object obj){
		myVoiceControls.get(myActiveVoiceControl).executeEvent();
	}
}
