package game_engine.physics.utilities;


public class Scaler {
	
	private double myScaleFactor;
	
	public Scaler(double scaleFactor) {
		myScaleFactor = scaleFactor;
	}

	public double pixelsToMeters(double pixels) {
		return pixels * myScaleFactor;
	}

	public double metersToPixels(double meters) {
		return meters / myScaleFactor;
	}

	public Vector vectorPixelsToMeters(Vector vectorPixels) {
		return vectorPixelsToMeters(vectorPixels.getX(), vectorPixels.getY());
	}

	public Vector vectorPixelsToMeters(double xPixels, double yPixels) {
		return new Vector(pixelsToMeters(xPixels), pixelsToMeters(yPixels));
	}

	public Vector vectorMetersToPixels(Vector vector) {
		return vectorMetersToPixels(vector.getX(), vector.getY());
	}

	public Vector vectorMetersToPixels(double xMeters, double yMeters) {
		return new Vector(metersToPixels(xMeters), metersToPixels(yMeters));
	}
	
}
