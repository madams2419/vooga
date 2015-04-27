package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 * @author Mengchao
 *
 */
public class GameAuthXMLParser {
    
    public static File mFile;
    public static Map<String,String> myGameCoefs=new HashMap<String,String>();
    public static Map<Integer,Map> myLevelObjectives = new HashMap<Integer,Map>();
    private static XPath xpath;
    private static Document doc;
    public static  Map<Integer,ArrayList> onFails = new HashMap<Integer,ArrayList>();
    public static  Map<Integer,ArrayList> onCompletes = new HashMap<Integer,ArrayList>();
    public static  Map<Integer,Map> levelSpriteStates = new HashMap<Integer,Map>();
    public static  Map<Integer,Map> levelSpriteImages = new HashMap<Integer,Map>();
    public static  Map<Integer,Map> levelSpriteInfos = new HashMap<Integer,Map>();

    public static void parse(File f) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            XPathFactory xPathfactory = XPathFactory.newInstance();
            xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/game/*");
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//          System.out.println(doc.getFirstChild().getFirstChild());
            for(int temp=0;temp<nl.getLength();temp++){
                 if(countChildren(nl.item(temp))>0){
                     
                 }else{
                     myGameCoefs.put(nl.item(temp).getNodeName(),nl.item(temp).getTextContent());
                 }
            }
            parseLevelInfo();
            parseLevelObjectives();
            parseLevelSprites();
//            System.out.println(levelSpriteInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseLevelObjectives () throws XPathExpressionException {
        Map<String,Map> objectiveSpecs = new HashMap<String,Map>();
        for(int temp = 0;temp< Integer.parseInt(myGameCoefs.get("num_of_levels"));temp++){
            XPathExpression expr = xpath.compile("/game/level/level_"+temp+"/level_objectives/*");
            NodeList levelNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            ArrayList<Map> objectiveBehaviors = new ArrayList<Map>();
            ArrayList<Map> objectiveFailBehaviors = new ArrayList<Map>();
            for(int tem=0;tem<levelNodes.getLength();tem++){
                Map<Integer, ArrayList> behaviors = new HashMap<Integer, ArrayList>();
                Map<Integer, ArrayList> failBehaviors = new HashMap<Integer, ArrayList>();
                Map<String,String> objectiveSpec = new HashMap<String,String>();
                expr = xpath.compile("/game/level/level_"+temp+"/level_objectives/objective_"+tem+"/description");
                Node desc = (Node) expr.evaluate(doc, XPathConstants.NODE);
                objectiveSpec.put(desc.getNodeName(),desc.getTextContent());
//                System.out.println("at level "+temp+" objective "+tem+", "+objectiveSpecs);
                expr = xpath.compile("/game/level/level_"+temp+"/level_objectives/objective_"+tem+"/prereqs");
                Node prereqs = (Node) expr.evaluate(doc, XPathConstants.NODE);
//                System.out.println(prereqs);
                if(prereqs!=null){
                    objectiveSpec.put(prereqs.getNodeName(),prereqs.getTextContent());
                }
                objectiveSpecs.put(Integer.toString(tem),objectiveSpec);
                expr = xpath.compile("/game/level/level_"+temp+"/level_objectives/objective_"+tem+"/onComplete/behaviors/*");
                NodeList behaviorNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                ArrayList<Map> behaviorList = new ArrayList<Map>();
//                System.out.println("behaviors: "+behaviorNodes.getLength());
                for(int te=0;te<behaviorNodes.getLength();te++){
                    NodeList behaviorSpecs = behaviorNodes.item(te).getChildNodes();
                    Map<String,String> behaviorContent = new HashMap<String,String>();
                    for(int t=0;t<behaviorSpecs.getLength();t++){
                        Node thisNode = behaviorSpecs.item(t);
                        if(thisNode.getNodeType()==Node.ELEMENT_NODE){
                            behaviorContent.put(thisNode.getNodeName(), thisNode.getTextContent());
                        }
                    }
                    behaviorList.add(behaviorContent);
//                    System.out.println("Level "+temp+" Objective "+tem+" behavior list: "+behaviorList);
                    behaviors.put(tem,behaviorList);
                }
                expr = xpath.compile("/game/level/level_"+temp+"/level_objectives/objective_"+tem+"/onFailed/behaviors/*");
                NodeList failBehaviorNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                ArrayList<Map> failBehaviorList = new ArrayList<Map>();
//                System.out.println("behaviors: "+behaviorNodes.getLength());
                for(int te=0;te<failBehaviorNodes.getLength();te++){
                    NodeList behaviorSpecs = behaviorNodes.item(te).getChildNodes();
                    Map<String,String> behaviorContent = new HashMap<String,String>();
                    for(int t=0;t<behaviorSpecs.getLength();t++){
                        Node thisNode = behaviorSpecs.item(t);
                        if(thisNode.getNodeType()==Node.ELEMENT_NODE){
                            behaviorContent.put(thisNode.getNodeName(), thisNode.getTextContent());
                        }
                    }
                    failBehaviorList.add(behaviorContent);
//                    System.out.println("Level "+temp+" Objective "+tem+" behavior list: "+behaviorList);
                    failBehaviors.put(tem,failBehaviorList);
                }
//                onCompletes.put(temp, behaviors);
//                System.out.println(behaviors);
                objectiveBehaviors.add(behaviors);
                objectiveFailBehaviors.add(failBehaviors);
                
            }
            onCompletes.put(temp, objectiveBehaviors);
            onFails.put(temp, objectiveFailBehaviors);
        }
    }
    private static void parseLevelSprites () throws XPathExpressionException {
        for(int temp = 0;temp< Integer.parseInt(myGameCoefs.get("num_of_levels"));temp++){
            XPathExpression expr = xpath.compile("/game/level/level_"+temp+"/sprites/*");
            NodeList spriteNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            ArrayList<Map> objectiveFailBehaviors = new ArrayList<Map>();
            Map<Integer, ArrayList> spriteStates = new HashMap<Integer, ArrayList>();
            Map<Integer, ArrayList> spriteImages = new HashMap<Integer, ArrayList>();
            Map<Integer, ArrayList> spritePhysics = new HashMap<Integer, ArrayList>();
            for(int tem=0;tem<spriteNodes.getLength();tem++){
//                Map<Integer, ArrayList> failBehaviors = new HashMap<Integer, ArrayList>();
//                Map<String,String> objectiveSpec = new HashMap<String,String>();
                expr = xpath.compile("/game/level/level_"+temp+"/sprites/sprite_"+tem+"/animation/*");
                NodeList stateNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                ArrayList<String> spriteState = new ArrayList<String>();
                ArrayList<Map> spriteImage = new ArrayList<Map>();
                ArrayList<Map> spritePhysic = new ArrayList<Map>();
                Map<String, String> spritePathDetail = new HashMap<String, String>();
                for(int te=0;te<stateNodes.getLength();te++){
                    Map<String, String> spriteImageDetail = new HashMap<String, String>();
                    expr = xpath.compile("/game/level/level_"+temp+"/sprites/sprite_"+tem+"/animation/state_"+te+"/name");
                    Node state = (Node) expr.evaluate(doc, XPathConstants.NODE);
                    spriteState.add(state.getTextContent());
                    expr = xpath.compile("/game/level/level_"+temp+"/sprites/sprite_"+tem+"/animation/state_"+te+"/images/image_0/*");
                    NodeList imageDetails = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    for(int t=0;t<imageDetails.getLength();t++){
                        if(countChildren(imageDetails.item(t))==0){
                            spriteImageDetail.put(imageDetails.item(t).getNodeName(),imageDetails.item(t).getTextContent());
                        }
                    }
                    spriteImage.add(spriteImageDetail);
                }
                expr = xpath.compile("/game/level/level_"+temp+"/sprites/sprite_"+tem+"/path/*");
                NodeList spritePathNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for(int t=0;t<spritePathNodes.getLength();t++){
                    if(countChildren(spritePathNodes.item(t))==0){
                        spritePathDetail.put(spritePathNodes.item(t).getNodeName(),spritePathNodes.item(t).getTextContent());
                    }
                }
                Map<String, Map> spriteInfoDetail = new HashMap<String, Map>();
                spriteInfoDetail.put("path",spritePathDetail);
                expr = xpath.compile("/game/level/level_"+temp+"/sprites/sprite_"+tem+"/physics/*");
                Map<String, String> spritePhysicsDetail = new HashMap<String, String>();
                NodeList spritePhysicsNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for(int t=0;t<spritePhysicsNodes.getLength();t++){
                    if(countChildren(spritePhysicsNodes.item(t))==0){
                        spritePhysicsDetail.put(spritePhysicsNodes.item(t).getNodeName(),spritePhysicsNodes.item(t).getTextContent());
                    }
                }
                spriteInfoDetail.put("physics",spritePhysicsDetail);
                spritePhysic.add(spriteInfoDetail);
                spriteStates.put(tem, spriteState);
                spriteImages.put(tem, spriteImage);
                spritePhysics.put(tem, spritePhysic);
          }
          levelSpriteStates.put(temp,spriteStates);
          levelSpriteImages.put(temp,spriteImages);
          levelSpriteInfos.put(temp,spritePhysics);
        }
    }
    private static void parseLevelInfo () throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/game/level/*");
        NodeList levels = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        myGameCoefs.put("first_level",levels.item(0).getFirstChild().getTextContent());
        myGameCoefs.put("num_of_levels",Integer.toString(levels.getLength()-1));
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
//
//  private static void traverseXML(Node node) {
//      NodeList nList = node.getChildNodes();
//      Map<String, String> newMap = new HashMap<String, String>();
//      Map<String, Map> returnMap = new HashMap<String, Map>();
//      for (int temp = 0; temp < nList.getLength(); temp++) {
//          Node nNode = nList.item(temp);
//          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//              int childrenNum = countChildren(nNode);
//              if (childrenNum > 0) {
//                  traverseXML(nNode);
//              } else {
//                  newMap.put(nNode.getNodeName(), nNode.getTextContent());
//                  Node parent = nNode.getParentNode();
//                  returnMap.put(parent.getNodeName(), newMap);
//              }
//          }
//      }
//      if (!returnMap.isEmpty()) {
//          myElements.add(returnMap);
//      }
//  }
}