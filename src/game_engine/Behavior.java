package game_engine;

/**
 * Interface for all actions that can be executed
 * @author 
 *
 */
public class Behavior implements IBehavior{
	private IAction myBehavior;
	private String[] myParams;
    
    
	public Behavior (IAction behavior, String[] params) {
	    myBehavior = behavior;
	    myParams = params;
	}
	
	public void perform () {
	    myBehavior.execute(myParams);
	}
	
}
