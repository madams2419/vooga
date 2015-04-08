/**
 * 
 */
package authoring.userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Button;

import org.w3c.dom.Node;

import XML.LayoutXMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory {
	private static final boolean String = false;
	private static File mFile;
	private static String mType;
	private static ButtonFactory mInstance;
	private static String s;
	private static ArrayList<Map> mAttributesList=new ArrayList<Map>();
	private static ArrayList<Button> mButtonList=new ArrayList<Button>();

	/*==============================Constructors=================================================*/

	public static ButtonFactory getSharedInstace() {
		if(mInstance==null)
			mInstance=new ButtonFactory();
		return mInstance;
		// TODO Auto-generated constructor stub
	}
	
	public ButtonFactory(){
		System.out.println("Reached public constructor for ButtonFactory");
	}

	public static void generateButton(Map<String,String> m){
		System.out.println("button" + m);
//		ArrayList<String> Keys=new ArrayList<String>();
//		for(String mapKeys :m.keySet()){
//			if(!Keys.contains(mapKeys)){
//				Keys.add(mapKeys);
//			}
//		}
		Button b=new Button(m.get("label"));
		b.setOnAction(e->m.get("listener"));
		mButtonList.add(b);
	}
}
