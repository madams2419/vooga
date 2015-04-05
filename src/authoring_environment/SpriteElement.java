package authoring_environment;

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

    void addAttribute (List<String> path, String attribute) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.addAttribute(path.get(path.size() - 1), attribute);
    }

    void addAttribute (String attributeName, String attributeValue) {
        myAttributes.put(attributeName, attributeValue);
    }

    public void addElement (List<String> path, String value) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.setValue(value);
    }

    void addElement (String elementName, SpriteElement element) {
        myElements.put(elementName, element);
    }

    void addElement (String elementName, String value) {
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
