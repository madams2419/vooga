package game_engine.collision;

public class CollisionBox {
	/**
	 * Defines coordinates of the collision to the view
	 * Assumes that top left is (0,0)
	 * Bottom right (+, +)
	 */
	private double left;
	private double top;
	private double right;
	private double bottom;
	CollisionBox(double highLeft, double highTop, double lowRight, double lowBot){
		left = highLeft;
		top = highTop;
		right = lowRight;
		bottom = lowBot;
	}

	public void setLeft(int value){
		left = value;
	}

	public void setTop(int value){
		top = value;
	}
	public void setRight(int value){
		right = value;
	}
	public void setBottom(int value){
		bottom = value;
	}

	public double getLeft(){
		return left;
	}
	public double getTop(){
		return top;
	}
	public double getRight(){
		return right;
	}
	public double getBottom(){
		return bottom;
	}
	public double getWidth(){
		return right - left;
	}
	public double getHeight(){
		return bottom - top;
	}


}
