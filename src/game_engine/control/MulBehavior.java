package game_engine.control;

import game_engine.IBehavior;

public class MulBehavior implements IBehavior{

	@Override
	public void execute(String... params) {
		ControlTester.mulTrack();
		ControlTester.updateText();		
	}

}
