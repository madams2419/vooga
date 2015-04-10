package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/***
 * 
 * @author daniel and Jeannie
 *
 */

import authoring.userInterface.UIElementDistributer;

public class BottomPane extends HBox {
	//TODO fill out this badboy
	static ArrayList<Button> mButtonList=new ArrayList<Button>();
	private CenterPane myCenterPane;
	public static Group root=new Group();


	BottomPane() {
		this.getStylesheets().add("styles/top_pane.css");
	}
	
	public BottomPane(CenterPane c){
		myCenterPane = c;
	}

	public Group generateComponents(ArrayList<Map> values){
		for(int i=0; i<values.size(); i++){
			Map<String, Map> m=values.get(i);
			System.out.println(m);
			for(String key: m.keySet()){
				if(key.equals("Button")){	
					mButtonList.add(ButtonFactory.generateButton(m.get(key)));
				}
				if(key.equals("Dropdown")){
					DropdownFactory dFactory=new DropdownFactory();
					dFactory.generateDropdown(m.get(key));
				}
			}
		}	
		
		root.getChildren().addAll(mButtonList);
		System.out.println("BottomPane Buttons: " + mButtonList);
		
		//root.getChildren().add(new MapLevelTabPane(myCenterPane));
		return root;

	}


}
