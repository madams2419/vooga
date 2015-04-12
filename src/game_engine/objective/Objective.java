package game_engine.objective;

import game_engine.IAction;
import game_engine.IActor;
import game_engine.IBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * Class that represents goals or winning/losing conditions of the game.
 * Each objective has a condition and a behavior. When the condition is true, the behavior will be
 * executed.
 * 
 * @author Tony
 *
 */
public class Objective implements IActor{
    private Map<Predicate<Long>, Status> myConditions;
    private Map<Status, IBehavior> myBehaviors;
    /**
     * List of objectives that must be completed before this objective is active.
     */
    private List<Objective> myPreReqs;
    private Optional<GameTimer> myTimer;
    private Status myStatus;
    protected enum Status {
        INACTIVE,
        ACTIVE,
        COMPLETE,
        FAILED;
    }

    /**
     * Constructor
     * 
     * @param condition
     * @param onComplete
     * @param linked List of objectives that must be completed before this objective is active.
     */
    public Objective () {
        myStatus = Status.INACTIVE;
        myPreReqs = new ArrayList<>();
        myConditions = new HashMap<>();
        myBehaviors = new HashMap<>();
        myTimer = Optional.empty();
    }

    /**
     * Set the objectives that must be completed before this objective is active.
     * 
     * @param preReqs Objectives that must be completed before this objective is active.
     */
    public void setPreReqs (List<Objective> preReqs) {
        myPreReqs = preReqs;
    }
    
    public void setBehavior (String status, IBehavior behavior){
        myBehaviors.put(Status.valueOf(status.toUpperCase()), behavior);
    }
    
    public void addCondition (Predicate<Long> condition, String status) {
        addCondition(condition, Status.valueOf(status.toUpperCase()));
    }
     
    public void addCondition (Predicate<Long> condition, Status status) {
        myConditions.put(condition, status);
    }
    
    public void setTimer (GameTimer timer){
        myTimer = Optional.ofNullable(timer);
        addCondition(this::checkTimer, Status.FAILED);
    }
    
    /**
     * Returns true if and only if the timer has expired.
     * @param now
     * @return
     */
    private boolean checkTimer (long now) {
        return myTimer.filter(timer -> timer.isFinished(now)).isPresent();
    }

    /**
     * Updates active and completion status. Completes if objective is complete.
     * @param now 
     */
    public void update (long now) {
        updateActive(now);
        if (isActive()) {
            updateStatus (now);
            executeStatus();
        }
    }
    
    private void updateStatus (long now) {
        for (Predicate<Long> condition: myConditions.keySet()) {
            if (condition.test(now)) {
                myStatus = myConditions.get(condition);
            }
        }
    }    
    
    private void executeStatus () {
        myBehaviors.getOrDefault(myStatus,() -> {}).perform();
    }

    private void updateActive(long now) {
        if (isActive() || isFinished()){
            return;
        }
        setActive(arePreReqsFinished(), now);
    }
    
    /**
     * Updates the active status and returns the status. An objective's completion status only if it
     * is active.
     * 
     * @return true iff objective is active.
     */
    public boolean isActive () {
        return myStatus == Status.ACTIVE;
    }

    private boolean arePreReqsFinished () {
        return myPreReqs.stream().allMatch(obj -> obj.isComplete());
    }

    public boolean isComplete () {
        return myStatus == Status.COMPLETE;
    }
    
    public boolean isFailed() {
        return myStatus == Status.FAILED;
    }
    
    
    public boolean isFinished() {
        return isComplete() || isFailed();
    }

    public void setActive (boolean active, long now) {
        myStatus = Status.ACTIVE;
        if (isActive()){
            myTimer.ifPresent(timer -> timer.start(now));
        }
    }

    @Override
    public IAction getAction (String name) {
        if (name == "setStatus"){
            return (params) -> {
                myStatus = Status.valueOf(params[0].toUpperCase());
            };
        }
        return (params) -> {};
    }
}
