package game_engine.sprite;

import com.sun.javafx.scene.traversal.Direction;

public interface ICollectible {
	// constants
	
	// method signatures
	
	double turn(Direction direction,
			double distance);
	
	double hover(double distance);
	
	double rotate(Direction direction,
			double speed);
	
	
}
