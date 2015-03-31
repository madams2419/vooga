<<<<<<< HEAD:src/formerdefault/XMLBuilder.java
package formerdefault;
=======
<<<<<<< HEAD
>>>>>>> dd4a5204e379c7d7c57b5c168742362f6c6a705f:src/XMLBuilder.java
import java.io.*;
import org.w3c.dom.Node;

/***
 * Interface for a class which will write the game files, after storing them in
 * a temporary location
 * 
 * @author daniel
=======
import java.io.File;

import javafx.scene.Node;

/***
 * This is an interface. It represents a class which will be used to write the
 * elements from the class into an xml file, to be used by the player.
 * 
 * @author Daniel Luker
>>>>>>> 3e3e2b71d2d9a29a1fb10ea8a704b72bbd2d36e6
 *
 */
interface XMLBuilder {

<<<<<<< HEAD
	/***
	 * Gets a node with the elements (children) of the element in the xml tree
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	abstract Node getElements(String name, String value);

	/***
	 * Adds a new node to the file
	 * 
	 * @param name
	 * @param value
	 */
	abstract void add(String name, String value);

	/***
	 * Updates a node
	 * 
	 * @param name
	 * @param value
	 */
	abstract void update(String name, String value);

	/***
	 * Removes a node from an xml file, along with all its associated children
	 * tags
	 * 
	 * @param name
	 * @param value
	 */
	abstract void remove(String name, String value);

	/***
	 * Writes out the file to the specific provided filename
	 * 
	 * @param filename
	 *            String url where we are saving the file
	 * @return Handler to the saved file.
	 */
	abstract File write(String filename);

=======
       /***
        * Returns all of the elements (children) of a given key.
        * 
        * @param name
        * @param value
        * @return
        */
       abstract Node getElements(String name, String value);

       /***
        * Puts a value into the file
        * 
        * @param name
        * @param value
        */
       abstract void add(String name, String value);

       /***
        * Updates a value
        * 
        * @param name
        * @param value
        */
       abstract void update(String name, String value);

       /***
        * Removes a file from the map
        * 
        * @param name
        * @param value
        */
       abstract void remove(String name, String value);

       /***
        * Writes out the result of the generated xml file
        * 
        * @return a handle to the file object generated.
        */
       abstract File write();
>>>>>>> 3e3e2b71d2d9a29a1fb10ea8a704b72bbd2d36e6
}
