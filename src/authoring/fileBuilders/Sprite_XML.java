package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import authoring.dataEditors.Sprite;


/***
 * 
 * @author daniel
 *
 */
public class Sprite_XML {

    private static final String ANIMATIONS = "animations";
    private static final String PATH = "path";
    private static final String INITIAL_STATE = "initial_state";
    private static final String SPRITE_ = "sprite_";
    private static final String DELAY_TIME = "1.0";
    private static final String PHYSICS_FORMAT = "%f %f";
    private static final String DEFAULT_INITIAL_STATE = "0";
    private String initial_state;
    private String path;
    private List<State> animation;
    private Physics physics;
    private int index;

    public Sprite_XML (Sprite s) {
        populateAnimations(s);
        initial_state = animation.size() > 0 ? animation.get(0).name : DEFAULT_INITIAL_STATE;
        path = s.getPath();
        physics =
                new Physics(s.getMyType(), s.getMyMaterial(), String.format(
                                                                            PHYSICS_FORMAT,
                                                                            s.getXPosition(),
                                                                            s.getYPosition()));
        index = s.getID();
    }

    private void populateAnimations (Sprite s) {
        animation = new ArrayList<>();
        s.getAnimations()
                .getAnimations()
                .forEach(
                         (state, imageuri) -> {
                             animation.add(new State(state,
                                                     new Image(imageuri,
                                                               DELAY_TIME, Double.toString(s
                                                                       .getWidth()),
                                                               Double.toString(s.getHeight()))));
                         });
    }

    public void writeToXML (Element parent, XMLBuilder xml) {
        Element current = xml.add(parent, SPRITE_ + index);
        xml.addChildWithValue(current, INITIAL_STATE, initial_state);
        xml.addChildWithValue(current, PATH, path);
        Element am = xml.add(current, ANIMATIONS);
        for (int i = 0; i < animation.size(); i++)
            animation.get(i).writeToXML(am, i, xml);
        physics.writeToXML(current, xml);
    }

    private class State {
        private static final String IMAGES = "images";
        private static final String NAME = "name";
        private static final String STATE = "state_";
        private String name;
        private Image[] images;

        private State (String name, Image ... images) {
            this.name = name;
            this.images = images;
        }

        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, STATE + index);
            xml.addChildWithValue(current, NAME, name);
            Element im = xml.add(current, IMAGES);
            for (int i = 0; i < images.length; i++)
                images[i].writeToXML(im, i, xml);
        }
    }

    private class Image {
        private static final String HEIGHT = "height";
        private static final String WIDTH = "width";
        private static final String DELAY = "delay";
        private static final String SOURCE = "source";
        private static final String IMAGE_ = "image_";
        private String source;
        private String delay;
        private String width;
        private String height;
        private Hitbox[] hitboxes;

        private Image (String source, String delay, String width, String height,
                       Hitbox ... hitboxes) {
            this.source = source;
            this.delay = delay;
            this.width = width;
            this.height = height;
            this.hitboxes = hitboxes;
        }

        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, IMAGE_ + index);
            xml.addChildWithValue(current, SOURCE, source);
            xml.addChildWithValue(current, DELAY, delay);
            xml.addChildWithValue(current, WIDTH, width);
            xml.addChildWithValue(current, HEIGHT, height);
            for (int i = 0; i < hitboxes.length; i++)
                hitboxes[i].writeToXML(current, i, xml);
        }
    }

    private class Hitbox {
        private static final String POINT_3 = "point_3";
        private static final String POINT_2 = "point_2";
        private static final String POINT_1 = "point_1";
        private static final String POINT_0 = "point_0";
        private static final String HITBOX = "hitbox_";
        private String point_0;
        private String point_1;
        private String point_2;
        private String point_3;

        private Hitbox (String point_0, String point_1, String point_2,
                        String point_3) {
            this.point_0 = point_0;
            this.point_1 = point_1;
            this.point_2 = point_2;
            this.point_3 = point_3;
        }

        public void writeToXML (Element parent, int index, XMLBuilder xml) {
            Element current = xml.add(parent, HITBOX + index);
            xml.addChildWithValue(current, POINT_0, point_0);
            xml.addChildWithValue(current, POINT_1, point_1);
            xml.addChildWithValue(current, POINT_2, point_2);
            xml.addChildWithValue(current, POINT_3, point_3);
        }
    }

    private class Physics {
        private static final String POSITION = "position";
        private static final String MATERIAL = "material";
        private static final String TYPE = "type";
        private static final String PHYSICS = "physics";
        private String type;
        private String material;
        private String position;

        private Physics (String type, String material, String position) {
            this.type = type;
            this.material = material;
            this.position = position;
        }

        public void writeToXML (Element parent, XMLBuilder xml) {
            Element physics = xml.add(parent, PHYSICS);
            xml.addChildWithValue(physics, TYPE, type);
            xml.addChildWithValue(physics, MATERIAL, material);
            xml.addChildWithValue(physics, POSITION, position);
        }
    }
}
