package authoring.fileBuilders;

import org.w3c.dom.Element;

public class Collision_XML {

	private String sprites;
	private String[] detectors;
	private Resolver[] resolvers;

	public void writeToXML(Element parent, int index, XMLBuilder xml) {
		Element current = xml.add(parent, "collision_" + index);
		xml.addChildWithValue(current, "sprites", sprites);
		Element det = xml.add(current, "detectors");
		for (int i = 0; i < detectors.length; i++)
			xml.addChildWithValue(det, "detector_" + i, detectors[i]);
		Element res = xml.add(current, "resolvers");
		for (int i = 0; i < resolvers.length; i++)
			resolvers[i].writeToXML(res, i, xml);
	}

	private class Resolver {
		private String type;
		private Behaviours_XML[] behaviours;
		private Resolver(String type, Behaviours_XML[] behaviours) {
			this.type = type;
			this.behaviours = behaviours;
		}
		public void writeToXML(Element parent, int index, XMLBuilder xml){
			Element current = xml.add(parent, "resolver_"+index);
			xml.addChildWithValue(current, "type", type);
			Element beh = xml.add(current, "behaviours");
			for(int i = 0; i < behaviours.length; i++)
				behaviours[i].writeToXML(beh, i, xml);
		}
	}

}
