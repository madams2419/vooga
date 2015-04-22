package game_engine.behaviors;

import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class Condition implements ICondition{
    private BooleanSupplier mySupplier;
    
    public <T, U> Condition (BiPredicate<T, U> condition, T t, U params) {
        mySupplier = () -> condition.test(t, params);
    }
    
    public <U> Condition (Predicate<U> condition, U params) {
        mySupplier = () -> condition.test(params);
    }

    @Override
    public boolean test () {
        return mySupplier.getAsBoolean();
    }
}
