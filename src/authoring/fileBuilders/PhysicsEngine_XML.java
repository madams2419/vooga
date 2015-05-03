// This entire file is part of my masterpiece.
// Daniel Luker

package authoring.fileBuilders;

import org.w3c.dom.Element;


public class PhysicsEngine_XML extends Object_XML {

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

    @Override
    public void writeToXML (Element parent, int index, XMLBuilder xml) {
        xml.addChildWithValue(parent, TYPE, type);
        xml.addChildWithValue(parent, DRAG_COEFFICIENT, drag_coefficient);
        Element global = xml.add(parent, GLOBAL_ACCELERATIONS);
        for (int i = 0; i < global_accelerations.length; i++)
            xml.addChildWithValue(global, ACCEL + i, global_accelerations[i]);
    }

}
