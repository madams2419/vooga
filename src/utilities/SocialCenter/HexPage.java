package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.scene.Group;

public class HexPage implements IShapePage {
	private ArrayList<HexTile> hexList = new ArrayList<>();
	public static final int COORDINATES = 2;
	public static final int NUMBER = 6;

	public HexPage(double centerX, double centerY, double size, double offset) {
		initializePage(centerX, centerY, size, offset);
	}

	private void initializePage(double centerX, double centerY, double size,
			double offset) {

		HexTile hex = new HexTile(centerX, centerY, size, offset);
		hexList.add(hex);

		for (int i = 0; i < NUMBER * COORDINATES; i += COORDINATES) {
			hexList.add(new HexTile(hex.getConnections()[i], hex
					.getConnections()[i + 1], size, offset));
		}

	}

	public HexTile getPosition(int position) {
		return hexList.get(position);
	}

	public void addTag(String tag) {
		hexList.forEach(hexagon -> hexagon.getHexagon().getStyleClass()
				.add(tag));
	}

	public void addID(String id, int position) {
		hexList.get(position).getHexagon().setId(id);

	}

	public void addGroup(Group group) {
		hexList.forEach(h -> group.getChildren().add(h.getHexagon()));

	}

	public int getNumberOfHexagons() {
		return NUMBER + 1;
	}

}
