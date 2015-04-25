package authoring.fileBuilders;

import org.w3c.dom.Element;

public class Sprite_XML {

	private String inital_state;
	private State[] animation;
	private Physics physics;
	private int index;
	
	public void writeToXML(Element parent, XMLBuilder xml) {
		Element current = xml.add(parent, "sprite_"+index);
		xml.addChildWithValue(current, "initial_state", inital_state);
		for(int i = 0; i < animation.length; i++)
			animation[i].writeToXML(current, i, xml);
		physics.writeToXML(current, xml);
	}

	private class State {
		private String name;
		private Image[] images;
		private State(String name, Image[] images) {
			this.name = name;
			this.images = images;
		} 
		public void writeToXML(Element parent, int index, XMLBuilder xml){
			Element current = xml.add(parent, "state_"+index);
			xml.addChildWithValue(current, "name", name);
			for(int i = 0; i < images.length; i++)
				images[i].writeToXML(current, i, xml);
		}
	}
	
	private class Image {
		private String source;
		private String delay;
		private String width;
		private String height;
		private Hitbox[] hitboxes;
		private Image(String source, String delay, String width, String height,
				Hitbox[] hitboxes) {
			this.source = source;
			this.delay = delay;
			this.width = width;
			this.height = height;
			this.hitboxes = hitboxes;
		}
		public void writeToXML(Element parent, int index, XMLBuilder xml){
			Element current = xml.add(parent, "image_"+index);
			xml.addChildWithValue(current, "source", source);
			xml.addChildWithValue(current, "delay", delay);
			xml.addChildWithValue(current, "width", width);
			xml.addChildWithValue(current, "height", height);
			for(int i = 0; i < hitboxes.length; i++)
				hitboxes[i].writeToXML(current, i, xml);
		}
	}
	
	private class Hitbox {
		private String point_0;
		private String point_1;
		private String point_2;
		private String point_3;
		private Hitbox(String point_0, String point_1, String point_2,
				String point_3) {
			this.point_0 = point_0;
			this.point_1 = point_1;
			this.point_2 = point_2;
			this.point_3 = point_3;
		}
		public void writeToXML(Element parent, int index, XMLBuilder xml){
			Element current = xml.add(parent, "hitbox_"+index);
			xml.addChildWithValue(current, "point_0", point_0);
			xml.addChildWithValue(current, "point_1", point_1);
			xml.addChildWithValue(current, "point_2", point_2);
			xml.addChildWithValue(current, "point_3", point_3);
		}
	}
	
	private class Physics {
		private String type;
		private String material;
		private String position;
		private Physics(String type, String material, String position) {
			this.type = type;
			this.material = material;
			this.position = position;
		}
		public void writeToXML(Element parent, XMLBuilder xml) {
			Element physics = xml.add(parent, "physics");
			xml.addChildWithValue(physics, "type", type);
			xml.addChildWithValue(physics, "material", material);
			xml.addChildWithValue(physics, "position", position);
		}
	}
}
