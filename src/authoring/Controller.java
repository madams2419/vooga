//package authoring;
//import java.util.Arrays;
//import java.util.Stack;
//
///**
// * 
// * @author Natalie
// *
// */
//
//public class Controller {
//    SpriteManager mySpriteManager;
//
//    void createSprite (double x, double y, Stack<String> path, Object... args) {
//        SpriteElement newSprite = new SpriteElement();
//       
//    }
//    
//    void addElement (String spriteIDorName, String value, String ... path) {
//        SpriteElement sprite = mySpriteManager.getSprite(spriteIDorName);
//        sprite.addElementAtPath(Arrays.asList(path), value);
//    }
//
////    void onXSpeedSet (String spriteIDorName, String xSpeedString, String xSpeed) {
////        addElement(spriteIDorName, xSpeedString, xSpeed);
////    }
////    
////    void onHealthSet (String spriteIDorName, String healthString, String health) {
////        addElement(spriteIDorName, healthString, health);
////    }
////
////    void onYGravityAccelerationSet (String spriteIDorName,
////                                    String gravityString,
////                                    String accelerationString,
////                                    String yString,
////                                    String yGravityAccelerationValue) {
////        SpriteElement sprite = mySpriteManager.get(spriteIDorName);
////        String[] path = { gravityString, accelerationString, yString };
////        sprite.addElement(Arrays.asList(path), yGravityAccelerationValue);
////    }
//
//}
