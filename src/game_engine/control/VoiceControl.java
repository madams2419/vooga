package game_engine.control;

import game_engine.behaviors.IBehavior;

import java.util.*;

public class VoiceControl implements Control{
	private Map<String, IBehavior> mySpeechMap;
	
	public VoiceControl(Map<String, IBehavior> speechMap){
		mySpeechMap = speechMap;
	}
	
	
}
