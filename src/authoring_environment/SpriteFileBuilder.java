package authoring_environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

/***
 * Class to generate sprite.xml files
 * 
 * @author Daniel Luker
 *
 */
class SpriteFileBuilder extends XMLBuilder {

	// TODO javadoc this badboy
	
	List<Element> mSprites = new ArrayList<>();

	SpriteFileBuilder(String rootElement, String... attributes_values) {
		super(rootElement, attributes_values);
	}

	void addSprite(Sprite newSprite) {
		Element e = super.createElement(newSprite.name);
		e.setAttribute("id", ""+newSprite.id);
		addChildWithProperty(e, "health", "" + newSprite.health);
		addChildWithProperty(e, "imageuri", newSprite.imageuri);
		addChildWithProperty(e, "speed", "" + newSprite.speed);
		addChildWithProperty(e, "gravity", "" + newSprite.mGravity);
		addChildProperties(e, "key-actions", newSprite.key_actions);
		addChildProperties(e, "position", newSprite.position);
		this.addToRoot(e);
	}

	void addProperties(Element element, Map<Object, Object> properties) {
		properties.forEach((e1, e2) -> addChildWithProperty(element, e1.toString(), e2.toString()));
	}

	
	/***
	*
	*TESTING ++=++=++=++=++=
	*
	*/
	
	public static void main(String[] args){
		SpriteFileBuilder b = new SpriteFileBuilder("sprites", "game", "mario", "xmlns:res", "swap/game.xml");
		
		Sprite s = new Sprite(0);
		{	// Creating a sprite object, and defining its properties. This will be all done graphically
		s.health = 1;
		s.imageuri = "null";
		s.mGravity = Sprite.GRAVITY.DOWN;
		s.name = "mario";
		s.position.put("X", "10.0");
		s.position.put("Y", "10.0");
		s.speed = 10;
		s.key_actions.put(Sprite.ACTIONS.UP.name(), "jump");
		s.key_actions.put(Sprite.ACTIONS.RIGHT.name(), "run");
		}
		
		Sprite t = new Sprite(1);
		{	// Creating a sprite object, and defining its properties. This will be all done graphically
		t.health = 1;
		t.imageuri = "res/images/koopa.jpeg";
		t.mGravity = Sprite.GRAVITY.DOWN;
		t.name = "koopa";
		t.position.put("X", "20.0");
		t.position.put("Y", "30.0");
		t.speed = 5;
		}
		
		
		
		b.addSprite(s);
		b.addSprite(t);
		
		b.streamFile("swap/sprites.xml", b.getRoot());
		
	}	
}