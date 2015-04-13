package game_engine.control;

import game_engine.*;
import game_engine.behaviors.IAction;

public class SubtractBehavior implements IAction{

	@Override

	public void execute(String... params) {
		ControlTester.subTrack();
		ControlTester.updateText();
	}

}
