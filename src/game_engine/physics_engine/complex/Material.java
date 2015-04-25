package game_engine.physics_engine.complex;

public enum Material {
	
        ROCK (0.6, 0.1),
        WOOD (0.3, 0.2),
        METAL (1.2, 0.05),
        BOUNCY_BALL (0.3, 0.8),
        SUPER_BALL (0.3, 0.95),
        PILLOW (0.1, 0.2),
        STATIC (0, 0);
	

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
}
