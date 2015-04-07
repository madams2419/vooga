package authoring.interfaces;

import javafx.stage.Stage;


/**
 * Implemented a gameWindow with a changeable size and attached listeners on both the height and
 * width of the scene to render the a new XML
 * 
 */
public interface GameWindow {
    /**
     * Populates the stage with a scene
     * 
     * @param destination
     * @param tree
     */
    public void start (Stage primaryStage);

}
