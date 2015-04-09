package authoring.userInterface;

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
	Reflection reflection = new Reflection(); 
	static Set ElementSet=new HashSet();
	
//	public static void main(String args[]){
//		ElementDistributer();
//	}
	
	public void ElementDistributer(){
		Iterator<Entry<String, ArrayList>> it=LayoutXMLParser.myElementMap.entrySet().iterator();
		String f="settings/layout.xml";
		LayoutXMLParser.parse(f);
		while(it.hasNext()){
		    ArrayList<Map> listOfElements = it.next().getValue();
			for(Map element:listOfElements){
				System.out.println("element keyset is "+element.toString());
				ElementSet.add(element.keySet());	
			}	
			String Classname=String.format("%sFactory",forEach(ElementSet));
			reflection.createInstance(Classname);
		}
		System.out.println(ElementSet);
	}

}
