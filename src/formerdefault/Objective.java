package formerdefault;
/**
 * @author hojeanniechung
 *
 *interface for Objectives
 */
public interface Objective{
	void onComplete();
	void onStart();
	void isComplete();
	void update();

	class MarioObjective implements Objective{
		public void onComplete(){
			System.out.println("Now do things after completion of objective");
		}
		public void onStart(){
			System.out.println("Object is Started");
		}
		public void isComplete(){
			System.out.println("Object is Completed");
		}
		public void update(){
			System.out.println("update the Objectives");
		}
	}
	
	Objective MarioObjective=new MarioObjective();
	
}
