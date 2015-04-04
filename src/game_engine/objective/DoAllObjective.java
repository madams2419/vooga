package game_engine.objective;

import game_engine.Behavior;
import java.util.function.IntSupplier;

public class DoAllObjective extends DoRemainingObjective {

    public DoAllObjective (IntSupplier numRemaining, Behavior onComplete) {
        super(numRemaining, onComplete);
    }
    
    @Override
    public String toString() {
        return convertToAll(super.toString(), getNumRemaining());
    }
    
    public static String convertToAll(String string, int num){
        return string.replaceFirst(Integer.toString(num), "All");
    }
}
