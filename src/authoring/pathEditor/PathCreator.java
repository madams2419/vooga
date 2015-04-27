package authoring.pathEditor;

import game_engine.sprite.Animation;
import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;


/**
 * 
 * @author Kevin Chang
 *         Class to simulate path creation
 */

public class PathCreator extends Application {
    private HashMap<Sprite, String[]> myTransitionMap = new HashMap<Sprite, String[]>();
    private HashMap<CubicCurve, Sprite> myFollowingMap = new HashMap<CubicCurve, Sprite>();
    private HashMap<ArrayList<Anchor>, CubicCurve> myCurveMap = new HashMap<>();

    // public PathCreator(Group group){
    // myGroup = group;
    // }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start (Stage stage) throws Exception {
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
                        // for (Anchor element : elements) {

                        try {
                            Anchor key = (Anchor) click.getTarget();
                            myCurveMap.keySet().forEach(anchors -> {
                                if (anchors.contains(key)) {
                                    System.out.println("new path");
                                    CubicCurve target = myCurveMap.get(anchors);
                                    Sprite follower = (Sprite) myFollowingMap.get(target);
                                    myTransitionMap.get(target);
                                    myFollowingMap.replace(target, follower);
                                    myTransitionMap.replace(follower, createParams(target));
                                    myCurveMap.replace(anchors, target);

                                    return;
                                }
                            });
                            return;
                            //////Possibly implement path intersection here
                            // System.out.println("end");
                            // if ((((Anchor) click.getTarget()).getBoundsInParent())
                            // .intersects(element.getBoundsInParent())) {
                            // System.out.println(click.getTarget());
                            // System.out.println(element);
                            // System.out.println("Intersect");
                            // Anchor dragged = (Anchor) click.getTarget();
                            // Anchor stationary = (Anchor) element;
                            // dragged.combineNode(stationary);
                            // stationary.combineNode(dragged);
                            // return;
                            // }
                        }
                        catch (Exception e) {
                        }

                        if (!click.getSource().getClass()
                                .equals(new Scene(new Group()).getClass())) {
                            System.out.println(click.getSource().getClass());
                            System.out.println(new Scene(new Group()).getClass());
                            // break;
                        }

                        // }

                        // Create elements to test sprite path following
                        Animation anim = new Animation(50);
                        anim.associateImage("standing", "resources/images/brick.png", 0, 50, 50);

                        myCurveMap.put(elements, path.getPath());
                        Sprite temp = new Sprite(null, anim, "standing", null, 1, "id");
                        ArrayList<String[]> parameters = new ArrayList<String[]>();
                        String[] params = createParams(path.getPath());
                        parameters.add(params);

                        myTransitionMap.put(temp, params);
                        myFollowingMap.put(path.getPath(), temp);
                        group.getChildren().add(path.getPath());
                        group.getChildren().addAll(elements);

                    }
                });

            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle (KeyEvent press) {
                // Set up transition manager
//                ArrayList<Sprite> sprites = new ArrayList<>();
//                ArrayList<String[]> params = new ArrayList<>();
//
//                myTransitionMap.keySet().forEach(sprite -> {
//                    sprites.add(sprite);
//                    params.add(myTransitionMap.get(sprite));
//                });
//                TransitionManager pathManager = new TransitionManager(group, sprites, params);
//                pathManager.initialize(5);
//                pathManager.playTransitions();
                System.out.println("follow");
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public String[] createParams (CubicCurve path) {
        String[] params = new String[8];
        params[0] = (Double.toString(path.startXProperty().doubleValue()));
        params[1] = (Double.toString(path.startYProperty().doubleValue()));
        params[2] = (Double.toString(path.controlX1Property().doubleValue()));
        params[3] = (Double.toString(path.controlY1Property().doubleValue()));
        params[4] = (Double.toString(path.controlX2Property().doubleValue()));
        params[5] = (Double.toString(path.controlY2Property().doubleValue()));
        params[6] = (Double.toString(path.endXProperty().doubleValue()));
        params[7] = (Double.toString(path.endYProperty().doubleValue()));
        for (int count = 0; count < params.length; count++) {
            System.out.println(params[count]);
        }
        return params;
    }
}
