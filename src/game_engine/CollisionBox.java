package game_engine;

public class CollisionBox {
	/**
	 * Defines coordinates of the collision to the view
	 * Assumes that top left is (0,0) 
	 * Bottom right (+, +)
	 */
	private int left;
	private int top;
	private int right;
	private int bottom;
	CollisionBox(int l, int t, int r, int b){
		left = l;
		top = t;
		right = r;
		bottom = b;
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
	
	public int getLeft(){
		return left;
	}
	public int getTop(){
		return top;
	}
	public int getRight(){
		return right;
	}
	public int getBottom(){
		return bottom;
	}
	public int getWidth(){
		return right - left;
	}
	public int getHeight(){
		return bottom - top;
	}
	

}
