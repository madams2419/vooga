package utilities.SocialCenter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
/**
 * 
 * @author Michael Lee
 * Button array of polygon shape tiles
 * Positions of Tiles in Array should be prespecified in the implementation of IShapePage
 */

public interface IShapePage {
	
	//Attach shapes to root
	public void addGroup(Group group);
	
	
	//CSS methods
	public void addTag(String tag);
	
	public void addID(String id, int position);
	
}
