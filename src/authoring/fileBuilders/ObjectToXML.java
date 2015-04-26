package authoring.fileBuilders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.dataEditors.Sprite;

public class ObjectToXML {

	public static Objective_XML[] mapToObjectives(Map<Integer, Map<String, List<String>>> objective){
		return new Objective_XML[0];
	}
	
	public static List<Sprite_XML> spritesToXML(List<Sprite> sprites){
		return sprites.stream().map(sprite -> {return new Sprite_XML(sprite);}).collect(Collectors.toList());
	}
	
	public static Collision_XML[] collisionToXML(){
		return new Collision_XML[0];
	}
	
}
