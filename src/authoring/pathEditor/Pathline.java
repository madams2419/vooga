package authoring.pathEditor;
import java.util.ArrayList;
import java.util.List;
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
public class Pathline{
    private CubicCurve myPath;
    private List<PathElement> myPathEl;
    
    public Pathline(){
       myPathEl = new ArrayList<>();
    }
    
    public ArrayList<Anchor> createPathElements(double startx, double starty,double endx, double endy){
        ArrayList<Anchor> elements = new ArrayList<>();
        CubicCurve curve = createPath(startx, starty, endx, endy);
        myPath = curve;
        elements.add(createNode(Color.PALEGREEN, curve.startXProperty(),curve.startYProperty(),curve));
        elements.add( new ControlAnchor(Color.GOLD,      curve.controlX1Property(), curve.controlY1Property(),curve));
        elements.add(new ControlAnchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),curve));
        elements.add(createNode(Color.TOMATO,    curve.endXProperty(),      curve.endYProperty(),curve));
        return elements;
    }
    
    public CubicCurve getPath(){
        return myPath;
    }
    
    public List<PathElement> getPathEl(){
        return myPathEl;
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
    
    private Anchor createNode(Color color, DoubleProperty x, DoubleProperty y,CubicCurve path){
        return new Anchor(color,x,y,path);
    }
    
    public void combineNode(Anchor node1, Anchor node2){
        node2.setCenterX(node1.getCenterX());
        node2.setCenterY(node1.getCenterY());
    }
}