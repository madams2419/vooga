package authoring.fileBuilders;

import org.w3c.dom.Element;

public class Level_XML {

	private Objective_XML[] level_objectives;
	private Sprite_XML[] sprites;
	private PhysicsEngine_XML physics_engine;
	private Control_XML controls;
	private Collision_XML[] collisions;

	public Level_XML(Objective_XML[] level_objectives, Sprite_XML[] sprites,
			PhysicsEngine_XML physics_engine, Control_XML controls,
			Collision_XML[] collisions) {
		super();
		this.level_objectives = level_objectives;
		this.sprites = sprites;
		this.physics_engine = physics_engine;
		this.controls = controls;
		this.collisions = collisions;
	}
	
	public void writeToXML(Element root, int index, XMLBuilder xml) {
		Element currentLevel = xml.add(root, "level_"+index);
		
		int i = 0;
		
		// Adding objectives
		Element levels = xml.add(currentLevel, "level_objectives");
		for(Objective_XML objective : level_objectives){
			objective.writeToXML(levels, i++, xml);
		}
		
		i = 0;
		
		// Adding sprites
		Element sprite = xml.add(currentLevel, "sprites");
		for(Sprite_XML sp : sprites){
			sp.writeToXML(sprite, xml);
		}
		
		i = 0;
		
		// Adding physics engine
		Element physicsEngine = xml.add(currentLevel, "physics_engine");
		physics_engine.writeToXML(physicsEngine, xml);
		
		// Adding controls
		Element control = xml.add(currentLevel, "controls");
		controls.writeToXML(control, xml);
		
		// Adding collisions
		Element collision = xml.add(currentLevel, "collisions");
		for(Collision_XML c : collisions) {
			c.writeToXML(collision, i++, xml);
		}
		
	}

}
