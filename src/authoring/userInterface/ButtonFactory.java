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
<<<<<<< HEAD


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
=======
	
	public static ButtonFactory getSharedInstance() {
		return mInstance;
	}

//	public static void main(String[] args){	
//		s="Button"; //test Case
//		String f="src/Resources/FilestoParse.xml";
//		LayoutXMLParser.parse(f, s);
//		mFile=LayoutXMLParser.getFile(); 
//	}
//	
	private ButtonFactory(String f){
		//s="Button"; //test Case
		f="src/settings/layout.xml";
		LayoutXMLParser.parse(f);
		mFile=LayoutXMLParser.getFile();
		GetAttributes();
>>>>>>> 040310bf83d8f9d359743e47048a6fb27acca693



	public static void generateButton(Map m){
		System.out.println("button" + m);
		Button b = new Button();
	}
<<<<<<< HEAD



=======
	
	public static ArrayList<Map> GetAttributes(){	
		//clear AttributeList from before
		for(int i=0; i<LayoutXMLParser.mAttributesList.size(); i++){
			LayoutXMLParser.mAttributesList.remove(i);
		}
		LayoutXMLParser.parse(mFile.toString(), mType);
		mAttributesList= LayoutXMLParser.mAttributesList;
		//System.out.println("mAttributesList is "+mAttributesList);
		return mAttributesList;
	}
	
	public static ArrayList<Button> generateButtonBoxes(){
		for(int i=0; i<mAttributesList.size(); i++){
			//Button box=new Button();
			Button button=new Button(mAttributesList.get(i).get("id").toString());
			mButtonList.add(button);
			//System.out.println(mAttributesList.get(i).get("id"));
		}
		return mButtonList;
	}
	
>>>>>>> 040310bf83d8f9d359743e47048a6fb27acca693
}
