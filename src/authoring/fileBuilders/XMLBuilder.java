package authoring.fileBuilders;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import authoring.dataEditors.Sprite;

/***
 * This is a class that will contain information in a tree structure, and encode
 * that information in an xml file
 *
 * @author Daniel Luker
 *
 */
public class XMLBuilder {

	// ====== Instance variables ===============================================

	/***
	 * This is the root of the tree structure. The API methods from below will
	 * allow the user to navigate up/down. To add elements for the appropriate
	 * child.
	 */
	private Element root;

	/***
	 * This represents the document instance, which will be used to eventually
	 * stream out the file.
	 */
	private Document mDocument;

	private static XMLBuilder mInstance;

	// ====== Constructors =====================================================

	public static XMLBuilder getInstance(String rootElement) {
		if (mInstance == null)
			mInstance = new XMLBuilder(rootElement);
		return mInstance;
	}

	private XMLBuilder(String rootElement, String... attributes_values) {
		this(rootElement, arrayToMap(attributes_values));
	}

	private XMLBuilder(String rootElement, Map<String, String> attributes_values) {
		root = createElement(rootElement, attributes_values);
	}

	// ====== Methods ==========================================================

	/***
	 * Method to retrieve specified child node from the parent node.
	 * 
	 * @param parent
	 * @param child_tagname
	 * @return Child node of parent, with name child_tagname
	 */
	public Element getChild(Element parent, String child_tagname) {
		for (int i = 0; i < parent.getChildNodes().getLength(); i++)
			if (((Element) parent.getChildNodes().item(i)).getAttribute("name")
					.equals(child_tagname))
				return (Element) parent.getChildNodes().item(i);
		return null;
	}

