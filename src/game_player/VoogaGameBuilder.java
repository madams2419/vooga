package game_player;

import game_engine.Layer;
import game_engine.Level;
import game_engine.physics.CircleBody;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Shape;
import game_engine.sprite.Sprite;
import game_engine.sprite.SpriteFactory;

public class VoogaGameBuilder {

	private XMLParser parser;
	//TODO: please god no
	private PhysicsEngine engine;

	public VoogaGameBuilder(XMLParser p) {
		parser = p;
	}

	public VoogaGame build() {
		VoogaGame game = new VoogaGame();

		parser.moveDown("game");
		game.setFPS(Double.parseDouble(parser.getValue("fps")));
		engine = buildPhysicsEngine(game.getFPS());
		parser.moveDown("level");
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
			sprite.setState(state);
			sprite.setPhysicsObject(buildPhysicsObject(engine));
			return sprite;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private PhysicsEngine buildPhysicsEngine(double fps) {
		//TODO first param will be removed
		PhysicsEngine globalEngine = new PhysicsEngine(0,fps); 
		return globalEngine;
	}

	private PhysicsObject buildPhysicsObject(PhysicsEngine engine) {
		parser.moveDown("physics");
		Shape tempShape = new CircleBody(15); // change this later
		Material material = Material.getMaterial(parser.getValue("material"));
		double startX = Double.parseDouble(parser.getValue("x"));
		double startY = Double.parseDouble(parser.getValue("y"));
		parser.moveUp();
		
		PhysicsObject physics = new PhysicsObject(engine, tempShape, material, startX, startY);
		return physics;
	}


}