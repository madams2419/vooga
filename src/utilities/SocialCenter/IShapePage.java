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
	
	//Handler methods
	public void addClickHandler(EventHandler<MouseEvent> e, int position);

	public void addEnterHandler(EventHandler<MouseEvent> e, int position);
	
	public void setOnMouseExit(EventHandler<MouseEvent> e, int position);
	
	
	//CSS methods
	public void addTag(String tag);
	
	public void addID(String id, int position);
	
}
