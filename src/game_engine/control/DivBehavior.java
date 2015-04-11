package game_engine.control;

import game_engine.IBehavior;

public class DivBehavior implements IBehavior{

	@Override
	public void execute(String... params) {
		ControlTester.divTrack();
		ControlTester.updateText();		
	}

}
