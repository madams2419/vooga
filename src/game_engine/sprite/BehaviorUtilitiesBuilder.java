package game_engine.sprite;
import java.lang.reflect.Method;

public class BehaviorUtilitiesBuilder {
	public void tester(){
		Method[] myMethods = Character.class.
		for(Method m:myMethods){
			String className = m.toString();
//			if(className.contains("IBehavior")){
				System.out.println(m.toString());
//			}
		}
	}
	
	public static void main (String[] args){
		BehaviorUtilitiesBuilder bub = new BehaviorUtilitiesBuilder();
		bub.tester();
	}
}
