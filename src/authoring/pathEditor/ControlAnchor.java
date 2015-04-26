package authoring.pathEditor;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
/**
 * 
 * @author Kevin Chang
 * Class that marks the control points of a curve
 */
public class ControlAnchor extends Anchor {

    ControlAnchor (Color color, DoubleProperty x, DoubleProperty y, CubicCurve path) {
        super(color, x, y, path);
      
    }
    
    @Override
    public boolean intersects(Bounds bound){
        return false;   
    }
}
