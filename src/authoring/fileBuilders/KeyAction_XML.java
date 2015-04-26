package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

public class KeyAction_XML {
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
	public KeyAction_XML(String key, List<String> behaviours,
			boolean onPress) {
		this.key = key;
		if (onPress) {
			behaviours.forEach(str -> {
				String[] behaviour = str.split(":");
				onPressed.add(new Behaviours_XML(
					"sprite", behaviour[0], behaviour[1],
					behaviour[2]));
			});
		}
	}
	
	public void appendBehaviours(KeyAction_XML other) {
		this.onPressed.addAll(other.onPressed);
		this.onReleased.addAll(other.onReleased);
		}
	
	public void writeToXML(Element parent, int index, XMLBuilder xml) {
		Element currentKey = xml.add(parent, "key_" + index);
		xml.addChildWithValue(currentKey, "key", key);
		Element pressed = xml.add(currentKey, "onPressed");
		Element beh = xml.add(pressed, "behaviours");
		for (int i = 0; i < onPressed.size(); i++)
			onPressed.get(i).writeToXML(beh, i, xml);
		Element released = xml.add(currentKey, "onReleased");
		beh = xml.add(released, "behaviours");
		for (int i = 0; i < onReleased.size(); i++)
			onReleased.get(i).writeToXML(beh, i, xml);
	}
}