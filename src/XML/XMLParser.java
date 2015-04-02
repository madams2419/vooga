package XML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
	public static Map<String,String> mAttributeMap;
	public static File mFile;
	
	public static File parse(String f, String s) {

		try {
			File fXmlFile = new File(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Button");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;


					/*This is the element we are looking for (i.e.)"button, box, etc."*/					
					if(eElement.getNodeName().equals(s)){
						mAttributeMap=new HashMap<String,String>();
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
					System.out.println(mAttributeMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mFile;
	}


}