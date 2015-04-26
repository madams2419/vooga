package authoring.fileBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import authoring.dataEditors.Sprite;
import authoring.util.FrontEndUtils;

public class Collision_XML {

	private String sprites;
	private String[] detectors;
	private List<Resolver> resolvers = new ArrayList<>();

	public Collision_XML(Sprite a, Sprite b, List<String> interactions) {
		System.err.println(interactions.toString());
		sprites = FrontEndUtils.getSpritesIDSorted(a, b);
		parseInteractions(interactions);
	}

	private void parseInteractions(List<String> interactions) {
		interactions.forEach(str -> {
			String[] params = str.split(":");
			if (params[0].equals("SimpleResolver")) 
				resolvers.add(new Resolver(params[0], new Behaviours_XML(Arrays
						.copyOfRange(params, 1, params.length))));
			 else if (params[0].equals("PhysicsResolver"))
				 resolvers.add(new Resolver(params[0]));
		});
	}

	public void writeToXML(Element parent, int index, XMLBuilder xml) {
		Element current = xml.add(parent, "collision_" + index);
		xml.addChildWithValue(current, "sprites", sprites);
		Element det = xml.add(current, "detectors");
		for (int i = 0; i < detectors.length; i++)
			xml.addChildWithValue(det, "detector_" + i, detectors[i]);
		Element res = xml.add(current, "resolvers");
		for (int i = 0; i < resolvers.size(); i++)
			resolvers.get(i).writeToXML(res, i, xml);
	}

	private class Resolver {
		private String type;
		private Behaviours_XML[] behaviours;

		private Resolver(String type, Behaviours_XML... behaviours) {
			this.type = type;
			this.behaviours = behaviours;
		}

		public void writeToXML(Element parent, int index, XMLBuilder xml) {
			Element current = xml.add(parent, "resolver_" + index);
			xml.addChildWithValue(current, "type", type);
			Element beh = xml.add(current, "behaviours");
			for (int i = 0; i < behaviours.length; i++)
				behaviours[i].writeToXML(beh, i, xml);
		}
	}

}
