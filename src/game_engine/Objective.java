package game_engine;

import java.util.List;
<<<<<<< HEAD
import java.util.function.Consumer;
import javafx.beans.property.BooleanProperty;

/**@author Tony
 * Goal that leads to a specific action when completed
 */
public class Objective {
	
        /**
         * Constructor
         * @param condition BooleanProperty Represents a condition for the objective completion
         * @param subObjectives List<Objective> List of objectives that must be completed first
         * @param action Consumer<?> Action that occurs when the objective is complete
         */
	public Objective(BooleanProperty condition, List<Objective> subObjectives, Consumer<?> action) {
		// TODO
	}
	
	/**
	 * Method setCondition.
	 * @param condition BooleanProperty
	 */
	public void setCondition(BooleanProperty condition) {
		// TODO
	}
	
	/**
	 * Method setSubObjectivies.
	 * @param subObjectives List<Objective>
	 */
	public void setSubObjectivies(List<Objective> subObjectives) {
		// TODO
	}
	
	/**
	 * Method addSubOjective
	 * @param objective Objective
	 */
	public void addSubObjective (Objective objective){
	    
	}
	
	/**
	 * Method removeSubObjective
	 * Removes a subobjective if present else do nothing
	 * @param objective Objective
	 */
	public void removeSubObjective (Objective objective){
	    
	}
	
	/**
	 * Method addParent
	 * Adds a parent objective. The parent objective has this objective object as a subobjective.
	 */
	public void addParent(Objective parent){
	    
	}
	
	/**
	 * Method start
	 * Start working towards completion of the objective.
	 */
	public void start() {
		// TODO
	}
=======
import java.util.Observable;
import java.util.function.Predicate;
/**
 * Objective class that defines objectives and their behaviors
 * @author Kevin
 *
 */
public class Objective {
	
	List<Objective> subObjectives;
	boolean condition;
	
	public Objective() {
		// TODO
	}
	/**
	 * On completion of an objective, perform action
	 */
	public void onComplete() {
		// TODO
	}
	/**
	 * Check if the objective is complete
	 * @return true if objective is completed
	 */
	public boolean isComplete() {
		// TODO
		return false;
	}
	/**
	 * Sets the predicates for an objective
	 * @param condition the condition for the objective to run?
	 */
	public <T extends Observable> void setPredicate(Predicate<T> condition) {
		// TODO
	}
	/**
	 * Lists the sub objectives that must be complete for objective to complete
	 * @param subObjectives list of sub objectives
	 */
	public void setSubObjectivies(List<Objective> subObjectives) {
		// TODO
	}
	/**
	 * 
	 */
	public void start() {
		// TODO
	}

}
