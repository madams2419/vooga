package game_engine.physics;

public interface Shape {
	
	public double getVolume();
	
	/* return radius bounding circle in pixels */
	public double getRadiusPixels();
	
	/* return radius bounding circle in meters */
	public double getRadiusMeters();
	
	/* return cross sectional area in meters */
	public double getCxArea();

}
