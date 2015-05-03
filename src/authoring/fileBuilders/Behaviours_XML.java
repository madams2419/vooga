// This entire file is part of my masterpiece.
// Daniel Luker

package authoring.fileBuilders;

import java.util.Arrays;
import org.w3c.dom.Element;


public class Behaviours_XML extends Object_XML {

    private String targetType;
    private String targetIndex;
    private String name;
    private String[] parameters;

    public Behaviours_XML (String targetType,
                           String targetIndex,
                           String name,
                           String ... parameters) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.name = name;
        this.parameters = parameters;
    }

    public Behaviours_XML (String targetType, String targetIndex, String name, String parameters) {
        this(targetType, targetIndex, name, parameters.split(SPACE));
    }

    public Behaviours_XML (String ... params) {
        if (params.length == 4) {
            this.targetType = params[0];
            this.targetIndex = params[1];
            this.name = params[2];
            this.parameters = Arrays.copyOfRange(params, 3, 4);
        }
    }

    @Override
    public void writeToXML (Element parent, int index, XMLBuilder xml) {
        Element currentBehaviour = xml.add(parent, BEHAVIOR + index);
        xml.addChildWithValue(currentBehaviour, TARGET_TYPE, targetType);
        xml.addChildWithValue(currentBehaviour, TARGET_INDEX, targetIndex);
        xml.addChildWithValue(currentBehaviour, NAME, name);
        StringBuilder sb = new StringBuilder();
        Arrays.asList(parameters).forEach(s -> sb.append(s + SPACE));
        xml.addChildWithValue(currentBehaviour, PARAMETERS, sb.toString());
    }

}
