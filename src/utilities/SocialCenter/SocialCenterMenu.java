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
	private static final int TEMPWIDTH = 1000;
	private static final int TEMPHEIGHT = 600;
	private ArrayList<HexTile> list;
	private Stage myStage;

	public SocialCenterMenu(Stage stage) {
		myStage = stage;
		initializeHexPage();
		root.getStyleClass().add("background");
		menu.getStylesheets().add("styles/social_menu.css");
	}
	
	private void initializeHexPage(){
		menu = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
		HexPage hex = new HexPage(TEMPWIDTH/2,TEMPHEIGHT/2, 90, 20);
		list = hex.getList();
		list.forEach(h-> hexGroup.getChildren().add(h.getHexagon()));
		hex.addCSS("hex");
		list.forEach(h->h.getHexagon().setFill(Color.WHITESMOKE.deriveColor(0, 1, 1, 0.5)));
		
		//hex profile page
		list.get(0).setOnMouseEnter(e->replaceHex());
		list.get(0).setOnMouseExit(e->removeImage());
		list.get(0).setOnMouseClicked(e->myStage.setScene(new ProfilePage(null, TEMPWIDTH,TEMPHEIGHT).getProfileScreen()));
		

		root.getChildren().add(hexGroup);	
	}
	
	
	//hardcoded. to be improved
	private void removeImage() {
		list.get(0).getHexagon().setFill(Color.WHITESMOKE.deriveColor(0, 1, 1, 0.5));
	}

	private void replaceHex(){
		Image profilePic = new Image("https://photos-5.dropbox.com/t/2/AAD2CB0YPtiwv4dYembdzaCIDYgVessA942_atn-J6WWzA/12/49423891/png/1024x768/3/1429956000/0/2/Screenshot%202015-04-20%2017.07.20.png/CJPMyBcgASACIAMoAQ/4MAyNY-XzTDXejkRTerhDqfB_CUlQJsiRFBVqa2trPE");
		list.get(0).getHexagon().setFill(new ImagePattern(profilePic));
	}
	
	public Scene returnScene(){
		return menu;
	}

}
