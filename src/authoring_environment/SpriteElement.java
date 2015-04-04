package authoring_environment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * Class that contains information about the sprites for eventually generating xml files
 * 
 * **not finished**
 * 
 * @author Daniel Luker, Natalie Chanfreau
 *
 */
public class SpriteElement {
    Map<String, SpriteElement> myElements;
    Map<String, String> myAttributes;
    // List<String> myValues; // can there be more than one value?
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
        // myValues.add(value);
        myValue = value;
    }

    public SpriteElement () {
        myElements = new HashMap<>();
        myAttributes = new HashMap<>();
        // myValues = new ArrayList<>();
    }

    void addAttribute (List<String> path, String value) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.addAttribute(path.get(path.size() - 1), value);
    }

    void addAttribute (String attributeName, String attributeValue) {
        myAttributes.put(attributeName, attributeValue);
    }

    public void addElement (List<String> path, String value) {
        SpriteElement myDescendant = findElement(path);
        myDescendant.addElement(path.get(path.size() - 1), value);
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

    String getValue (List<String> path) {
        SpriteElement sprite = findElement(path);
        return sprite.getValue();
    }

    String getValue () {
        return myValue;
    }

    // List<String> getValues (List<String> path) {
    // SpriteElement sprite = findElement(path);
    // return sprite.getValues();
    // }
    //
    // List<String> getValues () {
    // return myValues;
    // }

    private SpriteElement getElement (String elementName) {
        return myElements.get(elementName);
    }
}
