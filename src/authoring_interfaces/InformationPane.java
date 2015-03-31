package authoring_interfaces;

import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;

public interface InformationPane {
    /**
     * Changes our information pane to display whatever is defined in our event handler
     * @param info
     */
    void changeInformation(String info);

    /**
     * Create the map based on what the InformationPane should display when a button 
     * is pressed or an item is selected
     * 
     * @return
     */
    Map<String, EventHandler<Event>> createInformationMap();
}
