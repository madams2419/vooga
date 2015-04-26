package game_engine.control;

import javafx.scene.input.InputEvent;

public abstract class SceneControl implements Control{
	
	public void executeEvent(InputEvent e){System.out.println("Get called");}
	
}
