package authoring.panes.centerPane.modes;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import authoring.dataEditors.Sprite;


/**
 * 
 * @author Natalie, Andrew
 *
 */
public class DefaultMode implements Mode {

    public void mouseDragged (MouseEvent a, Sprite s) {
        s.setXPosition(a.getSceneX() - (s.getImage().getWidth() / 2));
        s.setYPosition(a.getSceneY() - (s.getImage().getHeight() / 2));
    }

    public Mode getToggle () {
        return new PathEditorMode();
    }

    public void mousePressed (MouseEvent click) {
        // do nothing
    }

    public void mouseReleased (MouseEvent click, Sprite sprite, Group group) {
        // do nothing
    }

}
