// This entire file is part of my masterpiece.
// Brian Lavallee

package game_engine.hitboxes;

import java.util.List;
import java.util.function.Supplier;

import javafx.util.Pair;
import game_engine.physics.Vector;

/**
 * An IHitbox serves as the major bridge between collisions and the physics
 * engine. Collisions use IHitboxes both as a optimization tool for pixel
 * perfect calculations in addition to accurately resolving collisions between
 * two sprites based on relative positions. The physics engine uses the IHitbox
 * to calculate mass and to realistically resolve collisions, should that
 * behavior be part of the collision's resolution behavior.
 * 
 * @author Brian Lavallee
 * @since 23 April 2015
 */
public interface IHitbox {

	/**
	 * Determines whether or not two hitboxes are intersecting by checking if
	 * any of the points in the other hitbox are contained in this hitbox using
	 * the containsPoint() method. Used for collision detection.
	 * 
	 * @param other
	 *            other is one of the two hitboxes involved in the potential
	 *            collision.
	 * 
	 * @return true if the two hitboxes overlap at all and false otherwise.
	 */
	public boolean intersects(IHitbox other);

	/**
	 * Provides access to the pairs of hitboxes that collided. These pairs are
	 * calculated by intersects().
	 * 
	 * @return the List of pairs.
	 */
	public List<Pair<Integer, Integer>> getCollisionPairs();

	/**
	 * Calculates the area of a hitbox. MultipleHitboxes do this via a sum of
	 * component areas. SingleHitboxes calculate recursively by slowly
	 * simplifying the shape into a series of triangles.
	 * 
	 * @return a signed double representing the area of the hitbox. Positive if
	 *         points are ordered clockwise and negative otherwise.
	 */
	public double getArea();

	/**
	 * Determines if a point is contained within an IHitbox. Calculated by
	 * counting the number of lines a horizontal line passing through the point
	 * would collide with.
	 * 
	 * @param point
	 *            is a Vector representing the x and y coordinates of the point.
	 * 
	 * @return true if the point is contained (an odd number of crossings) and
	 *         false otherwise.
	 */
	public boolean containsPoint(Vector point);

	/**
	 * Provides access to an ordered list of points that make up a polygon.
	 * 
	 * @return a list of Vectors representing the points.
	 */
	public List<Vector> getPoints();

	/**
	 * Finds the position of the parent PhysicsObject. Useful since all of the
	 * points of the hitbox are stored as relative to the PhysicsObject.
	 * 
	 * @return a Vector representing the x and y coordinates of the parent
	 *         PhysicsObject's position.
	 */
	public Vector getPosition();

	/**
	 * Provides access to a list of all of the component hitboxes.
	 * SingleHitboxes only have one, but MultipleHitboxes may be comprised of
	 * many.
	 * 
	 * @return a list of SingleHitboxes (as opposed to a list of IHitboxes,
	 *         allowing for multiple multiples).
	 */
	public List<SingleHitbox> getComponents();
	
	/**
	 * Adds a position supplier so that the hitbox can calculate actual as opposed
	 * to relative positions.
	 * 
	 * @param position
	 * 			a Supplier of Vector positions.
	 */
	public void addPositionSupplier(Supplier<Vector> position);
	
	/**
	 * Provides a rectangle that bounds the hitbox
	 * 
	 * @return an array of two Vectors defining the corners of the bounding box
	 */
	public List<Vector> getBoundingBox();
}