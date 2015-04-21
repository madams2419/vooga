import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * 
 * @author Kevin Chang
 * Class creates elements to define paths for sprites to follow
 */
public class Path{
    
    public Path(){
    
    }
    
    public ArrayList<Node> createPathElements(double startx, double starty,double endx, double endy){
        ArrayList<Node> elements = new ArrayList<>();
        CubicCurve curve = createPath(startx, starty, endx, endy);
        elements.add(curve);
        elements.add(createNode(Color.PALEGREEN, curve.startXProperty(),curve.startYProperty()));
        elements.add(createNode(Color.GOLD,      curve.controlX1Property(), curve.controlY1Property()));
        elements.add(createNode(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property()));
        elements.add(createNode(Color.TOMATO,    curve.endXProperty(),      curve.endYProperty()));
       
        return elements;
    }
    
    private CubicCurve createPath(double startx, double starty,double endx, double endy){
        double deltax = (endx-startx)/2;
        double deltay = (endy-starty)/2;
        
        double controlx = startx+deltax;
        double controly = starty+deltay;
       CubicCurve curve = new CubicCurve(startx,starty, controlx, controly, controlx, controly, endx, endy);
       curve.setStroke(Color.BLUE);
       curve.setStrokeWidth(4);
       curve.setStrokeLineCap(StrokeLineCap.ROUND);
       curve.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.1));
     
       return curve;
    }
    
    private Anchor createNode(Color color, DoubleProperty x, DoubleProperty y){
        return new Anchor(color,x,y);
    }
    
    private void combineNode(Anchor node1, Anchor node2){
        
    }
    

  // a draggable anchor displayed around a point.
  class Anchor extends Circle { 
    Anchor(Color color, DoubleProperty x, DoubleProperty y) {
      super(x.get(), y.get(), 10);
      setFill(color.deriveColor(1, 1, 1, 0.5));
      setStroke(color);
      setStrokeWidth(1.5);
      setStrokeType(StrokeType.OUTSIDE);

      x.bind(centerXProperty());
      y.bind(centerYProperty());
      enableDrag();
    }
    

    // make a node movable by dragging it around with the mouse.
    private void enableDrag() {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          // record a delta distance for the drag and drop operation.
          dragDelta.x = getCenterX() - mouseEvent.getX();
          dragDelta.y = getCenterY() - mouseEvent.getY();
          mouseEvent.consume();
        }
      });
      setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
        mouseEvent.consume();
        }
      });
      setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          double newX = mouseEvent.getX() + dragDelta.x;
          if (newX > 0 && newX < getScene().getWidth()) {
            setCenterX(newX);
          }  
          double newY = mouseEvent.getY() + dragDelta.y;
          if (newY > 0 && newY < getScene().getHeight()) {
            setCenterY(newY);
          }  
          mouseEvent.consume();
        }
      });
    }

    // records relative x and y co-ordinates.
    private class Delta { double x, y; }
  }  
}