package game_engine.control;

import game_engine.behaviors.IAction;

public class DivBehavior implements IAction{

	@Override
	public void execute(String... params) {
		ControlTester.divTrack();
		ControlTester.updateText();		
	}

}