	/***
	 * Method which will collect all the information stored in the parent node
	 * into the specified file
	 */
	public void streamFile(String filename) {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(root);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Creates an element for the xml file with the specified attributes. The
	 * resulting element will look like the following:
	 * {@code <tagname attr1="val1",...,attrn="valn"/>}
	 * 
	 * @param tagname
	 *            The name for the new element
	 * @param attributes_values
	 *            varargs element specifying the desired attributes. The input
	 *            must be as follows: {@code attribute1, value1, attribute2,
	 *            value2,...}
	 * @return
	 */
	public Element createElement(String tagname,
			Map<String, String> attributes_values) {
		Element newElement = null;
		try {
			mDocument = mDocument == null ? DocumentBuilderFactory
					.newInstance().newDocumentBuilder().newDocument()
					: mDocument;
			if (tagname.contains(" ")) {
				tagname = tagname.trim();
				StringBuilder s = new StringBuilder();
				Arrays.asList(tagname.split("\\s")).forEach(s1 -> s.append(s1));
				tagname = s.toString();
			}
			newElement = mDocument.createElement(tagname);
			if (attributes_values != null && !attributes_values.isEmpty())
				addAttributes(newElement, attributes_values);
		} catch (Exception e) {
			// TODO define error reaction
			e.printStackTrace();
		}
		return newElement;
	}

	public Element createElement(String tagname) {
		return createElement(tagname, null);
	}

	/***
	 * Helper method to turn a String array to a Map, in order to allow
	 * different input
	 * 
	 * @param attributes_values
	 * @return
	 */
	private static Map<String, String> arrayToMap(String... attributes_values) {
		Map<String, String> result = new HashMap<>();
		for (int i = 0; i < attributes_values.length; i += 2)
			result.put(attributes_values[i], attributes_values[i + 1]);
		return result;
	}

	/***
	 * Adds the specified attributes to the element
	 * 
	 * @param element
	 *            to which the attributes are added
	 * @param attributes_values
	 *            A map connecting the attributes to their desrired values
	 */
	private void addAttributes(Element element,
			Map<String, String> attributes_values) {
		attributes_values.forEach((e1, e2) -> element.setAttribute(e1, e2));
	}

	/***
	 * General method for adding a child with the specified tag-name and
	 * attributes
	 * 
	 * @param parent
	 *            To which the new Element will be appended as child
	 * @param tagname
	 *            Which will define the child node
	 * @param attributes
	 *            Map specifying desired attribute names and values
	 * @return Instance of Element which is the newly created tag
	 */
	public Element add(Element parent, String tagname,
			Map<String, String> attributes) {
		Element ret = this.createElement(tagname, attributes);
		parent.appendChild(ret);
		return ret;
	}

	/***
	 * Simplified version of add, which adds directly to the root of the
	 * document
	 */
	public Element addToRoot(Element newElement) {
		root.appendChild(newElement);
		return newElement;
	}

	/***
	 * Simplified method for adding a new element to the root
	 * 
	 * @param tagname
	 *            of the new element to be created
	 * @param attributes
	 *            for the new element
	 * @return the newly created element
	 */
	public Element addToRoot(String tagname, Map<String, String> attributes) {
		return add(root, tagname, attributes);
	}

	/***
	 * Simplified method to add a new element with the specified tagname as a
	 * child of root, with no attributes
	 * 
	 * @param tagname
	 *            for the new element
	 * @return the newly created element
	 */
	public Element addToRoot(String tagname) {
		return addToRoot(tagname, null);
	}

	/***
	 * Method for adding a child to the parent with no attributes
	 * 
	 * @param parent
	 *            To which the new Element will be appended as child
	 * @param tagname
	 *            Which will define the child node
	 * @return Instance of Element which is the newly created tag
	 */
	public Element add(Element parent, String tagname) {
		assert (parent != null);
		return add(parent, tagname, null);
	}

	/***
	 * Method for adding a child with a specified tag and the specified text
	 * field, with no attributes.
	 * 
	 * @param parent
	 *            of the new element to be created
	 * @param property
	 * @param textContent
	 *            value of the property
	 * @return new element which was created
	 */
	public Element addChildWithValue(Element parent, String property,
			String textContent) {
		Element newElement = add(parent, property);
		newElement.setTextContent(textContent);
		return newElement;
	}

	/***
	 * Method to add a map linking nametags and values as a child of parent. So,
	 * to add something like the following: {@code 	<childtagname>
	 * 				<prop1>val1</prop1>
	 * 				...
	 * 			</childtagname>}
	 * 
	 * @param parent
	 * @param child_tagname
	 * @param properties
	 */
	public void addChildProperties(Element parent, String child_tagname,
			Map<String, String> properties) {
		Element e = add(parent, child_tagname);
		properties.forEach((s1, s2) -> add(e, "" + s1).setTextContent(s2));
	}

	public Element getRoot() {
		return root;
	}

	public void addAllSprites(Element parent, List<Sprite> sprites) {
		for(int i = 0; i < sprites.size(); i++)
			addSprite(parent, sprites.get(i), i);
	}

	private void addSprite(Element parent, Sprite sprite, int index) {
		Element s = add(parent, String.format("sprite_%d",index));
//		s.setAttribute("name", sprite.getName());
		add(s, "type");
		add(s, "inital_state");
		addChildWithValue(s, "width", Double.toString(sprite.getFitWidth()));
		addChildWithValue(s, "height", Double.toString(sprite.getFitHeight()));
		addChildWithValue(s, "animation", "null");
//		sprite.getCharacteristics().forEach(
//				(key, value) -> addChildWithValue(s, key, value));
		addChildWithValue(s, "physics", "null");
	}

	public void addAllEnvironment(Element parent, Collection<Map<String, String>> environments) {
		Element mapElement = add(parent, "map_environment");
		assert (mapElement != null);
		environments.forEach(map -> {
			map.forEach((s1, s2) -> {
				Element newel = add(mapElement, (String) s1);
				newel.setTextContent((String) s2);
			});
		});
	}
}