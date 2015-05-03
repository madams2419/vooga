// This entire file is part of my masterpiece.
// Daniel Luker

package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Element;
import authoring.dataEditors.Sprite;
import authoring.util.FrontEndUtils;


public class Collision_XML extends Object_XML {

    private String sprites;
    private String[] detectors;
    private List<Resolver> resolvers = new ArrayList<>();

    public Collision_XML (Sprite a, Sprite b, List<String> interactions) {
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

    @Override
    public void writeToXML (Element parent, int index, XMLBuilder xml) {
        Element current = xml.add(parent, COLLISION + index);
        xml.addChildWithValue(current, SPRITES, sprites);
        Element det = xml.add(current, DETECTORS);
        if (detectors != null) {
            for (int i = 0; i < detectors.length; i++)
                xml.addChildWithValue(det, DETECTOR + i, detectors[i]);
        }
        Element res = xml.add(current, RESOLVERS);
        for (int i = 0; i < resolvers.size(); i++)
            resolvers.get(i).writeToXML(res, i, xml);
    }

    private class Resolver extends Object_XML {

        private String type;
        private Behaviours_XML[] behaviours;

        private Resolver (String type, Behaviours_XML ... behaviours) {
            this.type = type;
            this.behaviours = behaviours;
        }

        @Override
        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, RESOLVER + index);
            xml.addChildWithValue(current, TYPE, type);
            Element beh = xml.add(current, BEHAVIOURS);
            for (int i = 0; i < behaviours.length; i++)
                behaviours[i].writeToXML(beh, i, xml);
        }
    }

}
