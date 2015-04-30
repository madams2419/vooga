package game_player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * XMLParser allows the user to easily and recursively read values in from an XML file.
 * The class is basically just a Map<String, String> which associates labels with values.
 * Only the base values are written in the map, rather than every single label. To implement this,
 * the Map associates a full path with the value as opposed to just the label. However, to
 * smooth the use of this class, an activePath is stored additionally so the user can access a value
 * within a path with just the label. To provide some error checking, a Tree of Directory objects
 * is also implemented so that the user can't go to a Directory that doesn't exist within the XML
 * file.
 * 
 * @author Brian Lavallee
 * @since 12 April 2015
 */
public class XMLParser {

    /*
     * Directory stores a name and a list of sub-directories. Thus
     * it is a basic node-tree implementation.
     */
    private class Directory {

        private String name;
        private List<Directory> subDirectories;

        public Directory (String n) {
            if (n.charAt(n.length() - 1) != '/') {
                n += '/';
            }
            name = n;
            subDirectories = new ArrayList<Directory>();
        }

        public void addSubDirectory (Directory newSubDirectory) {
            subDirectories.add(newSubDirectory);
        }

        public List<Directory> getSubDirectories () {
            return Collections.unmodifiableList(subDirectories);
        }

        public Directory getDirectory (String name) {
            if (name.charAt(name.length() - 1) != '/') {
                name += '/';
            }
            for (Directory directory : subDirectories) {
                if (directory.name.equals(name)) {
                    return directory;
                }
            }
            return this;
        }

        public String toString () {
            return name.substring(0, name.length() - 1);
        }

        public boolean equals (Object o) {
            try {
                Directory other = (Directory) o;
                return name.equals(other.name);
            }
            catch (ClassCastException e) {
                return false;
            }
        }
    }

    private Map<String, String> xml;

    private List<Directory> activePath;

    private Directory root = new Directory("/");

    /**
     * General constructor.
     * 
     * @param data
     *        is the XML file that the user wants to parse.
     */
    public XMLParser (File data) {
        xml = new TreeMap<String, String>();
        activePath = new ArrayList<Directory>();
        activePath.add(root);

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(data);

            if (doc.hasChildNodes()) {
                for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                    read(doc.getChildNodes().item(i), "", root);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Resets activePath to just the root Directory ("/").
     */
    public void resetActivePath () {
        activePath.clear();
        activePath.add(root);
    }

    /**
     * Sets the activePath to the specified value. Extensive internal use. Method
     * will not change activePath if the path is not valid for the XML file.
     * 
     * @param pathName
     *        specifies the new path. Directories are separated by /'s.
     */
    public void setActivePath (String pathName) {
        List<Directory> path = new ArrayList<Directory>();
        String[] newPath = pathName.split("/");
        Directory current = root;
        for (int i = 1; i < newPath.length; i++) {
            String directoryName = newPath[i];
            if (!current.getSubDirectories().contains(new Directory(directoryName))) {
                return;
            }
            current = current.getDirectory(directoryName);
            path.add(current);
        }
        resetActivePath();
        activePath.addAll(path);
    }

    /**
     * Returns the activePath for internal and debugging purposes.
     * 
     * @return
     *         the activePath in the form of a String.
     */
    public String getActivePath () {
        String path = "";
        for (Directory directory : activePath) {
            path += directory.toString() + "/";
        }
        return path;
    }

    /**
     * Finds all of the sub-Directories of the current activePath. Useful for interpreting
     * the parsed XML file.
     * 
     * @return
     *         a List of all of the possible sub-Directories the user could move into next.
     */
    public List<String> getValidSubDirectories () {
        List<String> subDirectories = new ArrayList<String>();
        for (Directory directory : activePath.get(activePath.size() - 1).getSubDirectories()) {
            subDirectories.add(directory.toString());
        }
        return subDirectories;
    }
    
    public List<String> getValidSubDirectories (String prefix) {
        List<String> subDirectories = getValidSubDirectories();
        subDirectories.removeIf(dir -> !dir.startsWith(prefix));
        return subDirectories;
    }

    /**
     * Finds all of the valid labels within the current activePath. Also useful for interpreting
     * the parser XML file.
     * 
     * @return
     *         a List of all of the Map keys that begin with the activePath but are only labels not
     *         directories.
     */
    public List<String> getValidLabels () {
        List<String> labels = new ArrayList<String>();
        String prefix = getActivePath();
        for (String key : xml.keySet()) {
            if (key.startsWith(prefix) && key.split("/").length == prefix.split("/").length + 1) {
                labels.add(key);
            }
        }
        return labels;
    }

    /**
     * Adds to the activePath from the current Directory. The user can either put
     * the entire path in as one String with Directories separated by /'s or
     * each Directory name can be put as a distinct parameter.
     * 
     * @param path
     *        is either the entire path or an Array representing each step of the path.
     */
    public void moveDown (String ... path) {
        String pathName = "";
        for (String component : path) {
            pathName += component + "/";
        }
        setActivePath(getActivePath() + pathName);
        System.out.println(activePath);
    }

    /**
     * Moves the activePath up one level.
     */
    public void moveUp () {
        moveUp(1);
    }

    /**
     * Moves the activePath up by the specified amount.
     * 
     * @param distance
     *        the number of levels to move up.
     */
    public void moveUp (int distance) {
        List<Directory> remove = new ArrayList<Directory>();
        for (int i = activePath.size() - 1; i > activePath.size() - 1 - distance && i >= 0; i--) {
            remove.add(activePath.get(i));
        }
        activePath.removeAll(remove);
    }

    /**
     * Finds the value associated with the label. If the label isn't found within
     * the current directory, then the method checks to see if label is an entire
     * distinct path. This allows the user to decide whether or not to use the
     * activePath functionality.
     * 
     * @param label
     *        either represents a label with the activePath or an entire path, user choice.
     * 
     * @return
     *         The value associated with the label by the XML file.
     */
    public String getValue (String label) {
        String ret;
        if ((ret = xml.get(getActivePath() + label)) != null) {
            return ret;
        }
        else {
            return getPathValue(label);
        }
    }

    /*
     * Used by getValue(String) to find a value of an entire path.
     */
    private String getPathValue (String path) {
        return xml.get(path);
    }

    /*
     * Recursively builds the Directory tree and populates the map.
     */
    private void read (Node node, String path, Directory parent) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            return;
        }
        xml.put(path + "/" + node.getNodeName(), node.getTextContent());
        Directory child = new Directory(node.getNodeName());
        parent.addSubDirectory(child);
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            read(node.getChildNodes().item(i), path + "/" + node.getNodeName(), child);
        }
        // else if (node.getChildNodes().getLength() == 0 && !node.getNodeName().equals("#text")) {
        // Directory child = new Directory(node.getNodeName());
        // parent.addSubDirectory(child);
        // xml.put(path + "/" + node.getNodeName(), "");
        // }
        // else if (!node.getNodeName().equals("#text")) {
        // Directory child = new Directory(node.getNodeName());
        // parent.addSubDirectory(child);
        // for (int i = 0; i < node.getChildNodes().getLength(); i++) {
        // read(node.getChildNodes().item(i), path + "/" + node.getNodeName(), child);
        // }
        // }
    }
    


}
