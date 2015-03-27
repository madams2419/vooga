package game_engine;

import com.sun.javafx.geom.Rectangle;

public class HitBox {
	
	Rectangle rect;
	
	public HitBox(Rectangle rect) {
		this.rect = rect;
	}
	
	public boolean intersects(HitBox compare) {
		// TODO
		return false;
	}

}
