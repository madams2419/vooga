import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
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

/** Example of how a cubic curve works, drag the anchors around to change the curve. */
public class PathCreator extends Application{
    private Group myGroup;
    
// public PathCreator(Group group){
//     myGroup = group;
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
         @Override public void handle(MouseEvent click) {
           // record a delta distance for the drag and drop operation.
             startx = click.getX();
             starty = click.getY();
             
             scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                 @Override public void handle(MouseEvent click) {
                     double endx = click.getX();
                     double endy = click.getY();
                     Path path = new Path();
                    ArrayList<Node> elements = path.createPathElements(startx, starty, endx, endy);
                     group.getChildren().addAll(elements);
                 }
               });
         }
       });
     
     stage.setScene(scene);
     stage.show();
   }
 }


//public static void main(String[] args){
//    Stage stage = new Stage();
//    stage.setTitle("Cubic Curve Manipulation Sample");
//    Group group = new Group();
//    PathCreator pc = new PathCreator(group);
//    Scene scene = new Scene(group);
//    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//        double startx;
//        double starty;
//        @Override public void handle(MouseEvent click) {
//          // record a delta distance for the drag and drop operation.
//            startx = click.getX();
//            starty = click.getY();
//            
//            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
//                @Override public void handle(MouseEvent click) {
//                    double endx = click.getX();
//                    double endy = click.getY();
//                    Path path = new Path();
//                    CubicCurve curve = path.createPath(startx, starty, endx, endy);
//                    group.getChildren().add(curve);
//                    group.getChildren().addAll(path.createNode(Color.PALEGREEN, curve.startXProperty(),curve.startYProperty()),
//                                               path.createNode(Color.GOLD,      curve.controlX1Property(), curve.controlY1Property()),
//                                               path.createNode(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property()),
//                                               path.createNode(Color.TOMATO,    curve.endXProperty(),      curve.endYProperty()));
//                }
//              });
//        }
//      });
//    
//    stage.setScene(scene);
//    stage.show();
//  }



