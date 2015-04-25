package game_engine.sprite;

import game_engine.behaviors.IAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.pathEditor.BoundCubicCurve;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
/**
 * 
 * @author Kevin Chang
 * Class to manage Transitions for all sprites
 */
public class TransitionManager {
    private Map<Sprite,List<PathElement>> myTransitionMap;
    private List<PathTransition> myTransitions;
    private List<Sprite> mySprites;
    private List<String[]> myParams;
    
   public TransitionManager(ArrayList<Sprite> sprites,ArrayList<String[]> params){
       myTransitionMap = new HashMap<Sprite,List<PathElement>>();
       myTransitions = new ArrayList<PathTransition>();
       mySprites = sprites;
       myParams = params;
   }
   
   private void initialize(int seconds){
       for(int i = 0;i<mySprites.size();i++){
           createTransitions(mySprites.get(0),myParams.get(0));
       }
       
       myTransitionMap.keySet().forEach(sprite ->{
           Path path = new Path();
           path.getElements().addAll(myTransitionMap.get(sprite));
           PathTransition pt = initializePath((Node) sprite.getImageView(),path,seconds);
           myTransitions.add(pt);
       });
   }
   
   public void playTransitions(){
       myTransitions.forEach(transition ->{
           transition.play();
       });
   }
   
   private PathTransition initializePath (Node object, Path path, int seconds) {
       PathTransition pathTr = new PathTransition();
       pathTr.setDuration(Duration.seconds(seconds));
       pathTr.setPath(path);
       pathTr.setNode(object);
       pathTr.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
       pathTr.setCycleCount(Timeline.INDEFINITE);
       pathTr.setAutoReverse(true);
       return pathTr;

   }
   
   private void createTransitions(Sprite sprite, String[]param){
       ArrayList<PathElement> pathEl = new ArrayList<>();
       SimpleDoubleProperty[] parameters = new SimpleDoubleProperty[param.length];
       
       for(int i = 0;i<param.length;i++){
           SimpleDoubleProperty temp = new SimpleDoubleProperty();
          temp.set(Double.parseDouble(param[i]));
          
       }
       
       MoveTo start = new MoveTo(parameters[0].doubleValue(),parameters[1].doubleValue());
       BoundCubicCurve lineFollow = new BoundCubicCurve();
       lineFollow.setControlX1(parameters[2]);
       lineFollow.setControlY1(parameters[3]);
       lineFollow.setControlX2(parameters[4]);
       lineFollow.setControlY2(parameters[5]);
       lineFollow.setX(parameters[6]);
       lineFollow.setY(parameters[7]);
       pathEl.add(start);
       pathEl.add(lineFollow);
       
       myTransitionMap.put(sprite, pathEl);     
};

}
