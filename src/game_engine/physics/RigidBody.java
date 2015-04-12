package game_engine.physics;

public abstract class RigidBody {
	
	//TODO move somewhere else...constants or properties file
	private static final double DEFAULT_DEPTH = 5;

	protected double myDepth;
	
    public enum RBodyType {
    	CIRCLE, RECTANGLE
    }
    
    public RigidBody() {
    	myDepth = DEFAULT_DEPTH;
    }

	public RigidBody(double depth) {
		myDepth = depth;
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();

	public abstract boolean containsPoint(Vector point);

	public double getVolume() {
		return myDepth * getArea();
	}

	public double getCxArea() {
		return myDepth * getCxLength();
	}

}
