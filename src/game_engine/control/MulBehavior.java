package game_engine.control;

import game_engine.IAction;

public class MulBehavior implements IAction{

	@Override
	public void execute(String... params) {
		ControlTester.mulTrack();
		ControlTester.updateText();		
	}

}
