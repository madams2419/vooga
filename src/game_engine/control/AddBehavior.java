package game_engine.control;

import game_engine.*;

public class AddBehavior implements Behavior{
	
	@Override
	public void execute() {
		ControlTester.addTrack();
	}

}
