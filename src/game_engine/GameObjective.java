package game_engine;

import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

public class GameObjective {
	
	List<GameObjective> subObjectives;
	boolean condition;
	
	public GameObjective() {
		// TODO
	    condition = false;
	}
	
	public void onComplete() {
		// TODO
	    condition = true;
	}
	
	public boolean isComplete() {
		// TODO
		return false;
	}

	
	public <T extends Observable> void setPredicate(Predicate<T> condition) {
		// TODO
	}
	
	public void setSubObjectivies(List<GameObjective> subObjectives) {
		// TODO
	}
	
	public void start() {
		// TODO
	}

	public Object update() {
		// TODO Auto-generated method stub
		return null;
	}

}
