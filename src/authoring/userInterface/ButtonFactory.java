/**
 * 
 */
package authoring.userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.Button;

import org.w3c.dom.Node;
import XML.LayoutXMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory {
	private static File mFile;
	private static String mType;
	private static ButtonFactory mInstance;
	private static String s;
	private static ArrayList<Map> mAttributesList=new ArrayList<Map>();
	private static ArrayList<Button> mButtonList=new ArrayList<Button>();

	/*==============================Constructors=================================================*/

	public static ButtonFactory getSharedInstace(String f) {
		if(mInstance==null)
			mInstance=new ButtonFactory(f);
		return mInstance;
		// TODO Auto-generated constructor stub
	}

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
	
	public ButtonFactory(){
		System.out.println("Reached public constructor for ButtonFactory");
	}
	private ButtonFactory(String f){
		//s="Button"; //test Case
		f="src/settings/layout.xml";
		LayoutXMLParser.parse(f);
		mFile=LayoutXMLParser.getFile();
		GetAttributes();

	}

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

	public static void generateButton(Map m){
		System.out.println("button" + m);
	}
}
