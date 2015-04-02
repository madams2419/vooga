/**
 * 
 */
package authoring_UserInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;






import org.w3c.dom.Node;

import XML.UIXMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory {
	static String[] a;
	
	public static void main(String[] args){
		ArrayList<Map> Labels;
		String s="Button"; //test Case
		String f="src/Resources/ButtonTest.xml";
		File buttonFile=UIXMLParser.parse(f, s);
		Labels=UIXMLParser.mAttributesList;
	}
	
	
	
}
