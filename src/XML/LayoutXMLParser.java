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
	public static ArrayList<Map> elements=new ArrayList<Map>();
	
	public static void parse(String f, String s) {

		try {
			File fXmlFile = new File(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			traverseXML(doc);
			System.out.println(elements);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int countChildren (Node node) {
	    int count = 0;
	    NodeList children = node.getChildNodes();
        for(int i=0;i<children.getLength();i++){
            if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                count++;
            }
        }
        return count;
    }

    public static File getFile(){
		return mFile;
    }
	
	private static void traverseXML(Node node){
        NodeList nList = node.getChildNodes();
        Map<String,String> newMap = new HashMap<String,String>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                System.out.println("parent node: "+nNode.getNodeName());
                int childrenNum=countChildren(nNode);
                System.out.println("children number: "+childrenNum);
                if(childrenNum>0){
                    traverseXML(nNode);
                }else{
                    newMap.put(nNode.getNodeName(), nNode.getTextContent());
                    Node parent = nNode.getParentNode();
                    HashMap<String,Map> parentMap = new HashMap<String,Map>();
                    parentMap.put(parent.getNodeName(),newMap);
                    for(int i=0; i<parent.getAttributes().getLength(); i++){
                        Node attr=parent.getAttributes().item(i);
                        newMap.put(attr.getNodeName(), attr.getNodeValue());
                    }
                    elements.add(parentMap);
                }
            }
    	}
	}
}