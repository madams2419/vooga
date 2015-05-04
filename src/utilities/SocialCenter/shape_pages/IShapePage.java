//This entire file is part of my masterpiece
//Michael Lee

package utilities.SocialCenter.shape_pages;

import javafx.scene.Group;
/**
 * 
 * @author Michael Lee
 * Button array of polygon shape tiles
 * Positions of Tiles in Array should be prespecified in the implementation of IShapePage
 * Can specify any ITile to accompany it
 */

public interface IShapePage {
	//interface for defining layout of tiles
	
	/**
	 * Adds group to root
	 * @param group
	 */
	public void addGroup(Group group);
	
	/**
	 * takes params and sets centering, spacing, and size of menu
	 * @param params
	 */
	public void defineMatrix(double x, double y, double size, double offset, String tyleType);
	
	
	/**
	 * Attach CSS tags to page
	 * @param tag
	 */
	public void addTag(String tag);
	
	
	/**
	 * Add a CSS styling ID to a particular tile in the menu
	 * @param id
	 * @param position
	 */
	public void addID(String id, int position);

	public ITile getPosition(int i);
	
	public int getNumberOfTiles();
	
}
