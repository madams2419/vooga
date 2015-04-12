package game_player;


import game_engine.Behavior;
import game_engine.IAction;
import game_engine.IActor;
import game_engine.IBehavior;
import game_engine.Layer;
import game_engine.Level;
import game_engine.MultipleBehaviors;
import game_engine.collision.Collision;
import game_engine.collision.CollisionEngine;
import game_engine.control.ControlManager;
import game_engine.control.KeyControl;
import game_engine.objective.Objective;
import game_engine.sprite.Sprite;
import game_engine.sprite.SpriteFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.input.KeyCode;


public class VoogaGameBuilder {

    private XMLParser parser;
    private Map<String, Sprite> mySpriteMap;
    private Map<String, Objective> myObjectiveMap;

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
        parser.moveDown(levelID);
        Level level = new Level();
        Layer sprites = new Layer();
        level.addLayer(sprites);

        parser.moveDown("sprite");
        for (String directory : parser.getValidSubDirectories()) {
            Sprite sprite = buildSprite(directory);
            sprites.addSprite(sprite);
        }
        parser.moveUp();

        level.setObjectives(buildObjectives());
        level.setCollisionEngine(buildCollisionEngine());

        parser.moveUp();
        return level;
    }

    private Sprite buildSprite (String spriteID) {
        parser.moveDown(spriteID);
        String spriteType = parser.getValue("type");
        SpriteFactory factory = new SpriteFactory();
        try {
            Sprite sprite = factory.createSprite(spriteType);
            parser.moveDown("animation");
            for (String directory : parser.getValidSubDirectories()) {
                parser.moveDown(directory);
                String name = parser.getValue("name");
                String image = parser.getValue("image");
                sprite.addImage(name, image);
                parser.moveUp();
            }
            parser.moveUp();

            String state = parser.getValue("initialState");
            sprite.setState(state);
            sprite.setPhysicsObject(buildPhysicsObject(engine));
            parser.moveUp();
            mySpriteMap.put(spriteID, sprite);
            return sprite;
        }
        catch (Exception e) {
            return null;
        }
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
        Collision collision = new Collision(spriteA, spriteB, behaviorList, null);
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
        IActor sprite = getActor(id);
        IAction behavior = sprite.getAction(name);
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
	
	private ControlManager buildControl(){
		parser.moveDown("control");
		ControlManager controlManager = new ControlManager();
		for(String controlDirectory: parser.getValidSubDirectories()){
			parser.moveDown(controlDirectory);
			KeyControl newControl = buildKeyControl();
			controlManager.addControl(buildKeyControl());
		}
		parser.moveUp();
		return controlManager;
	}
	
	private KeyControl buildKeyControl(){
		Map<KeyCode, IBehavior> pressedKeyMap = new HashMap<>();
		Map<KeyCode, IBehavior> releasedKeyMap = new HashMap<>();
		Map<KeyCode, IBehavior> heldKeyMap = new HashMap<>();
		
			for(String keyDirectory: parser.getValidSubDirectories()){
				parser.moveDown(keyDirectory);
				String keyName = parser.getValue("key");
				KeyCode keyCode = KeyCode.valueOf(keyName);
				
				parser.moveDown("onPressed");
				pressedKeyMap.put(keyCode, buildBehaviorList());
				parser.moveUp();
				
				parser.moveDown("onReleased");
                                releasedKeyMap.put(keyCode, buildBehaviorList());
                                parser.moveUp();
                                
                                parser.moveDown("whilePressed");
                                heldKeyMap.put(keyCode, buildBehaviorList());
                                parser.moveUp();
				
				parser.moveUp();
			}
		parser.moveUp();
		return new KeyControl(pressedKeyMap, releasedKeyMap, heldKeyMap);
	}
}
