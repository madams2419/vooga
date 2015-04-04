package authoring_UserInterface;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;

public class CenterPane extends ScrollPane {

	CenterPane() {
		Canvas c = new Canvas(400,400);
		c.getGraphicsContext2D().setStroke(Color.BLACK);
		c.getGraphicsContext2D().strokeLine(0, 0, 400, 400);
		this.setContent((new Canvas(400,400)));
		
	}
	
	
}
