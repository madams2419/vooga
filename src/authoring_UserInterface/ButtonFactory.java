/**
 * 
 */
package authoring_UserInterface;

import java.io.File;
import java.util.Map;





import org.w3c.dom.Node;

import XML.XMLParser;
/**
 * @author hojeanniechung
 *
 */
public class ButtonFactory {
	static String[] a;
	
	public static void main(String[] args){
		Map<String, String> Labels;
		String s="Button"; //test Case
		String f="src/Resources/ButtonTest.xml";
		File buttonFile=XMLParser.parse(f, s);
		Labels=XMLParser.mAttributeMap;
	}
	
}
