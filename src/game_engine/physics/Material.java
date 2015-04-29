package game_engine.physics;

public class Material {

	public static final Material ROCK = new Material(0.6, 0.1);
	public static final Material WOOD = new Material(0.3, 0.2);
	public static final Material METAL = new Material(1.2, 0.05);
	public static final Material BOUNCY_BALL = new Material(0.3, 0.8);
	public static final Material SUPER_BALL = new Material(0.3, 0.95);
	public static final Material PILLOW = new Material(0.1, 0.2);
	public static final Material STATIC = new Material(0.0, 0.0);
	public static final Material FLOATING = new Material(0.0, 0.2);
	public static final Material TRANSPARENT = new Material(0.0, -1.0); 

	private double myDensity;
	private double myRestitution;

	private Material(double density, double restitution) {
		myDensity = density;
		myRestitution = restitution;
	}

	public double getDensity() {
		return myDensity;
	}

	public double getRestitution() {
		return myRestitution;
	}
	
	public static Material valueOf(String name) {
	    switch(name) {
	    case "ROCK": return ROCK;
	    case "WOOD": return WOOD;
	    case "METAL": return METAL;
	    case "BOUNCY_BALL": return BOUNCY_BALL;
	    case "SUPER_BALL": return SUPER_BALL;
	    case "PILLOW": return PILLOW;
	    case "STATIC": return STATIC;
	    case "FLOATING": return FLOATING;
	    case "TRANSPARENT" : return TRANSPARENT;
	    default: return STATIC;
	    }
	}
}
