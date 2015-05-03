// This entire file is part of my masterpiece.
// Daniel Luker

package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;


public class KeyAction_XML extends Object_XML {

    private String key;
    private List<Behaviours_XML> onPressed = new ArrayList<>();
    private List<Behaviours_XML> onReleased = new ArrayList<>();

    /***
     * 
     * @param targetIndex
     * @param key
     * @param behaviours
     * @param onPress
     */
    public KeyAction_XML (String key, List<String> behaviours,
                          boolean onPress) {
        this.key = key;
        onPressed = new ArrayList<>();
        onReleased = new ArrayList<>();
        if (onPress) {
            behaviours.forEach(str -> {
                String[] behaviour = str.split(COLON);
                onPressed.add(new Behaviours_XML(
                                                 SPRITE, behaviour[0], behaviour[1],
                                                 behaviour[2]));
            });
        }
    }

    public void appendBehaviours (KeyAction_XML other) {
        this.onPressed.addAll(other.onPressed);
        this.onReleased.addAll(other.onReleased);
    }

    @Override
    public void writeToXML (Element parent, int index, XMLBuilder xml) {
        Element currentKey = xml.add(parent, KEY_ + index);
        xml.addChildWithValue(currentKey, KEY, key);
        Element pressed = xml.add(currentKey, ON_PRESSED);
        Element beh = xml.add(pressed, BEHAVIOURS);
        for (int i = 0; i < onPressed.size(); i++)
            onPressed.get(i).writeToXML(beh, i, xml);
        Element released = xml.add(currentKey, ON_RELEASED);
        beh = xml.add(released, BEHAVIOURS);
        for (int i = 0; i < onReleased.size(); i++)
            onReleased.get(i).writeToXML(beh, i, xml);
    }
}
