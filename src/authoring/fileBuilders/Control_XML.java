package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import authoring.dataEditors.Sprite;


public class Control_XML {

    private static final String ACTIVE_SCHEME = "active_scheme";
    private String active_scheme;
    private List<ControlScheme> control_schemes;

    public Control_XML (Map<Sprite, Map<String, List<String>>> controls) {
        control_schemes = new ArrayList<>();
        for (Sprite s : controls.keySet()) {
            control_schemes.add(new ControlScheme(s.getID(), controls.get(s)));
        }
    }

    public Control_XML (List<KeyAction_XML> mActions) {
        control_schemes = new ArrayList<>();
        control_schemes.add(new ControlScheme(mActions));
    }

    public void writeToXML (Element control, XMLBuilder xml) {
        xml.addChildWithValue(control, ACTIVE_SCHEME, active_scheme);
        for (int i = 0; i < control_schemes.size(); i++)
            control_schemes.get(i).writeToXML(control, i, xml);
    }

    private class ControlScheme {

        private static final String CONTROL_SCHEME = "control_scheme_";
        private List<KeyAction_XML> keyActions;

        private ControlScheme (List<KeyAction_XML> actions) {
            keyActions = actions;
        }

        private ControlScheme (int index, Map<String, List<String>> actions) {
            keyActions = new ArrayList<>();
            for (String key : actions.keySet()) {
                keyActions.add(new KeyAction_XML(key, actions.get(key), true));
            }
        }

        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, CONTROL_SCHEME + index);
            for (int i = 0; i < keyActions.size(); i++)
                keyActions.get(i).writeToXML(current, i, xml);
        }
    }

}
