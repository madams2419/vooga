package game_engine.control;

import game_engine.behaviors.IAction;

public abstract class ControlManager {
	public void addControl(Control control){}

	public void handleEvent(Object obj){}

	public IAction setActiveControl(String... indexArray){
		return new IAction(){
			@Override
			public void execute(String... params) {
				System.out.println("Switch out controls");
			}
		};
	}
}
