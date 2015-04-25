package game_player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.input.KeyCode;
import game_engine.Level;
import game_engine.behaviors.Behavior;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;
import game_engine.collisions.Collision;
import game_engine.collisions.CollisionsManager;
import game_engine.collisions.detectors.HitboxDetector;
import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.detectors.MultipleDetector;
import game_engine.collisions.detectors.PixelPerfectDetector;
import game_engine.collisions.detectors.SimpleDetector;
import game_engine.collisions.resolvers.ICollisionResolver;
import game_engine.collisions.resolvers.MultipleResolver;
import game_engine.collisions.resolvers.PhysicalResolver;
import game_engine.collisions.resolvers.SimpleResolver;
import game_engine.controls.ControlScheme;
import game_engine.controls.ControlsManager;
import game_engine.controls.key_controls.KeyControlMap;
import game_engine.controls.key_controls.PressedKeyControlMap;
import game_engine.controls.key_controls.ReleasedKeyControlMap;
import game_engine.hitboxes.IHitbox;
import game_engine.hitboxes.MultipleHitbox;
import game_engine.hitboxes.SingleHitbox;
import game_engine.objectives.GameTimer;
import game_engine.objectives.Objective;
import game_engine.physics.Material;
import game_engine.physics.Vector;
import game_engine.physics.engines.ComplexPhysicsEngine;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.physics.objects.AcceleratingPhysicsObject;
import game_engine.physics.objects.ComplexPhysicsObject;
import game_engine.physics.objects.MovingPhysicsObject;
import game_engine.physics.objects.PhysicsObject;
import game_engine.sprite.Animation;
import game_engine.sprite.Sprite;

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
			game.addLevel(buildLevel(directory));
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
		
		level.setCollisionEngine(buildCollisionsManager());
		level.setControlManager(buildControlsManager());
		
		parser.moveUp();
		return level;
	}
	
	private PhysicsEngine buildPhysicsEngine() {
		parser.moveDown("physics_engine");
		
		String type = parser.getValue("type");
		
		PhysicsEngine engine = type.equals("ComlexPhysicsEngine") ? new ComplexPhysicsEngine(Double.parseDouble(parser.getValue("drag_coefficient"))) : new PhysicsEngine();
		
		parser.moveDown("global_accelerations");
		for (String label : parser.getValidLabels()) {
			String[] vector = parser.getValue(label).split(" ");
			engine.addGlobalAccel(new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
		}
		parser.moveUp();
		
//		parser.moveDown("global_forces");
//		for (String label : parser.getValidLabels()) {
//			String[] vector = parser.getValue(label).split(" ");
//			engine.addGlobalForce(new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
//		}
//		parser.moveUp();
		
		parser.moveUp();
		return engine;
	}
	
	private Sprite buildSprite(String spriteID, PhysicsEngine engine) {
		parser.moveDown(spriteID);
		
		Map<String, List<IHitbox>> hitboxes = new HashMap<>();
		Animation animation = buildAnimation(hitboxes);
		PhysicsObject physObj = buildPhysicsObject(animation, engine, hitboxes);
		String initialState = parser.getValue("initial_state");
		
		Sprite sprite = new Sprite(physObj, animation, initialState);
		
		parser.moveUp();
		return sprite;
	}
	
	private Animation buildAnimation(Map<String, List<IHitbox>> hitboxes) {
		parser.moveDown("animation");
		
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
	
	private PhysicsObject buildPhysicsObject(Animation animation, PhysicsEngine engine, Map<String, List<IHitbox>> hitboxes) {
		parser.moveDown("physics");
		
		String type = parser.getValue("type");
		String[] point = parser.getValue("position").split(" ");
		Vector position = new Vector(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
		
		PhysicsObject physObj = type.equals("ComplexPhysicsObject") ? new ComplexPhysicsObject(engine, hitboxes, position, animation, Material.valueOf(parser.getValue("material"))) :
								type.equals("AcceleratingPhysicsObject") ? new AcceleratingPhysicsObject(engine, hitboxes, position, animation) :
																				new MovingPhysicsObject(engine, hitboxes, position, animation);
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
    	String[] details = id.split("_");
    	IActor actor = details[0].startsWith("sprite") ? sprites.get(Integer.parseInt(details[1])) : details[0].startsWith("objective") ? objectives.get(Integer.parseInt(details[1])) : null;
    	return actor;
    }
    
    private CollisionsManager buildCollisionsManager() {
    	parser.moveDown("collisions");
    	
    	CollisionsManager manager = new CollisionsManager();
    	
    	for (String directory : parser.getValidSubDirectories()) {
    		manager.addCollision(buildCollision(directory));
    	}
    	
    	parser.moveUp();
    	return manager;
    }
    
    private Collision buildCollision(String collisionID) {
    	parser.moveDown(collisionID);
    	
    	String[] sprites = parser.getValue("sprites").split(" ");
    	Sprite a = getSprite(Integer.parseInt(sprites[0]));
    	Sprite b = getSprite(Integer.parseInt(sprites[1]));
    	
    	ICollisionDetector detector = buildDetector();
    	ICollisionResolver resolver = buildResolver();
    	
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
    										parser.getValue(label).equals("HitboxDetector") ? new HitboxDetector() :
    											parser.getValue(label).equals("PixelPerfectDetector") ? new PixelPerfectDetector() : null;
    		detector.addDetector(component);
    	}
    	
    	parser.moveUp();
    	return detector;
    }
    
    private ICollisionResolver buildResolver() {
    	parser.moveDown("resolvers");
    	
    	MultipleResolver resolver = new MultipleResolver();
    	
    	for (String directory : parser.getValidSubDirectories()) {
    		parser.moveDown(directory);
    		
    		String type = parser.getValue("type");
    		if (type.equals("PhysicalResolver")) {
    			resolver.addResolver(new PhysicalResolver());
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

//        		parser.moveDown("onReleased");
//        		onReleased.addBehavior(keyCode, buildBehaviorList());
//        		parser.moveUp();

//        		parser.moveDown("whilePressed");
//        		whilePressed.addBehavior(keyCode, buildBehaviorList());
//        		parser.moveUp();
        		
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