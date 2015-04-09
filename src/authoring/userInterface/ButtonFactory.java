/**
 * 
 */
package authoring.userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import XML.LayoutXMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory{
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


	//	public static void main(String[] args){	
	//		s="Button"; //test Case
	//		String f="src/Resources/FilestoParse.xml";
	//		LayoutXMLParser.parse(f, s);
	//		mFile=LayoutXMLParser.getFile(); 
	//	}
	//	
	
	public ButtonFactory(){
		System.out.println("Reached public constructor for ButtonFactory");
		
	}



	public static void generateButton(Map m){
		System.out.println("button" + m);
		Button b = new Button();
	}



}
