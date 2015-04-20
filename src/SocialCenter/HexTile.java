package SocialCenter;

import java.util.Arrays;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexTile {
	private Polygon hexagon;
	double[] connectionPoints;
	private static final int POINTS = 12;
	private double startAngle = 30;
	private static final int HEX = 60;

	public HexTile(double x, double y, double side) {
		double[] points = new double[12];
		connectionPoints = new double[12];
		
		for (int i = 0; i < POINTS; i +=2) {
			double newX = x + side * Math.cos(Math.toRadians(startAngle));
			double newY = y - side * Math.sin(Math.toRadians(startAngle));
			points[i] = newX;
			points[i + 1] = newY;
			startAngle += HEX;
		}
		
		for (int i = 0; i < POINTS; i +=2) {
			double newX = points[i] + side * Math.cos(Math.toRadians(startAngle+HEX));
			double newY = points[i+1] - side * Math.sin(Math.toRadians(startAngle+HEX));
			connectionPoints[i] = newX;
			connectionPoints[i + 1] = newY;
			startAngle += HEX;
		}
		System.out.println(Arrays.toString(connectionPoints));
		hexagon = new Polygon(points);
		hexagon.setFill(Color.WHITE);
		hexagon.setStroke(Color.BLACK);
	}

//	public static void main(String[] args) {
//		HexTile h = new HexTile(10, 10, 10);
//	}
	public Polygon getHexagon(){
		return hexagon;
	}
	
	public double[] getConnections(){
		return connectionPoints;
	}

}
