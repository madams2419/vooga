package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LayoutXMLParser {
	public static File mFile;
	public static ArrayList<Map> myElements;
	public static Map<String, ArrayList> myElementMap = new HashMap<String, ArrayList>();

	public static void parse(String f) {

		try {
			File fXmlFile = new File(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getFirstChild().getChildNodes();
			for (int temp = 0; temp < nodes.getLength(); temp++) {
				Node nNode = nodes.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					myElements = new ArrayList<Map>();
					traverseXML(nNode);
					myElementMap.put(nNode.getNodeName(), myElements);
				}
			}
			// System.out.println(myElementMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int countChildren(Node node) {
		int count = 0;
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
				count++;
			}
		}
		return count;
	}

	public static File getFile() {
		return mFile;
	}

	private static void traverseXML(Node node) {
		NodeList nList = node.getChildNodes();
		Map<String, String> newMap = new HashMap<String, String>();
		Map<String, Map> returnMap = new HashMap<String, Map>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				int childrenNum = countChildren(nNode);
				if (childrenNum > 0) {
					traverseXML(nNode);
				} else {
					newMap.put(nNode.getNodeName(), nNode.getTextContent());
					Node parent = nNode.getParentNode();
					returnMap.put(parent.getNodeName(), newMap);
				}
			}
		}
		if (!returnMap.isEmpty()) {
			myElements.add(returnMap);
		}
	}
}