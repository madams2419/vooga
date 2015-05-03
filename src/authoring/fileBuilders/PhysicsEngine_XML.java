package authoring.fileBuilders;

import org.w3c.dom.Element;


public class PhysicsEngine_XML {

    private static final String ACCEL = "accel_";
    private static final String GLOBAL_ACCELERATIONS = "global_accelerations";
    private static final String DRAG_COEFFICIENT = "drag_coefficient";
    private static final String TYPE = "type";
    private static final String DEFAULT_TYPE = "ComplexPhysicsEngine";
    private static final String DEFAULT_DRAG = "0.5";
    private static final String[] DEFAULT_ACCEL = { "0 -9.8" };
    private static final String GLOBAL_FORCES = "global_forces";
    private static final String DEFAULT_GLOBAL_FORCES = "";

    private String type;
    private String drag_coefficient;
    private String[] global_accelerations;

    public PhysicsEngine_XML (String type, String drag_coefficient,
                              String[] global_accelerations) {
        this.type = type;
        this.drag_coefficient = drag_coefficient;
        this.global_accelerations = global_accelerations;
    }

    public static PhysicsEngine_XML defaultPhysicsEngine () {
        return new PhysicsEngine_XML(DEFAULT_TYPE, DEFAULT_DRAG, DEFAULT_ACCEL);
    }

    public void writeToXML (Element parent, XMLBuilder xml) {
        xml.addChildWithValue(parent, TYPE, type);
        xml.addChildWithValue(parent, DRAG_COEFFICIENT, drag_coefficient);
        Element global = xml.add(parent, GLOBAL_ACCELERATIONS);
        for (int i = 0; i < global_accelerations.length; i++)
            xml.addChildWithValue(global, ACCEL + i, global_accelerations[i]);
        xml.addChildWithValue(parent, GLOBAL_FORCES, DEFAULT_GLOBAL_FORCES);
    }

}
