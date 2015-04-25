package authoring.pathEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * 
 * @author Kevin Chang
 * Class to simulate path creation
 */

public class PathCreator extends Application {
    private Group myGroup;
    private List<PathTransition> myPaths = new ArrayList<PathTransition>();
    private HashMap<Shape, PathTransition> myTransitionMap = new HashMap<Shape,PathTransition>();
    private HashMap<CubicCurve,Shape> myFollowingMap = new HashMap<CubicCurve,Shape>();
    // public PathCreator(Group group){
    // myGroup = group;
    // }

    @Override
    public void start (Stage stage) throws Exception {
        // TODO Auto-generated method stub
        stage.setTitle("Cubic Curve Manipulation Sample");
        Group group = new Group();
        Scene scene = new Scene(group);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            double startx;
            double starty;

            @Override
            public void handle (MouseEvent click) {
                // record a delta distance for the drag and drop operation.
                startx = click.getX();
                starty = click.getY();

                scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle (MouseEvent click) {
                        double endx = click.getX();
                        double endy = click.getY();
                        Pathline path = new Pathline();
                        ArrayList<Anchor> elements =
                                path.createPathElements(startx, starty, endx, endy);

                     

                        System.out.println(click.getTarget().getClass());
                        for (Anchor element : elements) {
                          try {
                               CubicCurve target =  ((Anchor) click.getTarget()).getPath();
                               Circle follower = (Circle) myFollowingMap.get(target);
                                PathTransition transition = myTransitionMap.get(target);                  
                                myFollowingMap.remove(follower);
                                                      
                                
                                Path pathFollow = new Path();
                                
                                pathFollow.getElements().addAll(createFollowable(target));
                                 
                                PathTransition trans = initializePath(follower, pathFollow, 2);
                               
                                myTransitionMap.put(follower,trans);
                                
//                                System.out.println("end");
                                if ((((Anchor) click.getTarget()).getBoundsInParent())
                                        .intersects(element.getBoundsInParent())) {
                                    System.out.println(click.getTarget());
                                    System.out.println(element);
                                    System.out.println("Intersect");
                                    Anchor dragged = (Anchor) click.getTarget();
                                    Anchor stationary = (Anchor) element;
                                    dragged.combineNode(stationary);
                                    stationary.combineNode(dragged);
                                    return;
                                }
                            }
                            catch (Exception e) {

                            }

                            if (!click.getSource().getClass()
                                    .equals(new Scene(new Group()).getClass())) {
                                System.out.println(click.getSource().getClass());
                                System.out.println(new Scene(new Group()).getClass());
                                break;
                            }

                        }
                        
                        Path pathFollow = new Path();
                         pathFollow.getElements().addAll(createFollowable(path.getPath()));
//                        pathFollow.getElements().addAll(path.getPathEl());
                        Circle circle = new Circle(15);
                        PathTransition trans = initializePath(circle, pathFollow, 2);
                        myPaths.add(trans);
                        myTransitionMap.put(circle,trans);
                        myFollowingMap.put(path.getPath(),circle);
                        group.getChildren().add(path.getPath());
                        group.getChildren().addAll(elements);

                        System.out.println("follow");
                    }
                });

            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle (KeyEvent press) {
                myFollowingMap.keySet().forEach(curve ->{
                    group.getChildren().add(myFollowingMap.get(curve));
                });
                myTransitionMap.keySet().forEach(key -> {
                    myTransitionMap.get(key).play();
                });
            }
        });

        stage.setScene(scene);
        stage.show();
    }

     public ArrayList<PathElement> createFollowable(CubicCurve path){
     ArrayList<PathElement> pathEl = new ArrayList<>();
     MoveTo start = new MoveTo(path.getStartX(),path.getStartY());
     BoundCubicCurve lineFollow = new BoundCubicCurve();
     lineFollow.setControlX1(path.getControlX1());
     lineFollow.setControlY1(path.getControlY1());
     lineFollow.setControlX2(path.getControlX2());
     lineFollow.setControlY2(path.getControlY2());
     lineFollow.setX(path.getEndX());
     lineFollow.setY(path.getEndY());
     pathEl.add(start);
     pathEl.add(lineFollow);
    
     return pathEl;
     }
     
     

    public PathTransition initializePath (Shape shape, Path path, int seconds) {
        PathTransition pathTr = new PathTransition();
        pathTr.setDuration(Duration.seconds(seconds));
        pathTr.setPath(path);
        pathTr.setNode(shape);
        pathTr.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTr.setCycleCount(Timeline.INDEFINITE);
        pathTr.setAutoReverse(true);
        return pathTr;

    }
}
