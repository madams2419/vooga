package game_engine.sprite;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.Path;

public interface ITransition {
    public SequentialTransition getTransition();
    
    public void setTransition(SequentialTransition transition);
    
    public void addTransition(Transition transition);
    
    public void removeTransition(Transition transition);
    
    public void setNode(Node transitionFollower);
    
    public SequentialTransition initializePath (Path path, int seconds,int delay);
    
    public void createTransition(Sprite sprite, String[]param);
}
