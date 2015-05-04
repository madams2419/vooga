//This entire file is part of my masterpiece
//Michael Lee
package utilities.SocialCenter.shape_pages;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

public class HexPage implements IShapePage {
	private ArrayList<ITile> TileList = new ArrayList<>();
	
	public static final int COORDINATES = 2;
	public static final int NUMBER = 6;

	private static final int POINTS = 12;
	private double startAngle = 30;
	private static final int HEX = 60;

	public HexPage(){};
	
	public void defineMatrix(double x, double y, double side, double offset, String tileType) {
		
		ITile centerTile;
		try {
			centerTile = (ITile) Class.forName(tileType).newInstance();
			centerTile.createPolygon(x, y, side);
			TileList.add(centerTile);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		for (int i = 0; i < POINTS; i += COORDINATES) {
			double newX = x + side * Math.cos(Math.toRadians(startAngle));
			double newY = y - side * Math.sin(Math.toRadians(startAngle));
			double connectionX = newX + (side+offset) * Math.cos(Math.toRadians(startAngle + HEX));
			double connectionY = newY - (side + offset) * Math.sin(Math.toRadians(startAngle + HEX));
			try {
				ITile tile = (ITile) Class.forName(tileType).newInstance();
				tile.createPolygon(connectionX, connectionY, side);
				TileList.add(tile);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
			startAngle += HEX;
		}
	}

	public ITile getPosition(int position) {
		return TileList.get(position);
	}

	public void addTag(String tag) {
		TileList.forEach(hexagon -> hexagon.getTile().getStyleClass()
				.add(tag));
	}

	public void addID(String id, int position) {
		TileList.get(position).getTile().setId(id);

	}

	public void addGroup(Group group) {
		TileList.forEach(h -> group.getChildren().add(h.getTile()));

	}

	public int getNumberOfTiles() {
		return NUMBER + 1;
	}

}
