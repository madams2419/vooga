package game_engine.control;

import game_engine.*;

public class SubtractBehavior implements IAction{

	@Override
	public void execute(String... params) {
		ControlTester.subTrack();
		ControlTester.updateText();
	}

}
