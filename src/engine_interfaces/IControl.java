package engine_interfaces;

import java.util.function.Consumer;

import javafx.scene.input.InputEvent;

/**
 * This interface defines the public api of the control
 * class that we plan to implement in order to handle customizing
 * which actions occur when some sort of InputEvent happens.  The main
 * functionality of the class will revolve around a Map of InputEvents
 * to a list of Consumer<?>'s.
 * 
 * @author Brian Lavallee
 *
 */
public interface IControl {
    
    /**
     * The purpose of this method is to replace the existing methods mapped to the given input
     * with a new one.  This will merely utilize the put() method of Map which will replace the
     * existing value in the Map.
     * 
     * @param input	is the InputEvent that will trigger the new event.
     * 
     * @param functionality	is the method (from a sprite) that makes changes within the Sprite object.
     */
    public void setControl(InputEvent input, Consumer<?> functionality);
    
    /**
     * The purpose of this method is to add another method to the existing methods mapped to the
     * given input.  This will get the list of consumers currently in the map and then add to that list
     * with the list add method.
     * 
     * @param input 	is the Input event that will trigger the added event.
     * 
     * @param functionality 	is the method (from a sprite) that represents added the Sprite functionality
     * 				triggered by the input.
     */
    public void addControl(InputEvent input, Consumer<?> functionality);
    
    /**
     * removeControl() de-associates a certain result from its input.  This is achieved by removing
     * the InputEvent from the map.
     * 
     * @param input	is the input type that will be removed from the map and de-associated from its behavior.
     */
    public void removeControl(InputEvent input);

    /**
     * handleInput() takes an InputEvent from the GameLoop which is listening to the entire keyboard.  When it
     * receives an InputEvent, handleInput() checks the map.  If the input is in the map, the associated
     * consumers are executed resulting in the desired behavior happening.  If the map does not contain the
     * InputEvent, nothing happens.
     * 
     * @param input	is the Key/Mouse/Input Event from the GameLoop that has occurred and may be associated
     * 			and may be associated with an event.
     */
    public void handleInput(InputEvent input);
}