package engine;

import java.util.function.Consumer;

/**
 * The SpriteBehavior class stores a consumer which is triggered during either a
 * collision, on a user input, or when the frame updates.  SpriteBehaviors take the place
 * of methods in the Sprites to simplify switching active code during collisions or user input.
 * 
 * @author Brian Lavallee
 * @since 30 March 2015
 */
public class SpriteBehavior
{
    private Consumer<Object> behavior;
    
    /**
     * Functional constructor, allows Sprites to define their own behavior using lambda functions.
     * 
     * @param handler	is the lambda function defined in a Sprite class which hold the desired behavior of the Sprite.
     */
    public SpriteBehavior(Consumer<Object> behavior)
    {
	this.behavior = behavior;
    }
    
    /**
     * Default constructor
     */
    public SpriteBehavior()
    {
	behavior = (lambda) -> {};
    }
    
    /**
     * Executes the behavior.  Null used since the object does not matter.  The Consumer
     * is only used as a data structure to store the behavior as a lambda function, hence the relatively
     * simple wrapper class.
     */
    public void run()
    {
	behavior.accept(null);
    }
}