package authoring.pathEditor;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
/**
 * 
 * @author Kevin Chang
 *      Class that marks certain curve property points
 */

public class Anchor extends Circle {
    private CubicCurve myPath;
    Anchor(Color color, DoubleProperty x, DoubleProperty y,CubicCurve path) {
        super(x.get(), y.get(), 10);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(1.5);
        setStrokeType(StrokeType.OUTSIDE);
        x.bind(centerXProperty());
        y.bind(centerYProperty());
        myPath = path;
        enableDrag();
      }
    
    public CubicCurve getPath(){
        return myPath;
    }
    
    public void combineNode(Anchor node2){
        this.setCenterX(node2.getCenterX());
        this.setTranslateX(getTranslateX());
        this.setCenterY(node2.getCenterY());
        this.setTranslateY(getTranslateY());
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
//          mouseEvent.consume();
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

