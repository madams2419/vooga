package game_engine.physics;

public class Material {

	private double myDensity;
	private double myRestitution;

	public Material(double density, double restitution) {
		myDensity = density;
		myRestitution = restitution;
	}

	public double getDensity() {
		return myDensity;
	}

	public void setDensity(double density) {
		myDensity = density;
	}

	public double getRestitution() {
		return myRestitution;
	}

	public void setRestitution(double restitution) {
		myRestitution = restitution;
	}

}
