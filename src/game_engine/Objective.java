package game_engine;

import java.util.List;
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
