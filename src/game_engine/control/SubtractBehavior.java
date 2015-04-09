package game_engine.control;

import game_engine.*;

public class SubtractBehavior implements Behavior{

	@Override
	public void execute() {
		ControlTester.subTrack();
		ControlTester.updateText();
	}

}
