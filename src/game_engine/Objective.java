package game_engine;

import java.util.List;

import java.util.Observable;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.beans.property.BooleanProperty;

/**
 * @author Tony Goal that leads to a specific action when completed
 */
public class Objective {

	List<Objective> subObjectives;
	boolean condition;

	/**
	 * /** Constructor
	 * 
	 * @param condition
	 *            BooleanProperty Represents a condition for the objective
	 *            completion
	 * @param subObjectives
	 *            List<Objective> List of objectives that must be completed
	 *            first
	 * @param action
	 *            Consumer<?> Action that occurs when the objective is complete
	 */
	public Objective(BooleanProperty condition, List<Objective> subObjectives,
			Consumer<?> action) {
		// TODO
	}

	/***
	 * Empty constructor for mockObjective in usecases
	 */
	public Objective() {

	}

	/**
	 * Method setCondition.
	 * 
	 * @param condition
	 *            BooleanProperty
	 */
	public void setCondition(BooleanProperty condition) {
		// TODO
	}

	/**
	 * Method setSubObjectivies.
	 * 
	 * @param subObjectives
	 *            List<Objective>
	 */
	public void setSubObjectivies(List<Objective> subObjectives) {
		// TODO
	}

	/**
	 * Method addSubOjective
	 * 
	 * @param objective
	 *            Objective
	 */
	public void addSubObjective(Objective objective) {

	}

	/**
	 * Method removeSubObjective Removes a subobjective if present else do
	 * nothing
	 * 
	 * @param objective
	 *            Objective
	 */
	public void removeSubObjective(Objective objective) {

	}

	/**
	 * Method addParent Adds a parent objective. The parent objective has this
	 * objective object as a subobjective.
	 */
	public void addParent(Objective parent) {

	}

	/**
	 * Method start Start working towards completion of the objective.
	 */
	public void start() {
		// TODO
	}

	/**
	 * method onComplete On completion of an objective, perform action
	 */
	public void onComplete() {
		// TODO
	}

	/***
	 * To check whether this objective has been completed
	 * 
	 * @return True iff the objective has been completed
	 */
	public boolean isComplete() {
		// TODO
		return false;
	}

	public <T extends Observable> void setPredicate(Predicate<T> condition) {
		// TODO
	}

}
