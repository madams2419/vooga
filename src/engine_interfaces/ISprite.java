package engine_interfaces;

import java.util.function.Consumer;

/**
 * The Sprite hierarchy represents all of the different objects that can be displayed during a game.
 * This includes the background, players, enemies, platforms, walls, power-ups, etcetera.  Each sprite
 * contains its own Physics object which determines how the sprite will move based on an individual set of
 * constants.  The behavior of sprites are actually determined by a series of Consumer<?>'s which make
 * it easy for Controls and Collisions to variably track which methods to call.  Sprites contain a
 * map of String names to Consumer<?>'s.  This way outside classes can quickly and easily get out the
 * functionality they want.
 * 
 * @author Brian Lavallee
 *
 */
public interface ISprite {

    /**
     * Updates the sprite by calling update() of Physics.  The properties of the sprite are
     * binded to those in Physics.
     */
    public void update();
    
    /**
     * getConsumer(String name) accesses the map of names to consumers and gets the requested Consumer.
     * This is the mechanism for other classes (Collisions and Controls) to get the behaviors of the sprite.
     * 
     * @param name	is the name associated with the desired Consumer.  This value will be specified by a properties
     * 			file for the authoring environment and then given to Collisions and Controls in this format.
     * 
     * @return	the behavior of the sprite represented as a Consumer<?>.
     */
    public Consumer<?> getConsumer(String name);
}