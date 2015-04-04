package game_engine.objective;

import game_engine.Behavior;
import java.util.ArrayList;
import java.util.List;
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
public class Objective {
    /**
     * Condition for this objective to be complete.
     */
    private Predicate<Long> myCondition;
    /**
     * Action performed when objective is complete.
     */
    private Behavior myOnComplete;
    
    /**
     * List of objectives that must be completed before this objective is active.
     */
    private List<Objective> myPreReqs;
    private String myName;
    private Optional<GameTimer> myTimer;
    private Status myStatus;
    private enum Status {
        INACTIVE,
        ACTIVE,
        COMPLETE;
    }

    /**
     * Constructor
     * 
     * @param condition
     * @param onComplete
     * @param linked List of objectives that must be completed before this objective is active.
     */
    public Objective (Predicate<Long> condition, Behavior onComplete) {
        myStatus = Status.INACTIVE;
        myPreReqs = new ArrayList<>();
        myCondition = condition;
        myOnComplete = onComplete;
    }

    /**
     * Set the objectives that must be completed before this objective is active.
     * 
     * @param preReqs Objectives that must be completed before this objective is active.
     */
    public void setPreReqs (List<Objective> preReqs) {
        myPreReqs = preReqs;
    }
    
    public void setTimer (GameTimer timer){
        myTimer = Optional.ofNullable(timer);
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
     * @param now TODO
     */
    public void update (long now) {
        updateActive(now);
        if (isActive()) {
            updateComplete(now);
            if (isComplete()) {
                complete(now);
            }
        }
    }

    private void updateActive(long now) {
        if (isActive()){
            return;
        }
        setActive(arePreReqsFinished() && !isComplete(), now);
    }
    
    private void updateComplete(long now) {
        if (myCondition.test(now) || checkTimer(now)){
            myStatus = Status.COMPLETE;
        }
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

    public void setActive (boolean active, long now) {
        myStatus = Status.ACTIVE;
        if (isActive()){
            myTimer.ifPresent(timer -> timer.start(now));
        }
    }

    /**
     * Executes the behavior associated with this object.
     * Also sets the objective to complete and objective is no longer active.
     */
    public void complete (long now) {
        myOnComplete.execute();
        myStatus = Status.COMPLETE;
    }

    public void setName (String name) {
        myName = name;
    }

    @Override
    public String toString () {
        return myName;
    }
    
    public static void main (String[] args) {
        Objective o = new Objective(now -> false, () -> System.out.println("done"));
        o.setTimer(new GameTimer(3));
        o.setActive(true, 0);
        for (int i =0; i < 5; i++){
            System.out.println("i =" + i);
            o.update(i);
        }
        
    }
}
