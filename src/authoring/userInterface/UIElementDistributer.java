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


	public static void main(String args[]){
		ElementDistributer();
	}

	public static void ElementDistributer(){
		String f="settings/layout.xml";
		LayoutXMLParser.parse(f);
		Iterator<Entry<String, ArrayList>> it=LayoutXMLParser.myElementMap.entrySet().iterator();
		listOfElements = it.next().getValue();
		for(Map element:listOfElements){
			//System.out.println("element keyset is "+element.keySet().toString());
			ElementSet.add(element.keySet().toString());
		}
		Iterator<String> it_element=ElementSet.iterator();


		while(it_element.hasNext()){
			String temp=it_element.next();
			elementNext=temp.toString().substring(1,(temp.toString().length())-1);
			String Classname=String.format("authoring.userInterface.%sFactory",elementNext);
			ClassConstructors.put(Classname,Reflection.createInstance(Classname));
			GeneratorLabel.add(elementNext);
		}
		/*
		 * Get the Type, and a map of its characteristic to the and map it with the constructed class
		 */
		for(ArrayList<Map> values: LayoutXMLParser.myElementMap.values()){
			for(int i=0; i<values.size(); i++){
				Map<String, Map> m=values.get(i);
				//System.out.println(m);
				for(String key: m.keySet()){
					if(ClassConstructors.containsKey(key)){
						Object SelectedClass=ClassConstructors.get(key);
						MethodInvoker(SelectedClass,key,m.get(key));
					}
					else{
						ClassConstructors.put(key,Reflection.createInstance(String.format("authoring.userInterface.%sFactory",key)));
						Object SelectedClass=ClassConstructors.get(key);
						MethodInvoker(SelectedClass,key,m.get(key));
					}
				}
			}
		}
	}

	public static void MethodInvoker(Object selectedClass,String key, Map map){

		Reflection.callMethod(selectedClass, String.format("generate%s",key),map);
	}

}
