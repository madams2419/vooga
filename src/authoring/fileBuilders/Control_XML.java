package authoring.fileBuilders;

import org.w3c.dom.Element;

public class Control_XML {

	private String active_scheme;
	private ControlScheme[] control_schemes;

	public void writeToXML(Element control, XMLBuilder xml) {
		xml.addChildWithValue(control, "active_scheme", active_scheme);
		for(int i = 0; i < control_schemes.length; i++)
			control_schemes[i].writeToXML(control, i, xml);
	}

	private class ControlScheme {
		private KeyAction[] keyActions;

		private ControlScheme(KeyAction[] keyActions) {
			this.keyActions = keyActions;
		}

		public void writeToXML(Element parent, int index, XMLBuilder xml) {
			Element current = xml.add(parent, "control_scheme_"+index);
			for(int i = 0; i < keyActions.length; i++)
				keyActions[i].writeToXML(current, i, xml);				
		}
	}

	private class KeyAction {
		private String key;
		private Behaviours_XML[] onPressed;
		private Behaviours_XML[] onReleased;

		private KeyAction(String key, Behaviours_XML[] onPressed,
				Behaviours_XML[] onReleased) {
			super();
			this.key = key;
			this.onPressed = onPressed;
			this.onReleased = onReleased;
		}

		public void writeToXML(Element parent, int index, XMLBuilder xml) {
			Element currentKey = xml.add(parent, "key_" + index);
			xml.addChildWithValue(currentKey, "key", key);
			Element pressed = xml.add(currentKey, "onPressed");
			Element beh = xml.add(pressed, "behaviours");
			for (int i = 0; i < onPressed.length; i++)
				onPressed[i].writeToXML(beh, i, xml);
			Element released = xml.add(currentKey, "onReleased");
			beh = xml.add(released, "behaviours");
			for (int i = 0; i < onReleased.length; i++)
				onReleased[i].writeToXML(beh, i, xml);
		}
	}

}
