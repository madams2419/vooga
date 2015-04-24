package game_engine.control;

import java.util.*;

public class ControlFactory {
	private static final Map<String, Class> myControlMap = new HashMap<>();
	
	public static void registerControl(String controlName, Class controlType){
		myControlMap.put(controlName, controlType);
	}
	
	public static String getControlType(){
		return null;
	}
}
