package game_engine.objective;

import game_engine.CollisionEngine;
import game_engine.sprite.Sprite;
import java.util.List;

/*
 * Example of building an objective
 * Objective obj = makeCollisionObjective (num, spriteA, spriteB)
 * obj.setPreqs(List<Objective> objectives) => these objectives must be complete 
 * for objective to be active.
 * obj.addBehavior (String status, IBehavior behavior, params)
 * obj.setTimer (new GameTimer(duration) if you want to add a timer, can input null or not call
 * 
 */
public class ObjectiveFactory {
    private CollisionEngine myCollisionEngine;

    public ObjectiveFactory (CollisionEngine collisionEngine) {
        myCollisionEngine = collisionEngine;
    }
    
    public Objective makeObjective () {
        return new Objective();
    }
    
    public Objective makeCollisionObjective (int num, Sprite spriteA, Sprite spriteB) {
        Objective obj = new DoRemainingObjective (num, () -> 0);
        obj.doNum(myCollisionEngine.countNumCollisions (spriteA, spriteB));
    }
    
    public Objective makeListObjective (int num, List<Objective> objectives){
        return new CompleteNumObjectives(num, objectives);
    }
    
    public Objective makeListObjective (List<Objective> objectives) {
        return new CompleteNumObjectives(objectives);
    }
}
