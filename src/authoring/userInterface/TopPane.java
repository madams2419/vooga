package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Node;
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

public class TopPane extends HBox {
	//TODO fill out this badboy
	static ArrayList<Node> mButtonList=new ArrayList<Node>();
	public static Group root=new Group();


	TopPane() {
		this.getStylesheets().add("styles/top_pane.css");
	}

	public Group generateComponents(ArrayList<Map> values){
		for(int i=0; i<values.size(); i++){
			Map<String, Map> m=values.get(i);
			for(String key: m.keySet()){
				if(key.equals("Button")){	
					mButtonList.add(ButtonFactory.generateButton(m.get(key)));

				}
				if(key.equals("Dropdown")){
					DropdownFactory dFactory=new DropdownFactory();
					mButtonList.add(dFactory.generateDropdown(m.get(key)));
				}
			}
		}	
		root.getChildren().addAll(mButtonList);
		System.out.println("TopPane Drops: " + mButtonList);
		return root;
	}


}
