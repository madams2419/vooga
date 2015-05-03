// This entire file is part of my masterpiece.
// Daniel Luker

package authoring.fileBuilders;

import org.w3c.dom.Element;

public abstract class Object_XML {
	
    protected static final String ON_RELEASED = "onReleased";
    protected static final String BEHAVIOURS = "behaviors";
    protected static final String ON_PRESSED = "onPressed";
    protected static final String KEY_ = "key_";
    protected static final String KEY = "key";
    protected static final String SPRITE = "sprite";
    protected static final String COLON = ":";
    
    protected static final String PARAMETERS = "parameters";
    protected static final String NAME = "name";
    protected static final String TARGET_INDEX = "targetIndex";
    protected static final String TARGET_TYPE = "targetType";
    protected static final String BEHAVIOR = "behavior_";
    protected static final String SPACE = " ";
    
    protected static final String BEHAVIORS = "behaviors";
    protected static final String ON_FAILED = "onFailed";
    protected static final String ON_COMPLETE = "onComplete";
    protected static final String PREREQS = "prereqs";
    protected static final String DESCRIPTION = "description";
    protected static final String OBJECTIVE = "objective_";       
    protected static final String TYPE = "type";
    
    protected static final String RESOLVER = "resolver_";
    protected static final String RESOLVERS = "resolvers";
    protected static final String DETECTOR = "detector_";
    protected static final String DETECTORS = "detectors";
    protected static final String SPRITES = "sprites";
    protected static final String COLLISION = "collision_";
    protected static final String PHYSICS_RESOLVER = "PhysicsResolver";
    protected static final String SIMPLE_RESOLVER = "SimpleResolver";
    
    protected static final String ANIMATIONS = "animations";
    protected static final String PATH = "path";
    protected static final String INITIAL_STATE = "initial_state";
    protected static final String SPRITE_ = "sprite_";
    protected static final String DELAY_TIME = "1.0";
    protected static final String PHYSICS_FORMAT = "%f %f";
    protected static final String DEFAULT_INITIAL_STATE = "0";
    
    protected static final String IMAGES = "images";
    protected static final String STATE = "state_";
    protected static final String HEIGHT = "height";
    protected static final String WIDTH = "width";
    protected static final String DELAY = "delay";
    protected static final String SOURCE = "source";
    protected static final String IMAGE_ = "image_";
    
    protected static final String ACTIVE_SCHEME = "active_scheme";
    
    protected static final String ACCEL = "accel_";
    protected static final String GLOBAL_ACCELERATIONS = "global_accelerations";
    protected static final String DRAG_COEFFICIENT = "drag_coefficient";
    protected static final String DEFAULT_TYPE = "ComplexPhysicsEngine";
    protected static final String DEFAULT_DRAG = "0.2";
    protected static final String[] DEFAULT_ACCEL = { "0 0" };
    
    protected static final String POINT_3 = "point_3";
    protected static final String POINT_2 = "point_2";
    protected static final String POINT_1 = "point_1";
    protected static final String POINT_0 = "point_0";
    protected static final String HITBOX = "hitbox_";
    protected static final String POSITION = "position";
    protected static final String MATERIAL = "material";
    protected static final String PHYSICS = "physics";

	public abstract void writeToXML(Element parent, int index, XMLBuilder xml);

}
