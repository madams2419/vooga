package game_engine;

import java.util.List;

public class Layer {
	
	List<Sprite> sprites;
	
	public Layer() {
		// TODO
	}
	
	public void update() {
		sprites.forEach(sprite -> sprite.update());
	}
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	public List<Sprite> getSprites() {
		return sprites;
	}
}
