/**
 * 
 */
package authoring_UserInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import XML.LayoutXMLParser;

/**
 * @author hojeanniechung
 *
 */
public class UIElementDistributer {
	Reflection reflection = new Reflection(); 
	static Set ElementSet=new HashSet();
	
//	public static void main(String args[]){
//		ElementDistributer();
//	}
	
	public void ElementDistributer(){
		Iterator it=LayoutXMLParser.myElementMap.entrySet().iterator();
		String f="settings/layout.xml";
		LayoutXMLParser.parse(f);
		while(it.hasNext()){
			ArrayList<Map> listOfElements = (ArrayList) it.next();
			for(Map element:listOfElements){
				System.out.println("element keyset is "+element.keySet().toString());
				ElementSet.add(element.keySet());	
			}	
			System.out.println(ElementSet);
//			String Classname=String.format("%sFactory",forEach(ElementSet));
//			reflection.createInstance(Classname);
		}
		
	}

}
