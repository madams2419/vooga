//package XML;
////XMLFile
//
//import java.io.File;
//import java.util.Map;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import formerdefault.PropertiesParser;
//
//public class XMLParser {
//	public Map<String,String> parse(File f) throws Exception {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		Document document = builder.parse(f);
//		document.getDocumentElement().normalize();
//		NodeList nList = document.getElementsByTagName(PropertiesParser.getSharedInstance().getLabelMap().get("Game"));
//
//		for (int temp = 0; temp < nList.getLength(); temp++) {
//			Node node = nList.item(temp);
//			;
//			if (node.getNodeType() == Node.ELEMENT_NODE) {
//				Element eElement = (Element) node;
//				String Player = eElement.getElementsByTagName(PropertiesParser.getSharedInstance().getLabelMap().get("Player")).item(0)
//						.getTextContent().trim();
//			}
//		}
//
//		// write it back onto the XML File
//		TransformerFactory transformerFactory = TransformerFactory
//				.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		DOMSource source = new DOMSource(document);
//		StreamResult result = new StreamResult(f);
//		transformer.transform(source, result);
//
//		return null;
//	}
//	
//
//}