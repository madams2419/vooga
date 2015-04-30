package authoring.fileBuilders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import authoring.dataEditors.Sprite;
import authoring.panes.centerPane.CenterCanvas;

public class ObjectToXML {

	public static Objective_XML[] mapToObjectives(Map<Integer, Map<String, List<String>>> objective){
		return new Objective_XML[0];
	}
	
	public static List<Sprite_XML> spritesToXML(List<Sprite> sprites, double width, double height){
		return sprites.stream().map(sprite -> {return new Sprite_XML(sprite, width, height);}).collect(Collectors.toList());
	}
	
	public static Collision_XML[] collisionToXML(CenterCanvas level){
	    List<Collision_XML> list = level.getCollisions();
	    return list.toArray(new Collision_XML[0]);
	}
	
}
