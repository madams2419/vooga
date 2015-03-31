package formerdefault;
import java.io.*;
import org.w3c.dom.Node;

/***
 * Interface for a class which will write the game files, after storing them in
 * a temporary location
 * 
 * @author daniel
 *
 */
interface XMLBuilder {

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

}
