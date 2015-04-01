import java.io.File;

import javafx.scene.Node;

/***
 * This is an interface. It represents a class which will be used to write the
 * elements from the class into an xml file, to be used by the player.
 * 
 * @author Daniel Luker
 *
 */
interface XMLBuilder {

<<<<<<< HEAD
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
}
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
}
>>>>>>> 33cf55872e74c89a6ed277e55ac1b2b9af4cd20d
