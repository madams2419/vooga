package authoring_interfaces;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;

public interface ButtonMaker {
    /**
     * Creates a button from its name and an EventHandler
     * 
     * @param name
     * @param actionOnClick
     * @return
     */
    Button makeButton(String name, EventHandler<Event> actionOnClick);
    
    /**
     * Creates a combobox from its name, components, and their EventHandlers
     * 
     * @param name
     * @param components
     * @param actionsOnClick
     * @return
     */
    ComboBox<String> makeComboBox(String name, String[] components, EventHandler<Event>[] actionsOnClick);

    /**
     * Creates a tab from its name and an EventHandler
     * 
     * @param name
     * @param actionOnClick
     * @return
     */
    Tab makeTab(String name, EventHandler<Event> actionOnClick);
}
