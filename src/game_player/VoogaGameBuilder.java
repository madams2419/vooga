package game_player;

import game_engine.Level;
import game_engine.behaviors.Behavior;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;
import game_engine.collisions.Collision;
import game_engine.collisions.CollisionsManager;
import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.detectors.MultipleDetector;
import game_engine.collisions.detectors.PhysicsDetector;
import game_engine.collisions.detectors.PixelPerfectDetector;
import game_engine.collisions.detectors.SimpleDetector;
import game_engine.collisions.resolvers.ICollisionResolver;
import game_engine.collisions.resolvers.MultipleResolver;
import game_engine.collisions.resolvers.SimpleResolver;
import game_engine.control.Control;
import game_engine.control.ControlManager;
import game_engine.control.ControlManagerFactory;
import game_engine.control.KeyControl;
import game_engine.control.VoiceControl;
import game_engine.controls.ControlScheme;
import game_engine.controls.ControlsManager;
import game_engine.controls.key_controls.KeyControlMap;
import game_engine.controls.key_controls.PressedKeyControlMap;
import game_engine.controls.key_controls.ReleasedKeyControlMap;
import game_engine.objectives.GameTimer;
import game_engine.objectives.Objective;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.rigidbodies.RigidBody;
import game_engine.physics.utilities.Vector;
import game_engine.sprite.Animation;
import game_engine.sprite.Sprite;
import game_engine.sprite.TransitionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

public class VoogaGameBuilder {

	private XMLParser parser;

	private Map<String, Sprite> sprites;
	private List<Objective> objectives;
	private Map<String, IActor> actors;

	private VoogaGame game;

	public VoogaGameBuilder(XMLParser p) {
		parser = p;
	}

	public VoogaGame buildGame() {
		parser.moveDown("game");
		System.out.println("labels" + parser.getValidLabels());
		actors = new HashMap<>();
		double frameRate = Double.parseDouble(parser.getValue("frame_rate"));
		double width = Double.parseDouble(parser.getValue("scene_width"));
		double height = Double.parseDouble(parser.getValue("scene_height"));
		game = new VoogaGame(frameRate, width, height);
		actors.put("game_0", game);
		parser.moveDown("level");
		for (String directory : parser.getValidSubDirectories("level")) {
			game.addLevel(buildLevel(directory));
		}

		int start = Integer.parseInt(parser.getValue("first_level"));
		game.setActiveLevel(start);
		parser.moveUp(2);

		return game;
	}

