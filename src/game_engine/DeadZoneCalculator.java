package game_engine;


public class DeadZoneCalculator{
    private double myValue;
    
    public DeadZoneCalculator (double value) {
        myValue = value;
    }
    
    private boolean check (double change) {
        return change / myValue > 1;
    }
    
    public double computeScrollChange (double change) {
        return check(change) ? change - myValue : 0;
    }
}
