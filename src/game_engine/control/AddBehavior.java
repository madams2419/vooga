package game_engine.control;

import game_engine.*;

public class AddBehavior implements IAction{
	
	@Override
	public void execute(String... params) {
		ControlTester.addTrack();
		ControlTester.updateText();
	}

}
