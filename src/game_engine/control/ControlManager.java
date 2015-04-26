package game_engine.control;

import game_engine.behaviors.IAction;

/**
 * This class is the super class for control managers. It specifies the public APIs of
 * control managers.
 * @author Yancheng Zeng 
 */
public abstract class ControlManager {
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

	/**
	 * Method setActiveControl.
	 * @param indexArray String[]
	 * @return IAction
	 */
	public IAction setActiveControl(String... indexArray){
		return new IAction(){
			@Override
			public void execute(String... params) {
				System.out.println(PrintMessage.SWITCH_CONTROL.getVal());
			}
		};
	}
}
