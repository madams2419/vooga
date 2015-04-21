/**
 * 
 */
package authoring.userInterface;

import java.util.Map;
import authoring.util.Reflection;
import javafx.scene.control.Button;
/**
 * @author hojeanniechung
 *
 */

public class ButtonFactory{

	private static ButtonFactory mInstance;
//	private static Reflection reflection=new Reflection();

	/*==============================Constructors=================================================*/

	public static ButtonFactory getSharedInstace() {
		if(mInstance==null)
			mInstance=new ButtonFactory();
		return mInstance;
	}

	public ButtonFactory(){
		System.out.println("Reached public constructor for ButtonFactory");

	}

	public static Button generateButton(Object listenerClass, Map<String,String> m){
		//System.out.println("button" + m);
		Button b = new Button();
		b.setText(m.get("label"));
		b.setOnMouseClicked(e->Reflection.callMethod(listenerClass, m.get("listener")));
		return b;
	}

}
