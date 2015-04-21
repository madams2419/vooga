package authoring.pathEditor;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class ControlAnchor extends Anchor {

    ControlAnchor (Color color, DoubleProperty x, DoubleProperty y) {
        super(color, x, y);
      
    }
    
    @Override
    public boolean intersects(Bounds bound){
        return false;   
    }
}
