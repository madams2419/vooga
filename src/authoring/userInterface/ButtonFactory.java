/**
 * 
 */
package authoring.userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import org.w3c.dom.Node;

import XML.LayoutXMLParser;
/**
 * @author hojeanniechung
 *
 */

public class ButtonFactory{
	//	private static File mFile;
	//	private static String mType;


	private static ButtonFactory mInstance;
	//	private static String s;
	//	private static ArrayList<Map> mAttributesList=new ArrayList<Map>();
	//	private static ArrayList<Button> mButtonList;

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


	public static Button generateButton(Map<String,String> m){
		//System.out.println("button" + m);
		Button b = new Button();
		b.setText(m.get("label"));
		//		mButtonList.add(b);
		//		return mButtonList;
		return b;
	}

	
}
