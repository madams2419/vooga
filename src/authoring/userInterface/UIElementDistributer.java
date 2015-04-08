package src.authoring.userInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import XML.LayoutXMLParser;

/**
 * @author hojeanniechung & mungcheow
 *
 */
public class UIElementDistributer {
	static Reflection reflection = new Reflection(); 
	static Set ElementSet=new HashSet();
	private static String elementNext;

	public static void main(String args[]){
		ElementDistributer();
	}

	public static void ElementDistributer(){
		String f="settings/layout.xml";
		LayoutXMLParser.parse(f);
		Iterator<Entry<String, ArrayList>> it=LayoutXMLParser.myElementMap.entrySet().iterator();

		ArrayList<Map> listOfElements = it.next().getValue();
		for(Map element:listOfElements){
			//System.out.println("element keyset is "+element.keySet().toString());
			ElementSet.add(element.keySet().toString());	
		}
		Iterator<String> it_element=ElementSet.iterator();


		while(it_element.hasNext()){
			//System.out.println(it_element.next().toString());
			String temp=it_element.next();
			elementNext=temp.toString().substring(1,(temp.toString().length())-1);
			System.out.println(elementNext);
			String Classname=String.format("authoring.userInterface.%sFactory",elementNext);
			reflection.createInstance(Classname);
		}
	}
}
