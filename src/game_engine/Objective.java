package game_engine;

import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

public class Objective {
	
	List<Objective> subObjectives;
	boolean condition;
	
	public Objective() {
		// TODO
	}
	
	public void onComplete() {
		// TODO
	}
	
	public boolean isComplete() {
		// TODO
		return false;
	}
	
	public <T extends Observable> void setPredicate(Predicate<T> condition) {
		// TODO
	}
	
	public void setSubObjectivies(List<Objective> subObjectives) {
		// TODO
	}
	
	public void start() {
		// TODO
	}

}
