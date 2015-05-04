//This entire file is part of my masterpiece
//Michael Lee

package utilities.SocialCenter.shape_pages;

import javafx.scene.shape.Polygon;

public class HexTile implements ITile{
	private Polygon hexagon;
	private static final int POINTS = 12;
	private double startAngle = 30;
	private static final int HEX = 60;
	private static final int PAIR = 2;

	public HexTile(){};

	public Polygon getTile() {
		return hexagon;
	}

	public void addParams(double[] points) {
		hexagon = new Polygon(points);
		
	}

	@Override
	public void createPolygon(double x, double y, double side) {
		double[] points = new double[POINTS];

		for (int i = 0; i < POINTS; i += PAIR) {
			double newX = x + side * Math.cos(Math.toRadians(startAngle));
			double newY = y - side * Math.sin(Math.toRadians(startAngle));
			points[i] = newX;
			points[i + 1] = newY;
			startAngle += HEX;
		}
		
		hexagon = new Polygon(points);
		
	}

}
