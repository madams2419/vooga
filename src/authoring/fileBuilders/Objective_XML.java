package authoring.fileBuilders;

import java.util.Arrays;

import org.w3c.dom.Element;

public class Objective_XML {
	
	private String description;
	private int[] prereqs;
	private Behaviours_XML[] onComplete;
	private Behaviours_XML[] onFailed;
	
	public Objective_XML(String description, int[] prereqs,
			Behaviours_XML[] onComplete, Behaviours_XML[] onFailed) {
		this.description = description;
		this.prereqs = prereqs;
		this.onComplete = onComplete;
		this.onFailed = onFailed;
	}
	
	public void writeToXML(Element parent, int index, XMLBuilder xml){
		Element thisObjective = xml.add(parent, "objective_"+index);
		xml.addChildWithValue(thisObjective, "description", description);
		StringBuilder sb = new StringBuilder();
		Arrays.asList(prereqs).forEach(s -> sb.append(s+" "));
		xml.addChildWithValue(thisObjective, "prereqs", sb.toString());
		addBehaviours(thisObjective, "onComplete", onComplete, xml);
		addBehaviours(thisObjective, "onFailed", onFailed, xml);
	}
	
	private void addBehaviours(Element currentObjective, String tagname, Behaviours_XML[] behaviours, XMLBuilder xml) {
		Element el = xml.add(currentObjective, tagname);
		Element b = xml.add(el, "behaviours");
		int i = 0;
		for(Behaviours_XML be : behaviours)
			be.writeToXML(b, i++, xml);
	}

}
