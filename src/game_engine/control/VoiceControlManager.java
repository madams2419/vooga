package game_engine.control;

import java.util.*;

public class VoiceControlManager extends ControlManager{
	private List<VoiceControl> myVoiceControls;
	private int myActiveVoiceControl;
	
	public VoiceControlManager(){
		myVoiceControls = new ArrayList<>();
		myActiveVoiceControl = -1;
	}
	
	@Override
	public void addControl(Control control){
		myVoiceControls.add((VoiceControl) control);
		myActiveVoiceControl++;
	}
	
	@Override
	public void handleEvent(Object obj){
		myVoiceControls.get(myActiveVoiceControl).executeEvent();
	}
}
