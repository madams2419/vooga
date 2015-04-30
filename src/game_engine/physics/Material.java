package game_engine.physics;

import java.lang.reflect.Field;

public class Material {


	public static final Material ROCK = new Material(0.6, 0.1, 1.0, 0.8);
	public static final Material WOOD = new Material(0.3, 0.2, 0.3, 0.2);
	public static final Material METAL = new Material(1.2, 0.05, 0.74, 0.57);
	public static final Material BOUNCY_BALL = new Material(0.3, 0.8, 1.0, 0.8);
	public static final Material SUPER_BALL = new Material(0.3, 1.2, 0, 0);
	public static final Material FLOATING = new Material(0.0, 0.2, 0.3, 0.3);
	public static final Material PLATFORMA = new Material (0.3, 1.5, 0.5, 0.5);
	public static final Material BOUNCY_BALLA = new Material(0.3, 1.2, 1.0, 0.8);
	public static final Material SUPER_BALLA = new Material(0.3, 0.95, 0.0, 0.0);
	public static final Material HUMAN = new Material(0.3,0,1.0,0.8);
	public static final Material INSANE_BOUNCY_BALL = new Material(0.01, 1.0, 0.0, 0.0);
	public static final Material PILLOW = new Material(0.1, 0.2, 1.0, 1.0);
	public static final Material STATIC = new Material(0.0, 0.0, 0.0, 0.0);
	public static final Material FLOATINGA = new Material(0.0, 1.0, 0.0, 0.0);
	public static final Material TRANSPARENT = new Material(0, -1.0, 0.0, 0.0);
	
	public static final Material TRAMPOLINE = new Material(0, 1.0, 0.0, 0.0);
	public static final Material PLATFORM = new Material(0.3, 1.5, 1.0, 0.5);


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

		Class<Material> matClass = Material.class;
	    Field myField;
		try {
			myField = matClass.getDeclaredField(name);
			return (Material) myField.get(null);
		} catch (Exception e) {
			System.out.println("Undefined material: " + name);
		}
		return null;
	}
}
