/**
 * @author hojeanniechung
 *
 */
abstract class Objective{
	abstract void onComplete();
	abstract void onStart();
	abstract void isComplete();
	abstract void update();

	class MarioObjective extends Objective{
		void onComplete(){
			System.out.println("Now do things after completion of objective");
		}
		void onStart(){
			System.out.println("Object is Started");
		}
		void isComplete(){
			System.out.println("Object is Completed");
		}
		void update(){
			System.out.println("update the Objectives");
		}
	}
	
	Objective MarioObjective=new MarioObjective();
	
}
