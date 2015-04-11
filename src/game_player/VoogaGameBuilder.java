package game_player;

import game_engine.Layer;
import game_engine.Level;
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
}