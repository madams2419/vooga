package formerdefault;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 */

/**
 * @author hojeanniechung
 *
 */
public class MainPlayer implements CharacterMovement {
Circle c=new Circle(40,40,50);

	@Override
	public void moveLeft(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateX(currentLocation-1);
	}

	@Override
	public void moveRight(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateX(currentLocation+1);
	}

	@Override
	public void jump(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateY(currentLocation+1);
	}

	@Override
	public void crawl(int currentLocation) {
		// TODO Auto-generated method stub
		c=new Circle(10,10,30);
	}
}