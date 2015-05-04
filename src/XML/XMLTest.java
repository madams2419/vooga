// This entire file is part of my masterpiece.
// Jeannie Chung

/**
 * 
 */
package XML;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hojeanniechung
 *
 */


public class XMLTest {

	/**
	 * @param args
	 */
	private static void importFile(File f){
		QueryParser b=new QueryParser();
		Map<String,String> resulta=new HashMap<>();
		try {
			b.parse(f.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		importFile(new File("resources/XML/Query.xml"));
	}

}
