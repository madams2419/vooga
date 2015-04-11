package game_player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;
import game_engine.Behavior;
import game_engine.Layer;
import game_engine.Level;
import game_engine.control.ControlManager;
import game_engine.control.KeyControl;
import game_engine.sprite.Sprite;
import game_engine.sprite.SpriteFactory;

public class VoogaGameBuilder {

	private XMLParser parser;

	public VoogaGameBuilder(XMLParser p) {
		parser = p;
	}

	public VoogaGame build() {
		VoogaGame game = new VoogaGame();

		parser.moveDown("game/level");
		for (String directory : parser.getValidSubDirectories()) {
			parser.moveDown(directory);
			Level level = buildLevel();
			game.addLevel(level);
			parser.moveUp();
		}
		game.setActiveLevel(Integer.parseInt(parser.getValue("start")));

		return game;
	}

	private Level buildLevel() {
		Level level = new Level();
		Layer sprites = new Layer();
		level.addLayer(sprites);

		parser.moveDown("sprite");

		for (String directory : parser.getValidSubDirectories()) {
			parser.moveDown(directory);
			Sprite sprite = buildSprite();
			sprites.addSprite(sprite);
			parser.moveUp();
		}

		parser.moveUp();

		return level;
	}

	private Sprite buildSprite() {
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
			sprite.setState.execute(state);
			return sprite;
		}
		catch (Exception e) {
			return null;
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
		Map<KeyCode, List<Behavior>> pressedKeyMap = new HashMap<>();
		Map<KeyCode, List<Behavior>> releasedKeyMap = new HashMap<>();
		Map<KeyCode, List<Behavior>> heldKeyMap = new HashMap<>();
		
			for(String keyDirectory: parser.getValidSubDirectories()){
				parser.moveDown(keyDirectory);
				String keyName = parser.getValue("key");
				KeyCode keyCode = KeyCode.valueOf(keyName);
				
				addBehaviorList(keyCode, pressedKeyMap, "onPressed");
				addBehaviorList(keyCode, releasedKeyMap, "onReleased");
				addBehaviorList(keyCode, heldKeyMap, "whilePressed");
				
				parser.moveUp();
			}
		parser.moveUp();
		return new KeyControl(pressedKeyMap, releasedKeyMap, heldKeyMap);
	}
	
	private void addBehaviorList(KeyCode keyCode, Map<KeyCode, List<Behavior>> map, String keyType){
		List<Behavior> behaviorList = getBehaviorList(keyType);
		map.put(keyCode, behaviorList);
	}
	
	private List<Behavior> getBehaviorList(String keyType){
//		parser.moveDown("keyType");
//		return buildBehavior();
		
		//swtich back to buildBehavor upon availability
		return null;
	}
}