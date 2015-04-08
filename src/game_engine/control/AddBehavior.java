package game_engine.control;

import game_engine.*;

public class AddBehavior implements IBehavior{
	
	@Override
	public void execute(double[] params) {
		ControlTester.addTrack();
		ControlTester.updateText();
	}

}
