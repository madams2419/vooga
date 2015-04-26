package authoring.fileBuilders;

import org.w3c.dom.Element;

public class PhysicsEngine_XML {

	private String type;
	private String drag_coefficient;
	private String[] global_accelerations;
	
	public PhysicsEngine_XML(String type, String drag_coefficient,
			String[] global_accelerations) {
		this.type = type;
		this.drag_coefficient = drag_coefficient;
		this.global_accelerations = global_accelerations;
	}

	public void writeToXML(Element parent, XMLBuilder xml) {
		Element physicsEngine = xml.add(parent, "physics_engine");
		xml.addChildWithValue(physicsEngine, "type", type);
		xml.addChildWithValue(physicsEngine, "drag_coefficient", drag_coefficient);
		Element global = xml.add(physicsEngine, "global_accelerations");
		for(int i = 0; i < global_accelerations.length; i++)
			xml.addChildWithValue(global, "accel_"+i, global_accelerations[i]);
	}

}
