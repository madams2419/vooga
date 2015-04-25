package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class HexPage {
	private ArrayList<HexTile> hexList = new ArrayList<>();
	public static final int COORDINATES = 2;
	public static final int NUMBER = 6;

	public HexPage(double centerX, double centerY, double size, double offset) {
		initializePage(centerX,centerY, size, offset);
	}
	
	private void initializePage(double centerX, double centerY, double size, double offset){

		HexTile hex = new HexTile(centerX, centerY,size, offset);
		hexList.add(hex);
		
		for(int i = 0; i < NUMBER*COORDINATES; i +=COORDINATES){
			hexList.add(new HexTile(hex.getConnections()[i],hex.getConnections()[i+1], size, offset));
		}

	}
	
	public void addClickHandler(EventHandler<MouseEvent> e, int position){
		hexList.get(position).setOnMouseClicked(e);
	}
	
	public void addEnterHandler(EventHandler<MouseEvent> e, int position){
		hexList.get(position).setOnMouseEnter(e);
		}
	
	public void addCSS(String tag){
		hexList.forEach(hexagon -> hexagon.getHexagon().getStyleClass().add(tag));
	}
	
	public ArrayList<HexTile> getList(){
		return hexList;
	}

}
