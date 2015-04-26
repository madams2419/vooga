package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;


/**
 * Abstract class for scrolling dependent on some mouse event. When some mouse event occurs, it will
 * focus on some point determined by that mouse event.
 * 
 * @author Tony
 *
 */
public abstract class MouseTracker extends AbstractTracker {
    private Group myGroup;
    private ObjectProperty<EventHandler<? super MouseEvent>> myEventHandlerProperty;
    private EventHandler<? super MouseEvent> myEventHandler;

    public MouseTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
        myGroup = scroller.getGroup();
    }

    /**
     * Returns the property of the mouse event we want to track.
     * 
     * @param group Target of the mouse event.
     * @return
     */
    protected abstract ObjectProperty<EventHandler<? super MouseEvent>> getHandlerProperty (Group group);

    /**
     * Defines what happens when some mouse event occurs. Typically some point will be focused on.
     * 
     * @param event MouseEvent that occurs
     * @param group Target of the mouse event
     */
    protected abstract void handle (MouseEvent event, Group group);

    /**
     * Starts listening to mouse events by adjusting event handler.
     */
    @Override
    protected void start () {
        myEventHandlerProperty = getHandlerProperty(myGroup);
        myEventHandler = myEventHandlerProperty.get();
        getHandlerProperty(myGroup).set(e -> {
            if (myEventHandler != null) {
                myEventHandler.handle(e);
            }
            handle(e, myGroup);
        });
    }

    /**
     * Resets the event handler to what it was before.
     */
    @Override
    protected void stop () {
        myEventHandlerProperty.set(myEventHandler);
    }

}
