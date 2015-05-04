// This entire file is part of my masterpiece.
// Jeannie Chung

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

public class QueryParser {

	public static  File mFile;
	public  ArrayList<Map> myElements;
	public  Map<String, String> myElementMap= new HashMap<String,String>();

	public Map<String,String> parse(String f) {

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
					for(String k:traverseXML(nNode).keySet()){
						myElementMap.put(k,traverseXML(nNode).get(k));
					}
					System.out.println(myElementMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myElementMap;
	}

	private int countChildren(Node node) {
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

	private Map<String,String> traverseXML(Node node) {
		NodeList nList = node.getChildNodes();
		Map<String, String> newMap = new HashMap<String, String>();
		Map<String, String> returnMap = new HashMap<String, String>();
		ArrayList<String> valueArray=new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType()==Node.TEXT_NODE) {
				int childrenNum = countChildren(nNode);
				if (childrenNum > 0) {
					traverseXML(nNode);
				} else {
					valueArray.add(nNode.getNodeValue());
					newMap.put(nNode.getParentNode().getNodeName(), nNode.getNodeValue());
					returnMap=newMap;
				}
			}
			return returnMap;
		}
		if (!returnMap.isEmpty()) {
			myElements.add(returnMap);
		}
		return returnMap;
	}
}