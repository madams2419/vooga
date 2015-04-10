package authoring;

import java.io.File;
import java.util.Collection;
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
		if(mInstance==null)
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
	public void streamFile(String filename, Element root) {
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
		System.out.println("streamed file");
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
			newElement = mDocument.createElement(tagname);
			if (attributes_values != null && !attributes_values.isEmpty())
				addAttributes(newElement, attributes_values);
		} catch (DOMException | ParserConfigurationException e) {
			// TODO define error reaction
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
	public void addChildWithValue(Element element, String property,
			String textContent) {
		add(element, property).setTextContent(textContent);
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

	public void addAll(Collection<Sprite> sprites) {
		sprites.forEach(sprite -> addSprite(sprite));
	}
	
	private void addSprite(Sprite sprite) {
		Element s = add(root, "sprite");
		s.setAttribute("name", sprite.getName());
		sprite.getCharacteristics().forEach((key, value) -> {
			addChildWithValue(s,key,value);
		});
	}
	
	// ====== Testing Main Method ==============================================

	public static void main(String[] args) {

		/***
		 * This will generate a sample xml file in src/sample.xml
		 */
		XMLBuilder b = new XMLBuilder("sample", "name", "sample", "author",
				"daniel");

		/*
		 * Generate the map with attributes. This is meant to be called by
		 * either an engine or a gui.
		 */
		Map<String, String> mAttributes = new HashMap<>();
		mAttributes.put("name", "first");
		Element el1 = b.addToRoot("element", mAttributes);

		// Adding propertyN with valueN as child of el1
		b.add(el1, "property1").setTextContent("value1");
		b.add(el1, "property2").setTextContent("value2");
		b.add(el1, "property3").setTextContent("value3");
		mAttributes.clear();

		// We want to add a subtree with tagname position to the root
		Element el2 = b.add(el1, "position");
		b.add(el2, "X").setTextContent("xpos");
		b.add(el2, "Y").setTextContent("ypos");

		// Adding another subtree
		Element el3 = b.add(el1, "key-actions");
		b.add(el3, "prop1").setTextContent("1");
		b.add(el3, "prop2").setTextContent("2");
		b.add(el3, "prop3").setTextContent("3");
		b.add(el3, "prop4").setTextContent("4");

		// Once we are done adding elements to the tree structure, we stream it
		// to generate the xml file
		b.streamFile("swap/game.xml", b.root);
	}

}