package authoring.fileBuilders;

import java.util.Arrays;

import org.w3c.dom.Element;

public class Behaviours_XML {
	
	private String targetType;
	private String targetIndex;
	private String name;
	private String[] parameters;
	
	public Behaviours_XML(String targetType, String targetIndex, String name, String... parameters){
		this.targetType = targetType;
		this.targetIndex = targetIndex;
		this.name = name;
		this.parameters = parameters;
	}
	
	public void writeToXML(Element parent, int index, XMLBuilder xml){
		Element currentBehaviour = xml.add(parent, "behaviour_"+index);
		xml.addChildWithValue(currentBehaviour, "targetType", targetType);
		xml.addChildWithValue(currentBehaviour, "targetIndex", targetIndex);
		xml.addChildWithValue(currentBehaviour, "name", name);
		StringBuilder sb = new StringBuilder();
		Arrays.asList(parameters).forEach(s -> sb.append(s + " "));
		xml.addChildWithValue(currentBehaviour, "parameters", sb.toString());
	}

}
