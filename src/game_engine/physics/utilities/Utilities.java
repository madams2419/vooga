package game_engine.physics.utilities;

public class Utilities {

	/**
	 * Clamp value within range specified by [min, max]
	 * @param min
	 * @param max
	 * @param value
	 * @return
	 */
	public static double clamp(double min, double max, double value) {
		if(value < min) {
			return min;
		} else if(value > max) {
			return max;
		} else {
			return value;
		}
	}

	public static double solvePythagorean(double aVal, double bVal) {
		return Math.sqrt(aVal * aVal + bVal * bVal);
	}

}
