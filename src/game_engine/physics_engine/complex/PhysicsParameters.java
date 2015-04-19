package game_engine.physics_engine.complex;

public class PhysicsParameters {
	private double myX;
	private double myY;
	private double myPreviousX;
	private double myPreviousY;
	private double myverticalAccelerationeration;
	private double myhorizontalAccelerationeration;
	private double myVerticalVelocity;
	private double myhorizontalVelocity;
	private String myState;
	
	public PhysicsParameters(double x, double y,
			double verticalAcceleration, double horizontalAcceleration,
			double verticalVelocity, double horizontalVelocity,
			String state){
		myX = x;
		myY = y;
		myverticalAccelerationeration = verticalAcceleration;
		myhorizontalAccelerationeration = horizontalAcceleration;
		myVerticalVelocity = verticalVelocity;
		myhorizontalVelocity = horizontalVelocity;
		myState = state;
	}
	
	// getters/setters
	protected void setState(String state){
		myState = state;
	}
	protected String getState(){
		return myState;
	}
	protected double getX(){
		return myX;
	}
	protected void setX(double x){
		myPreviousX = myX;
		myX = x;
	}
	
	protected double getY(){
		myPreviousY = myY;
		return myY;
	}
	protected void setY(double y){
		myY = y;
	}
	
	protected double getPreviousX(){
		return myPreviousX;
	}
	protected double getPreviousY(){
		return myPreviousY;
	}
	
	protected double getVerticalAcceleration(){
		return myverticalAccelerationeration;
	}
	protected void setVerticalAcceleration(double verticalAcceleration){
		myverticalAccelerationeration = verticalAcceleration;
	}
	
	protected double getVerticalVelocity(){
		return myVerticalVelocity;
	}
	protected void setVerticalVelocity(double verticalVelocity){
		myverticalAccelerationeration = verticalVelocity;
	}
	
	protected double getHorizontalAcceleration(){
		return myhorizontalAccelerationeration;
	}
	protected void setHorizontalAcceleration(double horizontalAcceleration){
		myhorizontalAccelerationeration = horizontalAcceleration;
	}
	
	protected double getHorizontalVelocity(){
		return myhorizontalVelocity;
	}
	protected void setHorizontalVelocity(double horizontalVelocity){
		myhorizontalVelocity = horizontalVelocity;
	}
	
	
}
