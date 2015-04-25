package authoring.pathEditor;

import java.util.ArrayList;
import java.util.List;
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
 *
 */

public class PathCreator extends Application {
    private Group myGroup;
    private List<PathTransition> myPaths = new ArrayList<PathTransition>();

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
//                                System.out.println(((Anchor) click.getTarget()).getBoundsInParent());
//                                System.out.println(element);
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

                        group.getChildren().add(path.getPath());
                        group.getChildren().addAll(elements);
                        
                        // Here to test line following
                        CubicCurve currentPath = path.getPath();
                        Path pathFollow = new Path();
                        pathFollow.getElements().addAll(createFollowable(currentPath));
                        Circle circle = new Circle(15);
                        group.getChildren().addAll(circle);
                        PathTransition trans = initializePath(circle,pathFollow,2);
                       
                        myPaths.add(trans);
                        System.out.println("follow");
                    }
                });
                
            }
        });
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle (KeyEvent press) {
                myPaths.forEach(path -> {
                       path.play();});
            }
        });

        stage.setScene(scene);
        stage.show();
    }


public ArrayList<PathElement> createFollowable(CubicCurve path){
    ArrayList<PathElement> pathEl = new ArrayList<>();
    MoveTo start = new MoveTo(path.getStartX(),path.getStartY());
    CubicCurveTo lineFollow = new CubicCurveTo();
    lineFollow.setX(path.getStartX());
    lineFollow.setY(path.getStartY());
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

public PathTransition initializePath(Shape shape, Path path, int seconds){
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

// public static void main(String[] args){
// Stage stage = new Stage();
// stage.setTitle("Cubic Curve Manipulation Sample");
// Group group = new Group();
// PathCreator pc = new PathCreator(group);
// Scene scene = new Scene(group);
// scene.setOnMousePressed(new EventHandler<MouseEvent>() {
// double startx;
// double starty;
// @Override public void handle(MouseEvent click) {
// // record a delta distance for the drag and drop operation.
// startx = click.getX();
// starty = click.getY();
//
// scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
// @Override public void handle(MouseEvent click) {
// double endx = click.getX();
// double endy = click.getY();
// Path path = new Path();
// CubicCurve curve = path.createPath(startx, starty, endx, endy);
// group.getChildren().add(curve);
// group.getChildren().addAll(path.createNode(Color.PALEGREEN,
// curve.startXProperty(),curve.startYProperty()),
// path.createNode(Color.GOLD, curve.controlX1Property(), curve.controlY1Property()),
// path.createNode(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property()),
// path.createNode(Color.TOMATO, curve.endXProperty(), curve.endYProperty()));
// }
// });
// }
// });
//
// stage.setScene(scene);
// stage.show();
// }

