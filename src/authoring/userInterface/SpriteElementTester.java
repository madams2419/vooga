package src.authoring.userInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import authoring.SpriteElement;

/**
 * 
 * @author Daniel, Natalie
 *
 */
public class SpriteElementTester {

//    @Test
//    public void spriteTester1() {
//        SpriteElement sprite = new SpriteElement();
//        List<String> path = Arrays.asList(new String[]{"mario", "location", "x"});
//        sprite.addElement(path, "5");
//        sprite.writeToXMLFile();
//    }
    
//    @Test
//    public void spriteTester2() {
//        SpriteElement sprite = new SpriteElement();
//        
//        String[] xLocationPath = {"mario", "location", "x"};
//        String[] yLocationPath = {"mario", "location", "y"};
//        String[] xSpeedPath = {"mario", "speed", "x"};
//        String[] ySpeedPath = {"mario", "speed", "y"};
//        String[] values = {"1", "2", "3", "4"};
//        
//        String[][] paths = {xLocationPath, yLocationPath, xSpeedPath, ySpeedPath};
//        
//        for (int i = 0; i < paths.length; i++) {
//            sprite.addElement(Arrays.asList(paths[i]), values[i]);
//        }
//        
//        sprite.writeToXMLFile();
//    }
    
  @Test
  public void spriteTester3() {
      SpriteElement sprite = new SpriteElement();
      
      String[] xLocationPath = {"mario", "location", "x"};
      String[] yLocationPath = {"mario", "location", "y"};
      String[] xSpeedPath = {"mario", "speed", "x"};
      String[] ySpeedPath = {"mario", "speed", "y"};
      String[] values = {"1", "2", "3", "4"};
      String[] marioPath = {"mario"};
      
      String[][] paths = {xLocationPath, yLocationPath, xSpeedPath, ySpeedPath};
      
      for (int i = 0; i < paths.length; i++) {
          sprite.addElementAtPath(Arrays.asList(paths[i]), values[i]);
      }
      sprite.addAttributeAtPath(Arrays.asList(marioPath), "id", "0");
      
      sprite.writeToXMLFile();
  }

}
