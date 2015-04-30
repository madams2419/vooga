package game_engine.control;

import java.util.Map;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;

/**
 * This class is the super class for control managers. It specifies the public APIs of
 * control managers.
 * @author Yancheng Zeng 
 */
public abstract class ControlManager implements IActor{
	private Map<String, IAction> myActions;
	/**
	 * Method addControl.
	 * @param control Control
	 */
	public void addControl(Control control){}

	/**
	 * Method handleEvent.
	 * @param obj Object
	 */
	public void handleEvent(Object obj){}

	@IActionAnnotation(description = "Changings the active control scheme", numParams = 1, paramDetails = "Control scheme id")
	private IAction setActiveControl = (params) -> {
		switchControl(params);
	};
	
	public abstract void switchControl(String... params);
	
	public IAction getAction(String name) {
		if (name.equals("setActiveControl")) {
			return setActiveControl;
		}
		return null;
	}
}
