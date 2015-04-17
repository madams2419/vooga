/**
 * This will allow the user to select from a number of images (or insert his/her
 * own image) to create a character that can be placed on the canvas.
 * 
 * @author hojeannie Chung
 *
 */

package authoring.userInterface;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	public SpriteSheet(BufferedImage image){
		this.image=image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height){
		BufferedImage img=image.getSubimage((col*60)-60,(row*60)-60,width,height);
		return img;
	}
}
