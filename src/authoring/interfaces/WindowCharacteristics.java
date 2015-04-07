package authoring.interfaces;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * WindowCharacteristics set the characteristics of the scene that is going to set the text.
 *
 */
public interface WindowCharacteristics {
    /**
     * 
     * @param s
     * @return
     */
    public Scene setImage (String s);

    /**
     * 
     * @param e
     */
    public void handleKeyInput (KeyEvent e);

}
