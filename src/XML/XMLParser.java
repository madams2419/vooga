package XML;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	private static String Label = null;

	public Map<String,String> parse(File f, String s) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Map<String,String> parsedMap=new HashMap<String,String>();
		Document document = builder.parse(f);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName(String.format("resources/%s",s));

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				Label = eElement.getNodeName().trim();
			}
			
			parsedMap.put(Label, "Button");
		}

		// write it back onto the XML File
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(f);
		transformer.transform(source, result);
		System.out.println(parsedMap);

		return parsedMap;
	}
	

}