package game_engine;

import java.util.List;
<<<<<<< HEAD
import java.util.Observable;
import java.util.function.Consumer;
import java.util.function.Predicate;

=======
import java.util.function.Consumer;
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
import javafx.beans.property.BooleanProperty;

/**@author Tony
 * Goal that leads to a specific action when completed
 */
public class Objective {
	
<<<<<<< HEAD
	List<Objective> subObjectives;
	boolean condition;
	
=======
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
        /**
         * Constructor
         * @param condition BooleanProperty Represents a condition for the objective completion
         * @param subObjectives List<Objective> List of objectives that must be completed first
         * @param action Consumer<?> Action that occurs when the objective is complete
         */
	public Objective(BooleanProperty condition, List<Objective> subObjectives, Consumer<?> action) {
		// TODO
	}
	
<<<<<<< HEAD
	/***
	 * Empty constructor for mockObjective in usecases
	 */
	public Objective() {
			
	}

=======
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
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
	
	/**
<<<<<<< HEAD
	 * Method invoked when this objective is complete
=======
	 * method onComplete
	 * On completion of an objective, perform action
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
	 */
	public void onComplete() {
		// TODO
	}
<<<<<<< HEAD
	
	/***
	 * To check whether this objective has been completed
	 * @return True iff the objective has been completed
=======
	/**
	 * method isComplete
	 * Check if the objective is complete
	 * @return true if objective is completed
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
	 */
	public boolean isComplete() {
		// TODO
		return false;
	}
	
<<<<<<< HEAD
	public <T extends Observable> void setPredicate(Predicate<T> condition) {
		// TODO
	}
	
}
=======
}
>>>>>>> fd367c3d0eadb2b64a3fa011ebf6723da6e1f422
