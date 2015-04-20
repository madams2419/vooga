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
public class PathCreator extends Application {
    private CubicCurve myCurve;
    private Group myGroup;
    
    public PathCreator(){
        myCurve = new CubicCurve(50,5, 50, 90, 50, 90, 50, 100);
        myCurve.setStroke(Color.BLUE);
        myCurve.setStrokeWidth(4);
        myCurve.setStrokeLineCap(StrokeLineCap.ROUND);
        myCurve.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.1));
//        myGroup = group;
    }
    
    public CubicCurve getCurve(){
        return myCurve;
    }
    
    @Override public void start(final Stage stage) throws Exception {
        PathCreator pc = new PathCreator();
        CubicCurve curve = pc.getCurve();
        
        double xHalfway = (curve.startXProperty().getValue()-curve.endXProperty().getValue())/2;
   
        Anchor start    = new Anchor(Color.PALEGREEN, curve.startXProperty(),    curve.startYProperty());
        Anchor control1 = new Anchor(Color.GOLD,    curve.controlX2Property(), curve.controlY1Property());
        Anchor control2 = new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property());
        Anchor end      = new Anchor(Color.TOMATO,    curve.endXProperty(),      curve.endYProperty());

        stage.setTitle("Cubic Curve Manipulation Sample");
        stage.setScene(new Scene(new Group( curve, start, control1,control2, end), 400, 400, Color.ALICEBLUE));
        stage.show();
      }
    

    public Anchor createNode(Color color, DoubleProperty x, DoubleProperty y){
        return new Anchor(color,x,y);
    }
    
    public 

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
          getScene().setCursor(Cursor.MOVE);
        }
      });
      setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getScene().setCursor(Cursor.HAND);
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
        }
      });
    }

    // records relative x and y co-ordinates.
    private class Delta { double x, y; }
  }  
}