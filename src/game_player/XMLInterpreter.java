package game_player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The XMLInterpreter class constructs a tree of xml entries and puts the lower level ones in a map.
 * The user is then able to access the map using directory names separated by a slash.
 * 
 * @author Brian Lavallee
 * @since 7 April 2015
 */
public class XMLInterpreter {
    
    private static Map<String, String> xml;
    
    private String activePath;
    
    /**
     * Initializes the map and activePath and recursively traces the tree and fills the map.
     * 
     * @param data
     *             is the game or development xml file that the user wants to parse.
     */
    public XMLInterpreter(File data) {
	xml = new HashMap<String, String>();
	activePath = "/";

	try {
	    DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = docBuilder.parse(data);
	    
	    if (doc.hasChildNodes()) {
		read(doc.getChildNodes(), "");
	    }
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
    
    /**
     * Completely resets the activePath to a new value.
     * (Note) does not check if valid.
     * 
     * @param directory
     *                  is the new path the user wants to work in.
     */
    public void setActivePath(String path) {
	activePath = path;
    }
    
    /**
     * Returns the current path.  Useful for debugging if necessary.
     * 
     * @return
     *         the value of the current activePath.
     */
    public String getActivePath() {
	return activePath;
    }
    
    /**
     * Moves the path down the specified additional path.  The user can either enter
     * the entire path as one string and include /'s to distinguish levels (no
     * leading slash necessary) or they may enter each level of the path as
     * individual parameters without /'s.
     * 
     * @param path
     *                  the path the user wants to work in within activePath.
     */
    public void moveDown(String ... path) {
	for (String component : path) {
	    activePath += component + "/";
	}
    }
    
    /**
     * Moves the activePath up one level.
     */
    public void moveUp() {
	moveUp(1);
    }
    
    /**
     * Moves the activePath up by a specified amount.
     * 
     * @param distance
     *                 the number of levels upwards in the activePath to travel.
     */
    public void moveUp(int distance) {
	String[] path = activePath.split("/");
	activePath = "/";
	for (int i = 0; i < path.length - distance; i++) {
	    activePath += path[i] + "/";
	}
    }
    
    /**
     * Uses the XML file to evaluate the given String.  The user can either identify
     * the label they want within the activePath or specify the entire path.
     * 
     * @param label
     *              either the label within the activePath or the entire path.
     *              
     * @return
     *         The value mapped to label by the XML file.
     */
    public String getValue(String label) {
	String ret;
	if ((ret = xml.get(activePath + label))!= null) {
	    return ret;
	}
	else {
	    return getPathValue(label);
	}
    }
    
    /*
     * Used by getValue(String s) to find an entire path as opposed to just a label.
     */
    private String getPathValue(String path) {
	return xml.get(path);
    }
    
    /*
     * Recursively traces the tree and populates the map.
     */
    private void read(NodeList nodes, String path) {
	for (int i = 0; i < nodes.getLength(); i++) {
	    Node node = nodes.item(i);
	    if (node.getChildNodes().getLength() == 1) {
		xml.put(path + "/" + node.getNodeName(), node.getTextContent());
	    }
	    else {
		read(node.getChildNodes(), path + "/" + node.getNodeName());
	    }
	}
    }
}