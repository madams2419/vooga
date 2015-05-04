//This entire file is part of my masterpiece
//Michael Lee

package utilities.SocialCenter.shape_pages;

import javafx.scene.shape.Polygon;

public class SquareTile implements ITile {
	private Polygon square;
	private static final int POINTS = 8;
	private double startAngle = 45;
	private static final int SQUARE = 90;
	
	public SquareTile(){};
	
	public void createPolygon(double x, double y, double side){
		double[] points = new double[POINTS];

		for (int i = 0; i < POINTS; i += 2) {
			double newX = x + side * Math.cos(Math.toRadians(startAngle));
			double newY = y - side * Math.sin(Math.toRadians(startAngle));
			points[i] = newX;
			points[i + 1] = newY;
			startAngle += SQUARE;
		}

		square = new Polygon(points);
	};

	public Polygon getTile() {

		return square;
	}

	public void addParams(double[] points) {
		square = new Polygon(points);
	}

}
