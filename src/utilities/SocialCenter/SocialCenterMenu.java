package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class SocialCenterMenu {
	
	private Scene menu;
	private StackPane root = new StackPane();
	private Group hexGroup = new Group();
	private double WIDTH;
	private double HEIGHT;
	private ArrayList<HexTile> list;
	private Stage myStage;
	private String ID;
	private ProfilePage pp;

	public SocialCenterMenu(String id, double width, double height) {
		ID=id;
		WIDTH=width;
		HEIGHT=height;
		initializeHexPage();
		root.getStyleClass().add("background");
		menu.getStylesheets().add("styles/social_menu.css");
	}
	
	private void initializeHexPage(){
		menu = new Scene(root, WIDTH, HEIGHT);
		HexPage hex = new HexPage(WIDTH/2,HEIGHT/2, 90, 20);
		list = hex.getList();
		list.forEach(h-> hexGroup.getChildren().add(h.getHexagon()));
		hex.addCSS("hex");
		list.forEach(h->h.getHexagon().setFill(Color.WHITESMOKE.deriveColor(0, 1, 1, 0.5)));
		
		//hex profile page
		list.get(0).setOnMouseEnter(e->replaceHex());
		list.get(0).setOnMouseExit(e->removeImage());
		list.get(0).setOnMouseClicked(e->goProfilePage());
		

		root.getChildren().add(hexGroup);	
	}
	
	private void goProfilePage(){
		pp=new ProfilePage(ID,WIDTH,HEIGHT);
		pp.getProfileScreen(myStage);
	}
	
	//hardcoded. to be improved
	private void removeImage() {
		list.get(0).getHexagon().setFill(Color.WHITESMOKE.deriveColor(0, 1, 1, 0.5));
	}

	private void replaceHex(){
		Image profilePic = new Image("http://i.ytimg.com/vi/c_cg-2f9RUw/hqdefault.jpg");
		list.get(0).getHexagon().setFill(new ImagePattern(profilePic));
	}
	
	public void returnScene(Stage s){
		myStage = s;
		s.setScene(menu);
	}

}
