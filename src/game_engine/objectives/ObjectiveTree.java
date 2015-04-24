package game_engine.objectives;

import java.util.Collection;
import java.util.function.Consumer;

public class ObjectiveTree extends StatusTree<Objective>{

    public ObjectiveTree (Collection<Objective> allNodes) {
        super(allNodes);
    }
    
    public void setOnComplete(Consumer<Objective> onComplete) {
        addCondition(Objective::isComplete, onComplete);
    }
    
    public void setOnFailed(Consumer<Objective> onFailed) {
        addCondition(Objective::isFailed, onFailed);
    }
    
    public void setOnActive(Consumer<Objective> onActive) {
        addCondition(Objective::isActive, onActive);
    }
    
    public void setOnInActive(Consumer<Objective> inActive) {
        addCondition (obj -> !obj.isFinished() || !obj.isActive(), inActive);
    }

    @Override
    public Collection<Objective> getParents (Objective t) {
        return t.getPreReqs();
    }
    
    public void update(Objective objective, long now) {
        objective.update(now);
        super.update(objective, now);
    }

}
