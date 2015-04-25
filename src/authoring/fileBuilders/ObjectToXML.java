package authoring.fileBuilders;

import java.util.List;
import java.util.Map;

import authoring.dataEditors.Sprite;

public class ObjectToXML {

	public static Objective_XML[] mapToObjectives(Map<Integer, Map<String, List<String>>> objective){
		return new Objective_XML[0];
	}
	
	public static Sprite_XML[] spritesToXML(List<Sprite> sprites){
		return new Sprite_XML[0];
	}
	
	public static Collision_XML[] collisionToXML(){
		return new Collision_XML[0];
	}
	
}
