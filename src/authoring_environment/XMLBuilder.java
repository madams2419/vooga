package authoring_environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * This is a class that will contain information about the currently created
 * game, and generate the appropriate files
 * 
 * @author Daniel Luker
 *
 */
class XMLBuilder {

	// ====== Instance variables ===============================================

	private Element root;
	private Document mDocument;

	// ====== Constructors =====================================================

	XMLBuilder(String rootElement, String... attributes_values) {
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
	Element getChild(Element parent, String child_tagname) {
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
	void streamFile(String filename) {
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
	Element createElement(String tagname, Map<String, String> attributes_values) {
		Element newElement = null;
		try {
			mDocument = mDocument == null ? DocumentBuilderFactory
					.newInstance().newDocumentBuilder().newDocument()
					: mDocument;
			newElement = mDocument.createElement(tagname);
			if (attributes_values != null && !attributes_values.isEmpty())
				addAttributes(newElement, attributes_values);
		} catch (DOMException | ParserConfigurationException e) {
			// TODO define error reaction
		}
		return newElement;
	}

	Element createElement(String tagname) {
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
	protected Element add(Element parent, String tagname,
			Map<String, String> attributes) {
		Element ret = this.createElement(tagname, attributes);
		parent.appendChild(ret);
		return ret;
	}

	protected void addToRoot(Element newElement) {
		root.appendChild(newElement);
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
	protected Element add(Element parent, String tagname) {
		return add(parent, tagname, null);
	}

	/***
	 * Method for adding a child with a specified tag and the specified text
	 * field, with no attributes.
	 * 
	 * @param element
	 * @param property
	 * @param textContent
	 */
	protected void addChildWithProperty(Element element, String property,
			String textContent) {
		add(element, property).setTextContent(textContent);
	}
	
	/***
	 * Method to add a map linking nametags and values as a child of parent. So, to add something like the following: 
	 * {@code 	<childtagname>
	 * 				<prop1>val1</prop1>
	 * 				...
	 * 			</childtagname>}
	 * @param parent
	 * @param child_tagname
	 * @param properties
	 */
	protected void addChildProperties(Element parent, String child_tagname,
			Map<String, String> properties) {
		Element e = add(parent, child_tagname);
		properties.forEach((s1, s2) -> add(e, ""+s1).setTextContent(s2));
	}

	public Element getRoot() {
		return root;
	}

	// ====== Testing Main Method ==============================================

	public static void main(String[] args) {
		XMLBuilder b = new XMLBuilder("game", "name", "Super Mario");

		Map<String, String> mAttributes = new HashMap<>();
		mAttributes.put("name", "mario");
		mAttributes.put("gravity", "down");
		Element mario = b.add(b.getRoot(), "sprite", mAttributes);

		b.add(mario, "speed", null).setTextContent("1.0");

		mAttributes.clear();
		Element el = b.add(mario, "position", mAttributes);

		b.add(el, "X").setTextContent("10.0");
		b.add(el, "Y").setTextContent("10.0");

		el = b.add(mario, "key-actions", null);

		b.add(el, "UP").setTextContent("jump");
		b.add(el, "DOWN").setTextContent("crouch");
		b.add(el, "LEFT").setTextContent("moveBack");
		b.add(el, "RIGHT").setTextContent("moveForward");

		b.streamFile("swap/game.xml");
	}

}