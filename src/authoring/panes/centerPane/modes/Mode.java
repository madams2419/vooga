package authoring.panes.centerPane.modes;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import authoring.dataEditors.Sprite;


/**
 * 
 * @author Natalie
 *
 */
public interface Mode {

    public void imageDragged (MouseEvent a, Sprite s);

    public Mode getToggle ();

    public void mousePressed (MouseEvent click);

    public void mouseReleased (MouseEvent click, Sprite sprite, Group group);

}
