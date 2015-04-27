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
 *         Class to manage Transitions for all sprites
 */
public class TransitionManager {
    private Group myGroup;
    private Map<Sprite, List<PathElement>> myTransitionMap;
    private List<SequentialTransition> myTransitions;
    private List<Sprite> mySprites;
    private List<String[]> myParams;

    private ArrayList<Integer> myDuration;
    private ArrayList<Integer> myDelay;

    public TransitionManager (Group group,
                              ArrayList<Sprite> sprites,
                              ArrayList<String[]> params,
                              ArrayList<Integer> duration,
                              ArrayList<Integer> delay) {
        myGroup = group;
        myTransitionMap = new HashMap<Sprite, List<PathElement>>();
        myTransitions = new ArrayList<SequentialTransition>();
        mySprites = sprites;
        myParams = params;
        myDuration = duration;
        myDelay = delay;
    }
    
    /**
     * method getParams
     * @return The list of parameters for all the sprites which include path following
     */
    public List<String[]> getParams () {
        return myParams;
    }

    /**
     * method getTransitions
     * @return the list of transitions that need to be executed
     */
    public List<SequentialTransition> getTransitions () {
        return myTransitions;
    }
    
    //Create all the transitions and add to list
    /**
     * method initialize
     * initializes elements of the path and makes appropriate calls to 
     * create all transitions
     */
    public void initialize () {

        for (int i = 0; i < mySprites.size(); i++) {

            createTransitions(mySprites.get(i), myParams.get(i));
            Sprite sprite = mySprites.get(i);
            myGroup.getChildren().add(sprite.getImageView());
            Path path = new Path();
            path.getElements().addAll(myTransitionMap.get(sprite));
            sprite.getPhysicsObject().ignorePhysics(sprite.getAnimation());
            SequentialTransition pt =
                    initializePath((Node) sprite.getImageView(), path, myDuration.get(i),
                                   myDelay.get(i));
            myTransitions.add(pt);

            System.out.println(myParams.get(i).length);
            for (int count = 0; count < myParams.get(i).length; count++) {
                System.out.println(myParams.get(i)[count]);
            }
        }
    }

    /**
     * method playTransitions
     * plays the transition in the list contained in transition manager
     */
    public void playTransitions () {

        myTransitions.forEach(transition -> {
            transition.play();
        });
    }

    /**
     * method initializePath
     * @param object the object to be following the path
     * @param path the path to be followed
     * @param seconds the duration to complete one length of the path
     * @param delay the time to pause before each path restarts
     * @return a SequentialTransition that represents a pause transition and the
     *          path transition to be followed
     */
    private SequentialTransition initializePath (Node object, Path path, int seconds, int delay) {
        PathTransition pathTr = new PathTransition();
        pathTr.setDuration(Duration.seconds(seconds));
        pathTr.setPath(path);
        pathTr.setNode(object);
        pathTr.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTr.setAutoReverse(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setAutoReverse(true);

        SequentialTransition seqTran = new SequentialTransition(pause, pathTr);
        seqTran.setCycleCount(Timeline.INDEFINITE);
        seqTran.setAutoReverse(true);

        return seqTran;

    }

    /**
     * method createTransitions
     * @param sprite the sprite to link to the path
     * @param param the parameters (properties) of the curve to be created and
     *          subsequently linked to the sprite
     */
    private void createTransitions (Sprite sprite, String[] param) {
        ArrayList<PathElement> pathEl = new ArrayList<>();
        SimpleDoubleProperty[] parameters = new SimpleDoubleProperty[param.length];

        for (int i = 0; i < param.length; i++) {
            SimpleDoubleProperty temp = new SimpleDoubleProperty();
            temp.set(Double.parseDouble(param[i]));
            parameters[i] = temp;

        }

        MoveTo start = new MoveTo(parameters[0].doubleValue(), parameters[1].doubleValue());
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
