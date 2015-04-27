package authoring.fileBuilders;

import org.w3c.dom.Element;

public class PhysicsEngine_XML {
	
	private static final String DEFAULT_TYPE = "ComplexPhysicsEngine";
	private static final String DEFAULT_DRAG = "0.2";
	private static final String[] DEFAULT_ACCEL = {"0 0"};

	private String type;
	private String drag_coefficient;
	private String[] global_accelerations;
	
	public PhysicsEngine_XML(String type, String drag_coefficient,
			String[] global_accelerations) {
		this.type = type;
		this.drag_coefficient = drag_coefficient;
		this.global_accelerations = global_accelerations;
	}

	public static PhysicsEngine_XML defaultPhysicsEngine() {
		return new PhysicsEngine_XML(DEFAULT_TYPE, DEFAULT_DRAG, DEFAULT_ACCEL);
	}
	
	public void writeToXML(Element parent, XMLBuilder xml) {
		xml.addChildWithValue(parent, "type", type);
		xml.addChildWithValue(parent, "drag_coefficient", drag_coefficient);
		Element global = xml.add(parent, "global_accelerations");
		for(int i = 0; i < global_accelerations.length; i++)
			xml.addChildWithValue(global, "accel_"+i, global_accelerations[i]);
	}

}
