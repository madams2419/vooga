/**
 * 
 */
package utilities.SocialCenter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class StatsPage {
	private Stage myStage;
	private Scene scoreScreen;
	private double Width;
	private double Height;
	private Group root;
	private Driver db=new Driver();
	
	private StatsPage(Stage s, double width, double height){
		myStage=s;
		Width=width;
		Height=height;
		initialize(width,height);
		
	}
	
	private void initialize(double w, double h){
		scoreScreen=new Scene(root,w,h);
	
	}
	
	private void DropDown(){
		//get the different types of games the user played
		
	}
	

}
