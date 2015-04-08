package authoring.interfaces;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public interface Controller {
    /**
     * Creates the object player and renders an image on top of the object.  
     */
    void createPlayer();
    /**
     * Creates the physics that will be applied on an objects. The method createPhysics also includes the possible methods of gravities and underwater applications.
     */
    void createPhysics();
    /**
     * Listeners from collisions that determine the event occuring after collisions.
     */
    void createInteractions();
    /**
     * Creates maps depending on the graphs.
     */
    void createMaps();
    /**
     * Set the physics created in createPhysics.
     */
    void setPhysics();
    /**
     * EventHandler for to change the InformationPane to display whatever we need the button to display
     * @param b
     * @param e
     */
    void setButtonClicked(Button b, EventHandler<Event> e);

}
