package authoring.userInterface;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.application.Application;
import XML.LayoutXMLParser;

/**
 * @author hojeanniechung & mungcheow
 *
 */
public class UIElementDistributer  extends AuthoringGUITester {
	static Reflection reflection = new Reflection(); 
	static Set ElementSet=new HashSet();
	private static String elementNext;
	private static Map<String, Object> ClassConstructors=new HashMap<String,Object>();
	private static ArrayList<String> GeneratorLabel=new ArrayList<String>();
	private static ArrayList<String> ListenerNames=new ArrayList<String>();
	private static ArrayList<Map> listOfElements=new ArrayList<Map>();
	public static ArrayList<Map> MapofValues=new ArrayList<Map>();

	public static void main(String args[]){
		ElementDistributer();
	}

	public static void ElementDistributer(){
		String f="settings/layout.xml";
		LayoutXMLParser.parse(f);
		//		Iterator<Entry<String, ArrayList>> it=LayoutXMLParser.myElementMap.entrySet().iterator();
		//		listOfElements = it.next().getValue();
		//		for(Map element:listOfElements){
		//			//System.out.println("element keyset is "+element.keySet().toString());
		//			ElementSet.add(element.keySet().toString());
		//		}
		//		Iterator<String> it_element=ElementSet.iterator();


		for(Entry<String, ArrayList> Entry: LayoutXMLParser.myElementMap.entrySet()){
			String Panes=Entry.getKey();
			ArrayList<Map> values=Entry.getValue();
			String Classname=String.format("authoring.userInterface.%s",Panes);
			ClassConstructors.put(Classname,Reflection.createInstance(Classname));
			MethodInvoker(ClassConstructors.get(Classname),"Components",values);
		}
	}


	public static void MethodInvoker(Object selectedClass,String key, ArrayList<Map> values){
		MapofValues=values;
		Reflection.callMethod(selectedClass, String.format("generate%s",key),values);

	}
}
