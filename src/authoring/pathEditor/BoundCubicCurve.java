package authoring.pathEditor;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.CubicCurveTo;
/**
 * 
 * @author Kevin Chang
 * Class for Path Transitions to bind Transition curve properties to curve shape properties
 */
public class BoundCubicCurve extends CubicCurveTo{

    public BoundCubicCurve(){
        super();
      
    }
    
   
    public void setX(DoubleProperty x){
        super.setX(x.doubleValue());
        this.xProperty().bindBidirectional(x);
    }
    
   public void setY(DoubleProperty y){
       super.setY(y.doubleValue());
       this.yProperty().bindBidirectional(y);
   }
   
   public void setControlX1(DoubleProperty x1){
       super.setControlX1(x1.doubleValue());
       this.controlX1Property().bindBidirectional(x1);
   }
   
   public void setControlY1(DoubleProperty y1){
       super.setControlY1(y1.doubleValue());
       this.controlX1Property().bindBidirectional(y1);
   }
   
   public void setControlX2(DoubleProperty x2){
    super.setControlX1(x2.doubleValue());
    this.controlX2Property().bindBidirectional(x2);
   }
   
   public void setControlY2(DoubleProperty y2){
       super.setControlY2(y2.doubleValue());
       this.controlX1Property().bindBidirectional(y2);
   }
}
