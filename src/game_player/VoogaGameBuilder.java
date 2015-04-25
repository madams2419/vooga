package game_player;

import game_engine.Level;
import game_engine.behaviors.Behavior;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;
import game_engine.collision.Collision;
import game_engine.collision.CollisionEngine;
import game_engine.control.ControlManager;
import game_engine.control.KeyControl;
import game_engine.objective.GameTimer;
import game_engine.objective.Objective;
import game_engine.physics_engine.complex.ComplexPhysicsEngine;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.sprite.Sprite;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.input.KeyCode;


public class VoogaGameBuilder {

    private XMLParser parser;
    private Map<String, Sprite> mySpriteMap;
    private Map<String, Objective> myObjectiveMap;
    private ComplexPhysicsEngine engine;

    public VoogaGameBuilder (XMLParser p) {
        parser = p;
        mySpriteMap = new HashMap<>();
        myObjectiveMap = new HashMap<>();
    }

    public VoogaGame build () {
        VoogaGame game = new VoogaGame();
        parser.moveDown("game/level");
        for (String directory : parser.getValidSubDirectories()) {
            Level level = buildLevel(directory);
            game.addLevel(level);
        }
       game.setActiveLevel(Integer.parseInt(parser.getValue("start")));
        
        return game;
    }

    private Level buildLevel (String levelID) {
        engine = buildPhysicsEngine(1.0/60);
        parser.moveDown(levelID);
        Level level = new Level();
        
        parser.moveDown("sprite");
        for (String directory : parser.getValidSubDirectories()) {
            Sprite sprite = buildSprite(directory);
            level.addSprite(sprite);
        }
        parser.moveUp();

        level.setObjectives(buildObjectives());
        level.setCollisionEngine(buildCollisionEngine());
        level.setControlManager(buildControl());

        parser.moveUp();
        return level;
    }

    private Sprite buildSprite (String spriteID) {
	parser.moveDown(spriteID);
	
	Animation spriteImage = new Animation();
	parser.moveDown("animation");
        for (int i = 1; i < parser.getValidSubDirectories().size(); i++) {
            String directory = parser.getValidSubDirectories().get(i);
            parser.moveDown(directory);
            String name = parser.getValue("name");
            String image = parser.getValue("image");
            spriteImage.setImage(name, image);
            parser.moveUp();
        }
        parser.moveUp();
        
        IPhysicsObject physObj = buildPhysicsObject(spriteImage);
        
        parser.moveUp();
        
        Sprite sprite = new Sprite(parser.getValue("initialState"), spriteImage, physObj);
        mySpriteMap.put(spriteID, sprite);
        return sprite;
    }
    
