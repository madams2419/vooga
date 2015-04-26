package authoring.panes.centerPane.modes;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurve;
import authoring.dataEditors.Sprite;
import authoring.pathEditor.Anchor;
import authoring.pathEditor.Pathline;


/**
 * 
 * @author Kevin, Natalie
 *
 */

public class PathEditorMode implements Mode {
    private EventHandler<?> pathRemover;
    private CubicCurve myPath;
    private Sprite mySprite;
    private double startx, starty;
    
    @Override
    public void imageDragged (MouseEvent event, Sprite sprite) {
        // do nothing
    }

    @Override
    public void mousePressed (MouseEvent click) {
        if (pathRemover != null) {
            pathRemover.handle(null);
        }
        startx = click.getX();
        starty = click.getY();
    }
    
    @Override
    public void mouseReleased (MouseEvent click, Sprite sprite, Group group) {
        {
            double endx = click.getX();
            double endy = click.getY();
            mySprite = sprite;
            Pathline path = new Pathline();
            ArrayList<Anchor> elements =
                    path.createPathElements(startx, starty, endx, endy);

            group.getChildren().add(path.getPath());
            group.getChildren().addAll(elements);
            setPathRemover(group, elements, path.getPath());
            myPath = path.getPath();
        }
    }

    public String[] createParams (CubicCurve path) {
        String[] params = new String[8];
        params[0] = (Double.toString(path.getStartX()));
        params[1] = (Double.toString(path.getStartY()));
        params[2] = (Double.toString(path.getControlX1()));
        params[3] = (Double.toString(path.getControlY1()));
        params[4] = (Double.toString(path.getControlX2()));
        params[5] = (Double.toString(path.getControlY2()));
        params[6] = (Double.toString(path.getEndX()));
        params[7] = (Double.toString(path.getEndY()));
        for (int count = 0; count < params.length; count++) {
            System.out.print(params[count] + "\t");
        }
        System.out.println();
        return params;
    }
    
    private void setPathRemover (Group group, List<Anchor> elements, CubicCurve path) {
        pathRemover = e -> removePathFromScene(group, elements, path);
    }
    
    private void removePathFromScene (Group group, List<Anchor> elements, CubicCurve path) {
        group.getChildren().remove(path);
        group.getChildren().removeAll(elements);
    }
    
    @Override
    public Mode getToggle () {
        mySprite.setPath(createParams(myPath));
        pathRemover.handle(null);
        return new DefaultMode();
    }
}
