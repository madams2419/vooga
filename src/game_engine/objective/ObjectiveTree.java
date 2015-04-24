package game_engine.objective;

import java.util.Collection;
import javafx.scene.Node;


/**
 * Abstract class. This is a specific implementation of StatusTree. This class will be primarily
 * used for visually representing an objective structure.
 * 
 * @author Tony
 *
 */
public abstract class ObjectiveTree extends StatusTree<Objective> {

    public ObjectiveTree (Collection<Objective> allNodes) {
        super(allNodes);
        addCondition(Objective::isFailed, this::onFailed);
        addCondition(Objective::isComplete, this::onComplete);
        addCondition(Objective::isActive, this::onActive);
        addCondition(obj -> !obj.isFinished() && !obj.isActive(), this::onInactive);
    }
    
    /**
     * Defines what happens when an objective is complete.
     * @param objective
     */
    public abstract void onComplete (Objective objective);

    /**
     * Defines what happens when an objective is failed.
     * @param objective
     */
    public abstract void onFailed (Objective objective);

    /**
     * Defines what happens when an objective is active.
     * @param objective
     */
    public abstract void onActive (Objective objective);

    public abstract void onInactive (Objective objective);

    @Override
    /**
     * See StatusTree. For objectives, the prerequisite objectives are treated as the parents of each objective.
     */
    public Collection<Objective> getParents (Objective t) {
        return t.getPreReqs();
    }

    public void update (Objective objective, long now) {
        objective.update(now);
        super.update(objective, now);
    }

    /**
     *
     * @return Returns the node that contains the tree. 
     */
    public abstract Node getTree ();
}
