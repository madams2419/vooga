package game_engine.collisions.detectors;

import java.util.ArrayList;
import java.util.List;

import game_engine.sprite.Sprite;

/**
 * This should be the most commonly used detector since it allows for the
 * accuracy of pixel perfect collisions but offers high levels of optimization
 * by only checking in close cases. This is accomplished by prioritizing the
 * checking of other faster detectors first. If they fail, then the algorithm
 * just returns false and doesn't run the more costly detections.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class MultipleDetectors implements ICollisionDetector {

	private List<ICollisionDetector> detectors;

	/**
	 * Creates a new MultipleDetectors, must add the detectors.
	 */
	public MultipleDetectors() {
		detectors = new ArrayList<>();
	}

	/**
	 * Creates a new MultipleDetectors with the detectors already specified.
	 * 
	 * @param list
	 *            list is a list of pre-determined detectors to be used.
	 */
	public MultipleDetectors(List<ICollisionDetector> list) {
		detectors = list;
	}

	public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
		for (ICollisionDetector detector : detectors) {
			if (!detector.detectCollision(spriteA, spriteB)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds a detector to the list, assumes lowest priority.
	 * 
	 * @param newDetector
	 *            newDetector is the added detector to be checked.
	 */
	public void addDetector(ICollisionDetector newDetector) {
		detectors.add(newDetector);
	}

	/**
	 * Adds a new detector with the specified priority to allow adding out of
	 * order.
	 * 
	 * @param newDetector
	 *            newDetector is the added detector to be checked.
	 * 
	 * @param priority
	 *            priority determines in what order this detector will be
	 *            checked compared to the other ones.
	 */
	public void addDetector(ICollisionDetector newDetector, int priority) {
		detectors.add(priority, newDetector);
	}
}