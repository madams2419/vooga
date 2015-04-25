package game_engine.behaviors;

public interface IConditionBehavior extends ICondition, IBehavior{
    public default void testAndPerform () {
        if (test()) {
            perform();
        }
    }
}
