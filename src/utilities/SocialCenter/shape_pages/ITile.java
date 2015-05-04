//This entire file is part of my masterpiece
//Michael Lee

package utilities.SocialCenter.shape_pages;

import javafx.scene.shape.Polygon;

/**
 * interface for tiles
 * @author bluedevilmike
 *
 */
public interface ITile {
	
	
	public Polygon getTile();
	
	public void addParams(double [] points); 
	
	public void createPolygon(double x, double y, double size);
	
	

}
