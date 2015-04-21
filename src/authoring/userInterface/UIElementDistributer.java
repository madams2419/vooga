package authoring.userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.Scene;
import XML.LayoutXMLParser;

/**
 * @author hojeanniechung & mungcheow
 * 
 */
@SuppressWarnings("rawtypes")
public class UIElementDistributer extends AuthoringGUITester {

	static Reflection reflection = new Reflection();
	private static Map<String, Object> ClassConstructors = new HashMap<String, Object>();
	public static ArrayList<Map> MapofValues = new ArrayList<Map>();

	@SuppressWarnings("unchecked")
	public static void ElementDistributer(Scene scene, AuthoringWindow window) {
		String f = "settings/layout.xml";
		LayoutXMLParser.parse(f);

		for (Entry<String, ArrayList> Entry : LayoutXMLParser.myElementMap
				.entrySet()) {
			String panes = Entry.getKey();
			System.out.println(panes);
			ArrayList<Map> values = Entry.getValue();
			String classname = String.format("authoring.%s",
					panes);
			ClassConstructors.put(classname, window.getPane(classname));
			System.out.println(classname);
			MethodInvoker(ClassConstructors.get(classname), "Components",
					values);
		}
	}

	public static void MethodInvoker(Object selectedClass, String key,
			ArrayList<Map> values) {
		MapofValues = values;
		Reflection.callMethod(selectedClass, String.format("generate%s", key),
				values);

	}
}
