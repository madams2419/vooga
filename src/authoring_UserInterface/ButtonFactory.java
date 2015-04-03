/**
 * 
 */
package authoring_UserInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.Button;

import org.w3c.dom.Node;

import XML.UIXMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory {
	private static File mFile;
	private static ButtonFactory mInstance;
	private static String s;
	private static ArrayList<Map> mAttributesList;
	private static ArrayList<Button> mButtonList;

	public static ButtonFactory getSharedInstace(File f, String s2) {
		if(mInstance==null)
			mInstance=new ButtonFactory(f, s2);
		return mInstance;
		// TODO Auto-generated constructor stub
	}

//	public static void main(String[] args){	
//		s="Button"; //test Case
//		String f="src/Resources/FilestoParse.xml";
//		UIXMLParser.parse(f, s);
//		mFile=UIXMLParser.getFile(); 
//	}
//	
	private ButtonFactory(File f, String s){
		//s="Button"; //test Case
		//String f="src/Resources/FilestoParse.xml";
		UIXMLParser.parse(f.toString(), s);
		mFile=UIXMLParser.getFile();
	}
	
	private static ArrayList<Map> GetAttributes(){	
		//clear AttributeList from before
		for(int i=0; i<UIXMLParser.mAttributesList.size(); i++){
			UIXMLParser.mAttributesList.remove(i);
		}
		UIXMLParser.parse(mFile.toString(), s);
		mAttributesList= UIXMLParser.mAttributesList;
		return mAttributesList;
	}
	
	private static ArrayList<Button> generateButtonBoxes(){
		for(int i=0; i<mAttributesList.size(); i++){
			//Button box=new Button();
			Button button=new Button(mAttributesList.get(i).get("id").toString());
			mButtonList.add(button);
			System.out.println(mAttributesList.get(i).get("id"));
		}
		return mButtonList;
	}
	
}
