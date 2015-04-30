package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Element;
import authoring.dataEditors.Sprite;
import authoring.util.FrontEndUtils;


public class Collision_XML {

    private static final String RESOLVERS = "resolvers";
    private static final String DETECTOR = "detector_";
    private static final String DETECTORS = "detectors";
    private static final String SPRITES = "sprites";
    private static final String COLLISION = "collision_";
    private static final String PHYSICS_RESOLVER = "PhysicsResolver";
    private static final String SIMPLE_RESOLVER = "SimpleResolver";
    private static final String SIMPLE_DETECTOR = "SimpleDetector";
    private static final String COLON = ":";
    private String sprites;
    private String[] detectors;
    private List<Resolver> resolvers = new ArrayList<>();

    public Collision_XML (Sprite a, Sprite b, List<String> interactions) {
        System.err.println(interactions.toString());
        sprites = FrontEndUtils.getSpritesIDSorted(a, b);
        parseInteractions(interactions);
    }

    private void parseInteractions (List<String> interactions) {
        interactions.forEach(str -> {
            String[] params = str.split(COLON);
            if (params[0].equals(SIMPLE_RESOLVER))
                resolvers.add(new Resolver(params[0], new Behaviours_XML(Arrays
                        .copyOfRange(params, 1, params.length))));
                else if (params[0].equals(PHYSICS_RESOLVER))
                    resolvers.add(new Resolver(params[0]));
            });
    }

    public void writeToXML (Element parent, int index, XMLBuilder xml) {
        Element current = xml.add(parent, COLLISION + index);
        xml.addChildWithValue(current, SPRITES, sprites);
        Element det = xml.add(current, DETECTORS);
        xml.addChildWithValue(det, DETECTOR + 0, SIMPLE_DETECTOR);
        Element res = xml.add(current, RESOLVERS);
        for (int i = 0; i < resolvers.size(); i++)
            resolvers.get(i).writeToXML(res, i, xml);
    }

    private class Resolver {
        private static final String BEHAVIOURS = "behaviors";
        private static final String TYPE = "type";
        private static final String RESOLVER = "resolver_";
        private String type;
        private Behaviours_XML[] behaviours;

        private Resolver (String type, Behaviours_XML ... behaviours) {
            this.type = type;
            this.behaviours = behaviours;
        }

        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, RESOLVER + index);
            xml.addChildWithValue(current, TYPE, type);
            Element beh = xml.add(current, BEHAVIOURS);
            for (int i = 0; i < behaviours.length; i++)
                behaviours[i].writeToXML(beh, i, xml);
        }
    }

}
