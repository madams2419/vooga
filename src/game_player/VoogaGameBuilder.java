package game_player;

import game_engine.Level;
import game_engine.behaviors.Behavior;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;
import game_engine.collisions.Collision;
import game_engine.collisions.CollisionsManager;
import game_engine.collisions.detectors.PhysicsDetector;
import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.detectors.MultipleDetector;
import game_engine.collisions.detectors.PixelPerfectDetector;
import game_engine.collisions.detectors.SimpleDetector;
import game_engine.collisions.resolvers.ICollisionResolver;
import game_engine.collisions.resolvers.MultipleResolver;
import game_engine.collisions.resolvers.SimpleResolver;
import game_engine.controls.ControlScheme;
import game_engine.controls.ControlsManager;
import game_engine.controls.key_controls.KeyControlMap;
import game_engine.controls.key_controls.PressedKeyControlMap;
import game_engine.controls.key_controls.ReleasedKeyControlMap;
import game_engine.objectives.GameTimer;
import game_engine.objectives.Objective;
import game_engine.physics.Material;
import game_engine.physics.Vector;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.sprite.Animation;
import game_engine.sprite.Sprite;
import game_engine.sprite.TransitionManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;

public class VoogaGameBuilder {
	
	private XMLParser parser;
	
	private List<Sprite> sprites;
	private List<Objective> objectives;
	
	private VoogaGame game;
	
	public VoogaGameBuilder(XMLParser p) {
		parser = p;
	}
	
	public VoogaGame buildGame() {
		parser.moveDown("game");
		
		double frameRate = Double.parseDouble(parser.getValue("frame_rate"));
		double width = Double.parseDouble(parser.getValue("scene_width"));
		double height = Double.parseDouble(parser.getValue("scene_height"));
		game = new VoogaGame(frameRate, width, height);
		
		parser.moveDown("level");
		for (String directory : parser.getValidSubDirectories()) {
		    if (directory.startsWith("level")) {
			game.addLevel(buildLevel(directory));
		    }
		}
		
		int start = Integer.parseInt(parser.getValue("first_level"));
		game.setActiveLevel(start);
		parser.moveUp(2);
		
		return game;
	}
	
	private Level buildLevel(String levelID) {
		parser.moveDown(levelID);
		
		Level level = new Level();
		sprites = new ArrayList<>();
		objectives = new ArrayList<>();
		
		PhysicsEngine engine = buildPhysicsEngine();
		
		parser.moveDown("sprites");
		for (String directory : parser.getValidSubDirectories()) {
			Sprite sprite = buildSprite(directory, engine);
			level.addSprite(sprite);
			sprites.add(sprite);
		}
		parser.moveUp();
		
		parser.moveDown("level_objectives");
		for (String directory : parser.getValidSubDirectories()) {
			Objective objective = buildObjective(directory);
			level.addObjective(objective);
			objectives.add(objective);
		}
		parser.moveUp();
		
		level.setCollisionEngine(buildCollisionsManager(engine));
		level.setControlManager(buildControlsManager());
		game.setTransitionManager(buildTransitionManager(game.getRoot()));
		game.getTransitionManager().initialize();
		game.getTransitionManager().getParams().forEach(param->{
		    System.out.println(Arrays.toString(param));
		});
		game.getTransitionManager().playTransitions();
		
		parser.moveUp();
		return level;
	}
	
	private TransitionManager buildTransitionManager(Group group) {
	    parser.moveDown("sprite_paths");
	    ArrayList<Sprite> pathSprites = new ArrayList<>();
	    ArrayList<String[]> pathValues = new ArrayList<>();
	    ArrayList<Integer> durations = new ArrayList<>();
	    ArrayList<Integer> delays = new ArrayList<>();
	    for (String directory: parser.getValidSubDirectories()) {
	        parser.moveDown(directory);
	        int id = Integer.parseInt(parser.getValue("id"));
	        Sprite sprite = sprites.get(id);
	        String[] values = parser.getValue("values").split(" ");
	        pathSprites.add(sprite);
	        pathValues.add(values);
	         int duration = Integer.parseInt(parser.getValue("duration"));
	         durations.add(duration);
	         int delay = Integer.parseInt(parser.getValue("delay"));
	         delays.add(delay);
	        parser.moveUp();
	    }
	    parser.moveUp();
	    return new TransitionManager(group,pathSprites,pathValues,durations,delays);
	}
	
