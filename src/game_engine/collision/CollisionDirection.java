package game_engine.collision;

public enum CollisionDirection {
	Up(-45, 45),
	Down(45, 135),
	Left(135, 225),
	Right(225, 315);
	
	private double rangeMin;
	private double rangeMax;
	
	CollisionDirection(double min, double max) {
		rangeMin = min;
		rangeMax = max;
	}
	
	public boolean inRange(double direction) {
		direction %= 360;
		direction -= 45;
		return direction >= rangeMin && direction < rangeMax;
	}
}