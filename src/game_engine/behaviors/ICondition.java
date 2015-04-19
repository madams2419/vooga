package game_engine.behaviors;

public interface ICondition {
    
    public boolean test();
    
    public default ICondition or (ICondition condition) {
        return () -> this.test() || condition.test();
    }
    
    public default ICondition and (ICondition condition) {
        return () -> this.test() && condition.test();
    }
}
