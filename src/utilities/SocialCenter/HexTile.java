package utilities.SocialCenter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class HexTile {
	private Polygon hexagon;
	
	double[] connectionPoints;
//	private static final int POINTS = 12;
//	private double startAngle = 30;
//	private static final int HEX = 60;
	private Constants constants=new Constants();

	public HexTile(double x, double y, double side, double offset) {
		double[] points = new double[12];
		connectionPoints = new double[12];

		for (int i = 0; i <constants.POINTS; i += 2) {
			double newX = x + side * Math.cos(Math.toRadians(constants.startAngle));
			double newY = y - side * Math.sin(Math.toRadians(constants.startAngle));
			points[i] = newX;
			points[i + 1] = newY;
			connectionPoints[i] = newX + (side+offset) * Math.cos(Math.toRadians(constants.startAngle + constants.HEX));
			connectionPoints[i + 1] = newY- (side + offset) * Math.sin(Math.toRadians(constants.startAngle + constants.HEX));;
			constants.startAngle += constants.HEX;
		}
		
		hexagon = new Polygon(points);

	}

	public Polygon getHexagon() {
		return hexagon;
	}
	public double[] getConnections() {
		return connectionPoints;
	}
	
	public void setOnMouseClicked(EventHandler<MouseEvent> e){
		hexagon.setOnMouseClicked(e);
	}
	
	public void setOnMouseEnter(EventHandler<MouseEvent> e){
		hexagon.setOnMouseEntered(e);
	}
	
	public void setOnMouseExit(EventHandler<MouseEvent> e){
		hexagon.setOnMouseExited(e);
	}

}
