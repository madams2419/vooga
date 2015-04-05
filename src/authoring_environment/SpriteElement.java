package authoring_environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;


/***
 * Class that contains information about the sprites for eventually generating xml files
 * 
 * **not finished**
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class SpriteElement {
    final static String ROOT_TAGNAME = "sprites";
    Map<String, SpriteElement> myElements;
    Map<String, String> myAttributes;
    String myValue;

    /*
     * attributeType = "id"
     * attributeValue = "4"
     */
    SpriteElement (List<String> attributeValues, List<String> attributeTypes) {
        this();
        for (int i = 0; i < attributeValues.size(); i++) {
            myAttributes.put(attributeValues.get(i), attributeTypes.get(i));
        }
    }

    /*
     * value = 5.0
     * (where SpriteElement represents xSpeed)
     */
    SpriteElement (String value) {
        this();
        myValue = value;
    }

    public SpriteElement () {
        myElements = new HashMap<>();
        myAttributes = new HashMap<>();
    }

    /**
     * Adds an attribute to the element at a particular path, creating any non-existing elements
     * along the way.
     * 
     * For example:
     * 
     * path = {"platform"}
     * attributeName = "id"
     * attributeValue = "0"
     * 
     * result in XML file:
     * 
     * <platform id="0">
     * </platform>
     * 
     * @param path
     * @param attributeName
     * @param attributeValue
     */
    public void addAttributeAtPath (List<String> path,
                                    String attributeName,
                                    String attributeValue) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.addAttribute(attributeName, attributeValue);
    }

    private void addAttribute (String attributeName, String attributeValue) {
        myAttributes.put(attributeName, attributeValue);
    }

    /**
     * Adds a value to a particular path, creating any non-existing elements along the way.
     * 
     * For example:
     * 
     * path = {"mario", "location", "x"}
     * value = "5"
     * 
     * result in XML file:
     * <mario>
     *   <location>
     *     <x>5</x>
     *   </location>
     * </mario>
     * 
     * 
     * @param path
     * @param value
     */
    public void addElementAtPath (List<String> path, String value) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.setValue(value);
    }

    private void addElement (String elementName, SpriteElement element) {
        myElements.put(elementName, element);
    }

    private void addElement (String elementName, String value) {
        myElements.put(elementName, new SpriteElement(value));
    }

    private SpriteElement findElement (List<String> path) {
        SpriteElement element = this;
        for (int i = 0; i < path.size(); i++) {
            SpriteElement childElement = element.getElement(path.get(i));
            if (childElement == null) {
                childElement = new SpriteElement();
                element.addElement(path.get(i), childElement);
            }
            element = childElement;
        }
        return element;
    }

    void setValue (String value) {
        myValue = value;
    }

    String getValue (List<String> path) {
        SpriteElement sprite = findElement(path);
        return sprite.getValue();
    }

    String getValue () {
        return myValue;
    }

    private Map<String, SpriteElement> getElements () {
        return myElements;
    }

    private SpriteElement getElement (String elementName) {
        return myElements.get(elementName);
    }

    Map<String, String> getAttributes () {
        return myAttributes;
    }

    public void writeToXMLFile () {
        XMLBuilder builder = new XMLBuilder();
        Element root = builder.createElement(ROOT_TAGNAME, myAttributes);
        addChildren(builder, root, this);
        builder.streamFile("swap/SpriteElementTesting.xml", root);
    }

    private void addChildren (XMLBuilder builder, Element element, SpriteElement sprite) {
        if (sprite.getElements().keySet().size() == 0) {
            element.setTextContent(sprite.getValue());
        }
        for (String elementString : sprite.getElements().keySet()) {
            SpriteElement childSprite = sprite.getElements().get(elementString);
            Element childElement =
                    builder.createElement(elementString, childSprite.getAttributes());
            element.appendChild(childElement);
            addChildren(builder, childElement, childSprite);
        }
    }
}
