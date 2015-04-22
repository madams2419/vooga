package game_engine.behaviors;

public class ConditionBehavior implements IConditionBehavior{
    private ICondition myCondition;
    private IBehavior myBehavior;
    
    public ConditionBehavior(ICondition condition, IBehavior behavior) {
        myCondition = condition;
        myBehavior = behavior;
    }

    @Override
    public boolean test () {
        return myCondition.test();
    }

    @Override
    public void perform () {
        myBehavior.perform();
    }
}
