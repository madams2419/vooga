package authoring_environment;
import java.util.ArrayList;
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
    List<String> myValues;

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

    SpriteElement (List<String> values) {
        this();
        myValues = values;
    }

    SpriteElement (List<String> attributeValues, List<String> attributeTypes, List<String> values) {
        this(attributeValues, attributeTypes);
        myValues = values;
    }

    SpriteElement () {
        myElements = new HashMap<>();
        myAttributes = new HashMap<>();
        myValues = new ArrayList<>();
    }

    void addAttribute (List<String> path, String value) {
        SpriteElement myDescendant = findElement(path, 0);
        myDescendant.addAttribute(path.get(path.size() - 1), value);
    }
    
    void addAttribute (String attributeName, String attributeValue) {
        myAttributes.put(attributeName, attributeValue);
    }

    void addElement (String elementName, SpriteElement element) {
        myElements.put(elementName, element);
    }
    
    private SpriteElement findElement(List<String> path, int index) {
        SpriteElement element = this;
        for (int i = index; i < path.size(); i++) {
            element = element.getElement(path.get(i));
        }
        return element;
    }

    static SpriteElement makeSpriteElement () {
        // TODO
        return null;
    }
    
    private SpriteElement getElement(String elementName) {
        return myElements.get(elementName);
    }
}