	private PhysicsEngine buildPhysicsEngine() {
		parser.moveDown("physics_engine");
		
		String type = parser.getValue("type");
		System.out.println(type);
		PhysicsEngine engine = new PhysicsEngine();
		
		parser.moveDown("global_accelerations");
		for (String label : parser.getValidLabels()) {
			String[] vector = parser.getValue(label).split(" ");
			System.out.println(Arrays.asList(vector));
			engine.setGlobalAccel(label, new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
		}
		parser.moveUp();
		
		parser.moveDown("global_forces");
		for (String label : parser.getValidLabels()) {
			String[] vector = parser.getValue(label).split(" ");
			engine.setGlobalForce(label, new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
		}
		parser.moveUp();
		
		parser.moveUp();
		return engine;
	}
	
	private Sprite buildSprite(String spriteID, PhysicsEngine engine) {
		parser.moveDown(spriteID);
		
		Map<String, List<IHitbox>> hitboxes = new HashMap<>();
		Animation animation = buildAnimation(hitboxes);
		SimplePhysicsObject physObj = buildPhysicsObject(animation, engine, hitboxes);
		String initialState = parser.getValue("initial_state");
		
		Sprite sprite = new Sprite(physObj, animation, initialState, null, 0);
		
		parser.moveUp();
		return sprite;
	}
	
	private Animation buildAnimation(Map<String, List<IHitbox>> hitboxes) {
		parser.moveDown("animations");
		
		Animation animation = new Animation(game.getHeight());
		
		for (String directory : parser.getValidSubDirectories()) {
			parser.moveDown(directory);
			
			String name = parser.getValue("name");
			List<IHitbox> hb = new ArrayList<>();
			
			parser.moveDown("images");
			for (String imageDirectory : parser.getValidSubDirectories()) {
				parser.moveDown(imageDirectory);
				String source = parser.getValue("source");
				double delay = Double.parseDouble(parser.getValue("delay"));
				double width = Double.parseDouble(parser.getValue("width"));
				double height = Double.parseDouble(parser.getValue("height"));
				
				animation.associateImage(name, source, delay, height, width);
				
				hb.add(buildHitbox());
				parser.moveUp();
			}
			parser.moveUp();
			
			hitboxes.put(name, hb);
			parser.moveUp();
		}
		
		parser.moveUp();
		return animation;
	}
	
	private IHitbox buildHitbox() {
		parser.moveDown("hitboxes");
		
		MultipleHitbox hitbox = new MultipleHitbox();
		for (String directory : parser.getValidSubDirectories()) {
			parser.moveDown(directory);
			
			SingleHitbox hb = new SingleHitbox();
			for (String label : parser.getValidLabels()) {
				String[] point = parser.getValue(label).split(" ");
				hb.addPoint(new Vector(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
			}
			
			hitbox.addHitbox(hb);
			parser.moveUp();
		}
		
		parser.moveUp();
		return hitbox;
	}
	
	private SimplePhysicsObject buildPhysicsObject(Animation animation, PhysicsEngine engine, Map<String, List<IHitbox>> hitboxes) {
		parser.moveDown("physics");
		
		String type = parser.getValue("type");
		String[] point = parser.getValue("position").split(" ");
		Vector position = new Vector(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
		
		SimplePhysicsObject physObj = type.equals("ComplexPhysicsObject") ? new ComplexPhysicsObject(engine, hitboxes, position, animation, Material.valueOf(parser.getValue("material").toUpperCase())) :
																	  new SimplePhysicsObject(engine, hitboxes, position, animation, Double.parseDouble(parser.getValue("mass")));
		parser.moveUp();
		return physObj;
	}
	
    private Objective buildObjective(String objectiveID) {
        parser.moveDown(objectiveID);
        
        Objective objective = new Objective();

        for (String directory : parser.getValidSubDirectories()) {
            if (directory.toLowerCase().startsWith("on")) {
                parser.moveDown(directory);
                IBehavior behavior = buildBehaviorList();
                objective.setBehavior(directory.substring(2, directory.length()), behavior);
                parser.moveUp();
            }
        }
        String timer = parser.getValue("timer");
        Optional.ofNullable(timer).ifPresent(string -> objective.setTimer(new GameTimer(Long.parseLong(string))));
        parser.moveUp();
        return objective;
    }
    
    private IBehavior buildBehaviorList() {
        parser.moveDown("behaviors");
        MultipleBehaviors behaviors = new MultipleBehaviors();
        for (String directory : parser.getValidSubDirectories()) {
            behaviors.addBehavior(buildBehavior(directory));
        }
        parser.moveUp();
        return behaviors;
    }

    private IBehavior buildBehavior(String behaviorID) {
        parser.moveDown(behaviorID);
        String id = parser.getValue("targetType") + "_" + parser.getValue("targetIndex");
        String name = parser.getValue("name");
        IActor actor = getActor(id);
        IAction behavior = actor.getAction(name);
        String[] params = parser.getValue("parameters").split(" ");
        parser.moveUp();
        return new Behavior(behavior, params);
    }
    
    private IActor getActor(String id) {
        System.out.println(id);
    	String[] details = id.split("_");
    	System.out.println(Arrays.asList(details));
    	IActor actor = details[0].startsWith("sprite") ? sprites.get(Integer.parseInt(details[1])) : details[0].startsWith("objective") ? objectives.get(Integer.parseInt(details[1])) : null;
    	return actor;
    }
    
    private CollisionsManager buildCollisionsManager(PhysicsEngine engine) {
    	parser.moveDown("collisions");
    	
    	CollisionsManager manager = new CollisionsManager();
    	
    	for (String directory : parser.getValidSubDirectories()) {
    		manager.addCollision(buildCollision(directory, engine));
    	}
    	
    	parser.moveUp();
    	return manager;
    }
    
    private Collision buildCollision(String collisionID, PhysicsEngine engine) {
    	parser.moveDown(collisionID);
    	
    	String[] sprites = parser.getValue("sprites").split(" ");
    	Sprite a = getSprite(Integer.parseInt(sprites[0]));
    	Sprite b = getSprite(Integer.parseInt(sprites[1]));
    	
    	ICollisionDetector detector = buildDetector();
    	ICollisionResolver resolver = buildResolver(engine);
    	
    	Collision collision = new Collision(detector, resolver, a, b);
    	parser.moveUp();
    	return collision;
    }

    
    private Sprite getSprite(int id) {
    	return sprites.get(id);
    }
    
    private ICollisionDetector buildDetector() {
    	parser.moveDown("detectors");
    	
    	MultipleDetector detector = new MultipleDetector();
    	for (String label : parser.getValidLabels()) {
    		ICollisionDetector component = parser.getValue(label).equals("SimpleDetector") ? new SimpleDetector() : 
    										parser.getValue(label).equals("HitboxDetector") ? new PhysicsDetector() :
    											parser.getValue(label).equals("PixelPerfectDetector") ? new PixelPerfectDetector() : null;
    		detector.addDetector(component);
    	}
    	
    	parser.moveUp();
    	return detector;
    }
    
    private ICollisionResolver buildResolver(PhysicsEngine engine) {
    	parser.moveDown("resolvers");
    	
    	MultipleResolver resolver = new MultipleResolver();
    	
    	for (String directory : parser.getValidSubDirectories()) {
    		parser.moveDown(directory);
    		
    		String type = parser.getValue("type");
    		if (type.equals("PhysicalResolver")) {
    			resolver.addResolver(new PhysicalResolver(engine));
    		}
    		else if (type.equals("SimpleResolver")) {
    			resolver.addResolver(new SimpleResolver(buildBehaviorList()));
    		}
    		else if (type.equals("HitboxResolver")) {
    			// implement
    		}
    		
    		parser.moveUp();
    	}
    	
    	parser.moveUp();
    	return resolver;
    }
    
    private ControlsManager buildControlsManager() {
    	parser.moveDown("controls");
    	System.out.println(parser.getValue("active_scheme"));
    	
    	int startIndex = Integer.parseInt(parser.getValue("active_scheme"));
    	
    	ControlsManager manager = new ControlsManager();
    	
    	for (String directory : parser.getValidSubDirectories()) {
    		parser.moveDown(directory);
    		
    		ControlScheme scheme = new ControlScheme();
    		
    		PressedKeyControlMap onPressed = new PressedKeyControlMap();
    		scheme.addPressedControlMap(onPressed);
    		ReleasedKeyControlMap onReleased = new ReleasedKeyControlMap();
    		scheme.addReleasedControlMap(onReleased);
    		KeyControlMap whilePressed = new KeyControlMap();
    		scheme.addControlMap(whilePressed);
    		
    		for (String key : parser.getValidSubDirectories()) {
    			parser.moveDown(key);
    			
    			KeyCode keyCode = KeyCode.valueOf(parser.getValue("key"));
        		
        		parser.moveDown("onPressed");
        		onPressed.addBehavior(keyCode, buildBehaviorList());
        		parser.moveUp();

        		parser.moveDown("onReleased");
        		onReleased.addBehavior(keyCode, buildBehaviorList());
        		parser.moveUp();

        		//parser.moveDown("whilePressed");
        		//whilePressed.addBehavior(keyCode, buildBehaviorList());
        		//parser.moveUp();
        		
        		parser.moveUp();
    		}
    		
    		manager.addControlScheme(scheme);
    		parser.moveUp();
    	}
    	
    	manager.setActiveControlScheme(startIndex);
    	
    	parser.moveUp();
    	return manager;
    }
}