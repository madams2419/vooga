package game_engine.behaviors;

public interface IBehavior {
    
    public void perform ();
    
    public default IBehavior andThen (IBehavior behavior) {
        return () -> {
            this.perform();
            behavior.perform();
        };
    }
}
