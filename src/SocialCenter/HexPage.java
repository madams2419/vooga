package SocialCenter;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class HexPage {
	private ArrayList<HexTile> hexList = new ArrayList<>();
	public static final int COORDINATES = 2;
	public static final int NUMBER = 6;

	public HexPage(double centerX, double centerY, double size) {
		initializePage(centerX,centerY, size);
	}
	
	private void initializePage(double centerX, double centerY, double size){

		HexTile hex = new HexTile(centerX, centerY,size);
		hexList.add(hex);
		
		for(int i = 0; i < NUMBER*COORDINATES; i +=COORDINATES){
			hexList.add(new HexTile(hex.getConnections()[i],hex.getConnections()[i+1], size));
		}

	}
	
	public void addHandler(EventHandler<MouseEvent> e, int position){
		hexList.get(position).setHandler(e);
	}

}