    private IPhysicsObject buildPhysicsObject(Animation a) {
	parser.moveDown("physics");
        Material material = Material.valueOf(parser.getValue("material").toUpperCase());
        int startX = Integer.parseInt(parser.getValue("x"));
        int startY = Integer.parseInt(parser.getValue("y"));
        String type = parser.getValue("type");
        parser.moveUp();
        int height = Integer.parseInt(parser.getValue("height"));
        int width = Integer.parseInt(parser.getValue("width"));
        
        
	try {
	    Class physObj = Class.forName("game_engine.physics_engine.physics_object.complex_physics_object." + type);
	    Constructor c = physObj.getConstructor(ComplexPhysicsEngine.class, int.class, int.class, Material.class, int.class, int.class, Animation.class);
	    return (IPhysicsObject) c.newInstance(engine, width, height, material, startX, startY, a);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    private ComplexPhysicsEngine buildPhysicsEngine (double fps) {
        ComplexPhysicsEngine globalEngine = new ComplexPhysicsEngine(0, fps);
        return globalEngine;
    }

    private Sprite getSprite (String id) {
        String fullID = "sprite_" + id + "/";
        return mySpriteMap.get(fullID);
    }

    private CollisionEngine buildCollisionEngine () {
        parser.moveDown("collision");
        CollisionEngine collisionEngine = new CollisionEngine();
        for (String directory : parser.getValidSubDirectories()) {
            collisionEngine.addCollision(buildCollision(directory));
        }
        parser.moveUp();
        return collisionEngine;
    }

    private Collision buildCollision (String collisionID) {
        parser.moveDown(collisionID);
        Sprite spriteA = getSprite(parser.getValue("sprite_1"));
        Sprite spriteB = getSprite(parser.getValue("sprite_2"));
        IBehavior behaviorList = buildBehaviorList();
        boolean realistic = Boolean.getBoolean(parser.getValue("realistic"));
        Collision collision = new Collision(spriteA, spriteB, behaviorList, realistic);
        parser.moveUp();
        return collision;
    }

    private IBehavior buildBehaviorList () {
        parser.moveDown("behaviors");
        MultipleBehaviors behaviors = new MultipleBehaviors();
        for (String directory : parser.getValidSubDirectories()) {
            behaviors.addBehavior(buildBehavior(directory));
        }
        parser.moveUp();
        return behaviors;
    }

    private IBehavior buildBehavior (String behaviorID) {
        parser.moveDown(behaviorID);
        String id = parser.getValue("targetType") + "_" + parser.getValue("targetIndex") + "/";
        String name = parser.getValue("name");
        IActor actor = getActor(id);
        IAction behavior = actor.getAction(name);
        String[] params = parser.getValue("parameters").split(" ");
        parser.moveUp();
        return new Behavior(behavior, params);
    }

    private List<Objective> buildObjectives () {
        parser.moveDown("objective");
        List<Objective> objectives = new ArrayList<>();
        for (String directory : parser.getValidSubDirectories()) {
            Objective objective = buildObjective(directory);
            objectives.add(objective);
            myObjectiveMap.put(directory, objective);
        }

        for (String directory : parser.getValidSubDirectories()) {
            parser.moveDown(directory);
            String[] preReqsID = parser.getValue("prereqs").split("\\s+");
            List<Objective> preReqs =
                    Arrays.asList(preReqsID)
                            .stream()
                            .filter(string -> !string.isEmpty())
                            .map(s -> myObjectiveMap.get("objective_" + s + "/"))
                            .collect(Collectors.toList());
            myObjectiveMap.get(directory).setPreReqs(preReqs);

            parser.moveUp();
        }
        parser.moveUp();
        return objectives;
    }

    private Objective buildObjective (String objectiveID) {
        parser.moveDown(objectiveID);
        Objective objective = new Objective();

        for (String directory : parser.getValidSubDirectories()) {
            if (directory.toLowerCase().startsWith("on")) {
                parser.moveDown(directory);
                IBehavior behavior = buildBehaviorList();
                objective.setBehavior(directory.substring(2, directory.length() - 1), behavior);
                parser.moveUp();
            }
        }
        String timer = parser.getValue("timer");
        Optional.ofNullable(timer).ifPresent(string -> objective.setTimer(new GameTimer(Long
                                                     .parseLong(string))));
        parser.moveUp();
        return objective;
    }

    private IActor getActor (String id) {
        if (id.startsWith("sprite")) {
            return mySpriteMap.get(id);
        }
        else {
            return myObjectiveMap.get(id);
        }
    }

    private ControlManager buildControl () {
        parser.moveDown("control");
        ControlManager controlManager = new ControlManager();
        for (String controlDirectory : parser.getValidSubDirectories()) {
            KeyControl newControl = buildKeyControl(controlDirectory);
            controlManager.addControl(newControl);
        }
        parser.moveUp();
        return controlManager;
    }

    private KeyControl buildKeyControl (String controlID) {
        parser.moveDown(controlID);
        Map<KeyCode, IBehavior> pressedKeyMap = new HashMap<>();
        Map<KeyCode, IBehavior> releasedKeyMap = new HashMap<>();
        Map<KeyCode, IBehavior> heldKeyMap = new HashMap<>();

        for (String keyDirectory : parser.getValidSubDirectories()) {
            parser.moveDown(keyDirectory);
            String keyName = parser.getValue("key");
            KeyCode keyCode = KeyCode.valueOf(keyName);

            parser.moveDown("onPressed");
            pressedKeyMap.put(keyCode, buildBehaviorList());
            parser.moveUp();

            parser.moveDown("onReleased");
            releasedKeyMap.put(keyCode, buildBehaviorList());
            parser.moveUp();

            parser.moveUp();
        }
        parser.moveUp();
        return new KeyControl(pressedKeyMap, releasedKeyMap, heldKeyMap);
    }
}