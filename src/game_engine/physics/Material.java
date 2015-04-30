package game_engine.physics;

public class Material {


	public static final Material ROCK = new Material(0.6, 0.1, 1.0, 0.8);
	public static final Material WOOD = new Material(0.3, 0.2, 0.3, 0.2);
	public static final Material METAL = new Material(1.2, 0.05, 0.74, 0.57);
	public static final Material BOUNCY_BALL = new Material(0.3, 0.8, 1.0, 0.8);
	public static final Material SUPER_BALL = new Material(0.3, 1.2, 0, 0);
	public static final Material PILLOW = new Material(0.1, 0.2, 1.0, 1.0);
	public static final Material STATIC = new Material(0.0, 0.0, 0, 0);
	public static final Material FLOATING = new Material(0.0, 0.2, 0.3, 0.3);
	public static final Material TRANSPARENT = new Material(0.0, -1.0, 0.0, 0.0);
	public static final Material TRAMPOLINE = new Material (0.0, 1, 0.0, 0.0);
	public static final Material PLATFORM = new Material (0.3, 1.5, 0.5, 0.5);

	private double myDensity;
	private double myRestitution;
	private double myStaticFriction;
	private double myKineticFriction;

	private Material(double density, double restitution,
			double staticFriction, double dynamicFriction) {
		myDensity = density;
		myRestitution = restitution;
		myStaticFriction = staticFriction;
		myKineticFriction = dynamicFriction;
	}

	public double getDensity() {
		return myDensity;
	}

	public double getRestitution() {
		return myRestitution;
	}

	public double getStaticFriction() {
		return myStaticFriction;
	}

	public double getKineticFriction() {
		return myKineticFriction;
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
	    case "TRAMPOLINE" : return TRAMPOLINE;
	    case "PLATFORM" : return PLATFORM;
	    default: return STATIC;
	    }
	}
}
