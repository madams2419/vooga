package game_engine.behaviors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * IActors are things that can have a IAction and therefore need getter methods
 * so that the game builder can retrieve and distribute the desired actions to
 * the correct triggers.
 * 
 * @author Tony Qiao
 * @author Brian Lavallee
 * @since 20 April 2015
 */
public interface IActor {
    /**
     * Returns the desired IAction from the actor's set of possible IAction's.
     * 
     * @param name
     *        name is the String representation of the IAction. This is
     *        generally specified by a preceding annotation.
     * 
     * @return The method returns the IAction associated with the given name.
     */
    public IAction getAction (String name);

    public default Map<String, IAction> buildActionMap () {
        try {
            Map<String, IAction> actions = new HashMap<>();
            for (Field field : getClass().getDeclaredFields()) {
                if (field.getType() == IAction.class) {
                    field.setAccessible(true);
                    String name = field.getName();
                    IAction action = (IAction) field.get(this);
                    actions.put(name, action);
                    field.setAccessible(false);
                }
            }
            return actions;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
