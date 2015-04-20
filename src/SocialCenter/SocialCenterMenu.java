package SocialCenter;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;

public class SocialCenterMenu {
	
	private Scene menu;
	private Group root = new Group();
	private static final int TEMPWIDTH = 800;
	private static final int TEMPHEIGHT = 600;

	public SocialCenterMenu() {
		initializeScene();
	}
	
	private void initializeScene(){
		menu = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
//		Polygon polygon = new Polygon();
//		polygon.getPoints().addAll(new Double[]{
//		    0.0, 0.0,
//		    20.0, 10.0,
//		    10.0, 20.0 });
		ArrayList<HexTile> hexList = new ArrayList<>();
		HexTile hex = new HexTile(500,300,50);
//		HexTile hex2 = new HexTile(75.98076211353317, 105.0, 30);

		for(int i = 0; i < 12; i +=2){
			hexList.add(new HexTile(hex.getConnections()[i],hex.getConnections()[i+1], 50));
		}
		ArrayList<Polygon> polyList = new ArrayList<>();
		for (HexTile h: hexList){
			polyList.add(h.getHexagon());
		}
		root.getChildren().addAll(polyList);
//		root.getChildren().add(hex2.getHexagon());
	}
	
	public Scene returnScene(){
		return menu;
	}

}
