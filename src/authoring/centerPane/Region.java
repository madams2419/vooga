package authoring.centerPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Region extends StackPane {
	private Rectangle myRectangle;
	private ImageView myImageView;
	
	public Region(double x, double y, Color c){
		myRectangle = new Rectangle(x, y, c);
		this.getChildren().add(myRectangle);
	}
	
	public void setBackgroundImage(Image i){
		if (myImageView != null){
			this.getChildren().remove(myImageView);
			myImageView = new ImageView(i);
		}
		else{
			myImageView = new ImageView(i);
			this.getChildren().add(myImageView);
		}
		
	}
	
}
