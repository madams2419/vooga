package game_engine.sprite;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;

public class TransitionObject implements ITransition {
    
        private SequentialTransition transition;
        private List<PathElement> pathElements;
        
    public TransitionObject(){
        pathElements = new ArrayList<>();
    }

    @Override
    public SequentialTransition getTransition () {
        return this.transition;
    }

    @Override
    public void setTransition (SequentialTransition transition) {
        // TODO Auto-generated method stub
        this.transition = transition;
    }

    @Override
    public void addTransition (Transition transition) {
       this.transition.getChildren().add(transition);      
    }

    @Override
    public void removeTransition (Transition transition) {
       this.transition.getChildren().remove(transition); 
    }

    @Override
    public void setNode (Node transitionFollower) {
        this.transition.setNode(transitionFollower);  
    }
    
    public List<PathElement> getPathElements(){
        return pathElements;
    }

    @Override
    public SequentialTransition initializePath (Path path, int seconds, int delay) {
        
        PathTransition pathTr = new PathTransition();
        pathTr.setDuration(Duration.seconds(seconds));
        pathTr.setPath(path);
        pathTr.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTr.setAutoReverse(true);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setAutoReverse(true);

       SequentialTransition seqTran = new SequentialTransition(pause,pathTr);
       seqTran.setCycleCount(Timeline.INDEFINITE);
       seqTran.setAutoReverse(true);
       
        return seqTran;
    }

    @Override
    public void createTransition (Sprite sprite, String[] param) {
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
        
        pathElements.addAll(pathEl);
        
    }
    
}
