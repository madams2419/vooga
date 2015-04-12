package game_engine.control;

import game_engine.IAction;

public class DivBehavior implements IAction{

	@Override
	public void execute(String... params) {
		ControlTester.divTrack();
		ControlTester.updateText();		
	}

}