	private Level buildLevel(String levelID) {
		parser.moveDown(levelID);
		PhysicsEngine engine = buildPhysicsEngine();
		Level level = new Level(engine);
		sprites = new HashMap<>();
		objectives = new ArrayList<>();
		
		actors.put(levelID, level);
		ControlsManager manager = new ControlsManager();
		ControlManagerFactory controlFactory = new ControlManagerFactory();
		actors.put("controlManager_0", manager);
		//actors.put("controlManager_0", controlFactory.getControlManager("keyboard"));

		parser.moveDown("sprites");
		for (String directory : parser.getValidSubDirectories("sprite")) {
			Sprite sprite = buildSprite(directory, engine);
			level.addSprite(sprite);
			sprites.put(directory.split("_")[1], sprite);
			actors.put(directory, sprite);
		}
		parser.moveUp();

		parser.moveDown("level_objectives");
		for (String directory : parser.getValidSubDirectories("objective")) {
			Objective objective = buildObjective(directory);
			objectives.add(objective);
			actors.put(directory, objective);
		}
		level.setObjectives(objectives);
		int i = 0;
		for (String directory: parser.getValidSubDirectories()) {
		    parser.moveDown(directory);
		    if (parser.getValue("prereqs") != null && parser.getValue("prereqs").trim().length() > 0) {
		        String[] prereqs = parser.getValue("prereqs").split(" ");
	                    List<Objective> list = new ArrayList<>();
	                    for (String id: prereqs) {
	                        list.add(objectives.get(Integer.parseInt(id.trim())));
	                    }
	                    objectives.get(i).setPreReqs(list);
		    }
		    i++;
		    parser.moveUp();
		}
		parser.moveUp();
		
		level.setControlManager(buildControlsManager(manager));
		//level.setControlFactory(buildControlFactory(controlFactory));
		level.setCollisionEngine(buildCollisionsManager(engine));

		game.setTransitionManager(buildTransitionManager(game.getRoot()));
		game.getTransitionManager().initialize();
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
		for (String directory: parser.getValidSubDirectories("path")) {
			parser.moveDown(directory);
			String id = parser.getValue("id");
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
			String accelName = label.substring(label.lastIndexOf("/") + 1);
			String[] vector = parser.getValue(label).split(" ");
			System.out.println(accelName);
			engine.setGlobalAccel(accelName, new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
		}
		parser.moveUp();

		parser.moveDown("global_forces");
		for (String label : parser.getValidLabels()) {
			String forceName = label.substring(label.lastIndexOf("/") + 1);
			String[] vector = parser.getValue(label).split(" ");
			engine.setGlobalForce(forceName, new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1])));
		}
		parser.moveUp();

		parser.moveUp();
		return engine;
	}

	private Sprite buildSprite(String spriteID, PhysicsEngine engine) {
		parser.moveDown(spriteID);

		String id = spriteID.split("_")[1];
		Animation animation = buildAnimation(engine);
		PhysicsObject physObj = buildPhysicsObject(animation, engine);
		String initialState = parser.getValue("initial_state");
		Sprite sprite = new Sprite(physObj, animation, initialState, null, 0, id);

		parser.moveUp();
		return sprite;
	}

	private Animation buildAnimation(PhysicsEngine engine) {
		parser.moveDown("animations");
		Animation animation = new Animation(game.getHeight());
		for (String directory : parser.getValidSubDirectories("state")) {
			parser.moveDown(directory);

			String name = parser.getValue("name");

			parser.moveDown("images");
			for (String imageDirectory : parser.getValidSubDirectories()) {
				parser.moveDown(imageDirectory);
				String source = parser.getValue("source").trim();
				double delay = Double.parseDouble(parser.getValue("delay"));
				double width = Double.parseDouble(parser.getValue("width"));
				double height = Double.parseDouble(parser.getValue("height"));
				RigidBody rBody = buildRigidBody(width, height, engine);

				animation.associateImage(name, source, rBody, delay, height, width);

				parser.moveUp();
			}
			parser.moveUp();

			parser.moveUp();
		}

		parser.moveUp();
		return animation;
	}

	private RigidBody buildRigidBody(double width, double height, PhysicsEngine engine) {
		//TODO refactor this if we have time to add complex rigid bodies
		return engine.createRigidBody(width, height);
	}

	//	private IHitbox buildHitbox() {
	//		parser.moveDown("hitboxes");
	//		
	//		MultipleHitbox hitbox = new MultipleHitbox();
	//		for (String directory : parser.getValidSubDirectories()) {
	//			parser.moveDown(directory);
	//			
	//			SingleHitbox hb = new SingleHitbox();
	//			for (String label : parser.getValidLabels()) {
	//				String[] point = parser.getValue(label).split(" ");
	//				hb.addPoint(new Vector(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
	//			}
	//			
	//			hitbox.addHitbox(hb);
	//			parser.moveUp();
	//		}
	//		
	//		parser.moveUp();
	//		return hitbox;
	//	}

	private PhysicsObject buildPhysicsObject(Animation animation, PhysicsEngine engine) {
		parser.moveDown("physics");

		String type = parser.getValue("type");
		String[] point = parser.getValue("position").split(" ");
		Vector position = new Vector(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
		Material material = Material.valueOf(parser.getValue("material").toUpperCase());
		PhysicsObject physObj = engine.addPhysicsObject(animation.getRigidBody(), material, position);

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
				objective.setBehavior(directory.substring(2), behavior);
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
		for (String directory : parser.getValidSubDirectories("behavior")) {
			behaviors.addBehavior(buildBehavior(directory));
		}
		parser.moveUp();
		return behaviors;
	}

	private IBehavior buildBehavior(String behaviorID) {
		parser.moveDown(behaviorID);
		String id = parser.getValue("targetType") + "_" + parser.getValue("targetIndex");
		String name = parser.getValue("name");
		System.out.println(id + " " + name);
		IActor actor = getActor(id);
		IAction behavior = actor.getAction(name);
		System.out.println(behavior + " " + name);
		String[] params = parser.getValue("parameters").split(" ");
		parser.moveUp();
		return new Behavior(behavior, params);
	}

	private IActor getActor(String id) {
		//String[] details = id.split("_");
		//IActor actor = details[0].startsWith("sprite") ? sprites.get(Integer.parseInt(details[1])) : details[0].startsWith("objective") ? objectives.get(Integer.parseInt(details[1])) : null;
		//return actor;
		return actors.get(id);
	}

	private CollisionsManager buildCollisionsManager(PhysicsEngine engine) {
		parser.moveDown("collisions");

		CollisionsManager manager = new CollisionsManager();

		for (String directory : parser.getValidSubDirectories("collision")) {
			manager.addCollision(buildCollision(directory, engine));
		}

		parser.moveUp();
		return manager;
	}

	private Collision buildCollision(String collisionID, PhysicsEngine engine) {
		parser.moveDown(collisionID);

		String[] spriteIds = parser.getValue("sprites").split(" ");
		Sprite a = sprites.get(spriteIds[0]);
		Sprite b = sprites.get(spriteIds[1]);

		ICollisionDetector detector = buildDetector(engine);
		ICollisionResolver resolver = buildResolver(engine);

		Collision collision = new Collision(detector, resolver, a, b);
		parser.moveUp();
		return collision;
	}




	

	private ControlManagerFactory buildControlFactory(ControlManagerFactory cFactory) {
		parser.moveDown("controls");

		int startIndex = Integer.parseInt(parser.getValue("active_scheme"));
		ControlsManager manager = new ControlsManager();
		
		int count = 0;

		for (String directory : parser.getValidSubDirectories("control_scheme")) {
			parser.moveDown(directory);
			count++;
			System.out.println("Start control_scheme "+count);
			ControlScheme scheme = new ControlScheme();
			
			Map<KeyCode, IBehavior> pressMap = new HashMap<>();
			Map<KeyCode, IBehavior> releaseMap = new HashMap<>();
			Map<KeyCode, IBehavior> heldMap = new HashMap<>();
			
			for(String type : parser.getValidSubDirectories("control_type")){
				parser.moveDown(type);
				System.out.println("In builder control_type");

				for(String scene: parser.getValidSubDirectories("scene_type")){
					parser.moveDown(scene);

					System.out.println("In builder scene_type");
					
					PressedKeyControlMap onPressed = new PressedKeyControlMap();
					scheme.addPressedControlMap(onPressed);
					ReleasedKeyControlMap onReleased = new ReleasedKeyControlMap();
					scheme.addReleasedControlMap(onReleased);
					KeyControlMap whilePressed = new KeyControlMap();
					scheme.addControlMap(whilePressed);
					
					

					for (String key : parser.getValidSubDirectories("key")) {
						parser.moveDown(key);
						
						KeyCode keyCode = KeyCode.valueOf(parser.getValue("key"));
						System.out.println("In builder key, add "+parser.getValue("key"));
						
						
						parser.moveDown("onPressed");
						//onPressed.addBehavior(keyCode, buildBehaviorList());
						pressMap.put(keyCode, buildBehaviorList());
						System.out.println("The built behaviors are "+buildBehaviorList());
						parser.moveUp();

						parser.moveDown("onReleased");
						//onReleased.addBehavior(keyCode, buildBehaviorList());
						releaseMap.put(keyCode, buildBehaviorList());
						parser.moveUp();

						parser.moveDown("whilePressed");
						//whilePressed.addBehavior(keyCode, buildBehaviorList());
						heldMap.put(keyCode, buildBehaviorList());
						parser.moveUp();
						
						parser.moveUp();
					}
					
					parser.moveUp();
				}
					parser.moveUp();
			}
			
			System.out.println("End control_scheme "+count);
			Control keyControl = new KeyControl(pressMap, releaseMap, heldMap);
			cFactory.getControlManager("keyboard").addControl(keyControl);
			manager.addControlScheme(scheme);
			parser.moveUp();
		}

		manager.setActiveControlScheme(startIndex);

		parser.moveUp();
		return cFactory;
	}
	


    
    private ICollisionDetector buildDetector(PhysicsEngine engine) {
    	parser.moveDown("detectors");
    	
    	MultipleDetector detector = new MultipleDetector();
    	for (String label : parser.getValidLabels()) {
    		String test = parser.getValue(label);
    		ICollisionDetector component = parser.getValue(label).equals("SimpleDetector") ? new SimpleDetector() : 
    										parser.getValue(label).equals("HitboxDetector") ? new PhysicsDetector(engine) :
    											parser.getValue(label).equals("PhysicsDetector") ? new PhysicsDetector(engine) :
    											parser.getValue(label).equals("PixelPerfectDetector") ? new PixelPerfectDetector() : null;
    		detector.addDetector(component);
    	}
    	
    	parser.moveUp();
    	return detector;
    }
    
    private ICollisionResolver buildResolver(PhysicsEngine engine) {
    	parser.moveDown("resolvers");
    	
    	MultipleResolver resolver = new MultipleResolver();
    	
    	for (String directory : parser.getValidSubDirectories("resolver")) {
    		parser.moveDown(directory);
    		
    		String type = parser.getValue("type");
    		if (type.equals("PhysicalResolver")) {
    			// physical resolver doesn't exist anymore
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
    
    private ControlsManager buildControlsManager(ControlsManager manager) {
    	parser.moveDown("controls");
    	
    	int startIndex = Integer.parseInt(parser.getValue("active_scheme"));
    	
    	for (String directory : parser.getValidSubDirectories("control_scheme")) {
    		parser.moveDown(directory);
    		
    		ControlScheme scheme = new ControlScheme();
    		
    		PressedKeyControlMap onPressed = new PressedKeyControlMap();
    		scheme.addPressedControlMap(onPressed);
    		ReleasedKeyControlMap onReleased = new ReleasedKeyControlMap();
    		scheme.addReleasedControlMap(onReleased);
    		KeyControlMap whilePressed = new KeyControlMap();
    		scheme.addControlMap(whilePressed);
    		
    		for (String key : parser.getValidSubDirectories("key")) {
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