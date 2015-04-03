package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UIXMLParser {
	public static File mFile;
	public static ArrayList<Map> mAttributesList=new ArrayList<Map>();
	
	public static void parse(String f, String s) {

		try {
			File fXmlFile = new File(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Button");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Map<String,String> mAttributeMap=new HashMap<String,String>();
					Element eElement = (Element) nNode;
					

					/*This is the element we are looking for (i.e.)"button, box, etc."*/					
					if(eElement.getNodeName().equals(s)){
						
						
						if(eElement.hasAttribute("file")){
							mFile=new File(eElement.getAttribute("file"));
							
						}
						
						
						for(int i=0; i<eElement.getAttributes().getLength(); i++){
							Node attr=eElement.getAttributes().item(i);
							String attrName=attr.getNodeName();
							String attrValue=attr.getNodeValue();	
							mAttributeMap.put(attr.getNodeName(), attr.getNodeValue());
						}
					}
					mAttributesList.add(mAttributeMap);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static File getFile(){
		return mFile;
	}

}