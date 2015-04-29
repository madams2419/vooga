package game_engine.objectives;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * Class that represents goals or winning/losing conditions of the game. Each objective has four
 * different statuses (inactive, active, complete, and failed). Conditions can be added that can
 * cause the objective to change status. A behavior can also be associated with each status such
 * that when the objective is changed to that status, that behavior is performed.
 * 
 * @author Tony
 *
 */
public class Objective implements IActor {
    private Map<Predicate<Double>, Status> myConditions;
    private Map<Status, IBehavior> myBehaviors;
    /**
     * List of objectives that must be completed before this objective is active.
     */
    private List<Objective> myPreReqs;
    private Optional<GameTimer> myTimer;
    private Status myStatus;
    private String myName;
    private Map<String, IAction> myActions;

    protected enum Status {
        INACTIVE,
        ACTIVE,
        COMPLETE,
        FAILED;

        public static Status get (String status) {
            return Status.valueOf(status.toUpperCase());
        }
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
        myActions = buildActionMap();
    }

    /**
     * Set the objectives that must be completed before this objective is active.
     * 
     * @param preReqs Objectives that must be completed before this objective is active.
     */
    public void setPreReqs (List<Objective> preReqs) {
        myPreReqs = preReqs;
    }

    public Collection<Objective> getPreReqs () {
        return myPreReqs;
    }

    /**
     * Sets the behavior performed when the objective's status is changed to a certain value.
     * 
     * @param status
     * @param behavior
     */
    public void setBehavior (String status, IBehavior behavior) {
        myBehaviors.put(Status.get(status), behavior);
    }

    /**
     * Adds a condition that leads to a given status.
     * 
     * @param condition
     * @param status
     */
    public void addCondition (Predicate<Double> condition, String status) {
        addCondition(condition, Status.get(status));
    }

    protected void addCondition (Predicate<Double> condition, Status status) {
        myConditions.put(condition, status);
    }

    /**
     * Sets a timer for the objective. Status is FAILED if the timer expires.
     * 
     * @param timer
     */
    public void setTimer (GameTimer timer) {
        myTimer = Optional.ofNullable(timer);
        addCondition(this::checkTimer, Status.FAILED);
    }

    /**
     * Returns true if and only if the timer has expired.
     * 
     * @param now
     * @return
     */
    private boolean checkTimer (double now) {
        return myTimer.filter(timer -> {
            timer.update(now);
            return timer.isFinished();
        }).isPresent();
    }

    /**
     * Updates active and completion status. Completes if objective is complete.
     * 
     * @param now
     */
    public void update (double now) {
        updateActive(now);
        if (isActive()) {
            updateStatus(now);
            executeStatus();
        }
    }

    private void updateStatus (double now) {
        for (Predicate<Double> condition : myConditions.keySet()) {
            if (condition.test(now)) {
                myStatus = myConditions.get(condition);
            }
        }
    }

    private void executeStatus () {
        myBehaviors.getOrDefault(myStatus, () -> {}).perform();
    }

    private void updateActive (double now) {
        if (isActive() || isFinished()) {
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

    public boolean isFailed () {
        return myStatus == Status.FAILED;
    }

    public boolean isFinished () {
        return isComplete() || isFailed();
    }

    /**
     * 
     * @param active
     * @param now
     */
    public void setActive (boolean active, double now) {
        myStatus = active ? Status.ACTIVE : Status.INACTIVE;
        if (isActive()) {
            myTimer.ifPresent(timer -> timer.start());
        }
    }

    public void setName (String name) {
        myName = name;
    }

    @Override
    public String toString () {
        return myName;
    }

    @IActionAnnotation (description = "set status", numParams = 1, paramDetails = "complete, failed, or active")
    private IAction setStatus = (params) -> {
        if (isActive() && !params[0].equals("active")) {
            addCondition( (now) -> true, params[0]);
        }
        if (!isFinished() && params[0].equals("active")) {
            myStatus = Status.ACTIVE;
        }
    };

    public IAction getAction (String name) {
        return myActions.get(name);
    }
}
