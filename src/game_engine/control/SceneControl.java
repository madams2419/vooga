package game_engine.control;

import javafx.scene.input.InputEvent;

/**
 * This class is the super class for control inputs from the javafx scene
 * and specifies the API
 */
public abstract class SceneControl implements Control{
	
	/**
	 * Method executeEvent.
	 * @param e InputEvent
	 */
	public void executeEvent(InputEvent e){System.out.println("Get called");}
	
}
