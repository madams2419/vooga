package game_engine.sprite;

import game_engine.behaviors.IAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
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
    private Group myGroup;
    private Map<Sprite,List<PathElement>> myTransitionMap;
    private List<SequentialTransition> myTransitions;
    private List<Sprite> mySprites;
    private List<String[]> myParams;
    
   public TransitionManager(Group group, ArrayList<Sprite> sprites,ArrayList<String[]> params){
       myGroup = group;
       myTransitionMap = new HashMap<Sprite,List<PathElement>>();
       myTransitions = new ArrayList<SequentialTransition>();
       mySprites = sprites;
       myParams = params;
   }
   
   public List<String[]> getParams(){
       return myParams;
   }
   
   public List<SequentialTransition> getTransitions(){
       return myTransitions;
   }
   
   public void initialize(int seconds){
       for(int i = 0;i<mySprites.size();i++){
           
           createTransitions(mySprites.get(i),myParams.get(i));
           System.out.println(myParams.get(i).length);
          for(int count = 0;count<myParams.get(i).length;count++){
              System.out.println(myParams.get(i)[count]);
          }
       }
       
       myTransitionMap.keySet().forEach(sprite ->{
           myGroup.getChildren().add(sprite.getImageView());
           Path path = new Path();
           path.getElements().addAll(myTransitionMap.get(sprite));
           sprite.getPhysicsObject().ignorePhysics(sprite.getAnimation());
           SequentialTransition pt = initializePath((Node) sprite.getImageView(),path,seconds);
           myTransitions.add(pt);
       });
   }
   
   public void playTransitions(){
       
       myTransitions.forEach(transition ->{
           transition.play();
       });
   }
   
   private SequentialTransition initializePath (Node object, Path path, int seconds) {
       PathTransition pathTr = new PathTransition();
       pathTr.setDuration(Duration.seconds(seconds));
       pathTr.setPath(path);
       pathTr.setNode(object);
       pathTr.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
       pathTr.setAutoReverse(true);
       
       PauseTransition pause = new PauseTransition(Duration.seconds(1));
       pause.setAutoReverse(true);

      SequentialTransition seqTran = new SequentialTransition(pause,pathTr);
      seqTran.setCycleCount(Timeline.INDEFINITE);
      seqTran.setAutoReverse(true);
      
       return seqTran;

   }
   
   private void createTransitions(Sprite sprite, String[]param){
       ArrayList<PathElement> pathEl = new ArrayList<>();
       SimpleDoubleProperty[] parameters = new SimpleDoubleProperty[param.length];
       
       for(int i = 0;i<param.length;i++){
           SimpleDoubleProperty temp = new SimpleDoubleProperty();
          temp.set(Double.parseDouble(param[i]));
          parameters[i]=temp;
          
       }
    
       MoveTo start = new MoveTo(parameters[0].doubleValue(),parameters[1].doubleValue());
       CubicCurveTo lineFollow = new CubicCurveTo();
       lineFollow.setControlX1(parameters[2].doubleValue());
       lineFollow.setControlY1(parameters[3].doubleValue());
       lineFollow.setControlX2(parameters[4].doubleValue());
       lineFollow.setControlY2(parameters[5].doubleValue());
       lineFollow.setX(parameters[6].doubleValue());
       lineFollow.setY(parameters[7].doubleValue());
       pathEl.add(start);
       pathEl.add(lineFollow);
       
       myTransitionMap.put(sprite, pathEl);     
};

}
