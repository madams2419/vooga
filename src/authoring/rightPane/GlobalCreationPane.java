/**
 * 
 */
package authoring.rightPane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author hojeanniechung
 *
 */
public class GlobalCreationPane extends EditingPane
{
	public List<HBox> myFields = new LinkedList<>();
	public Map<String,String> fields;
	

	public GlobalCreationPane(Scene myScene) {
		// TODO Auto-generated constructor stub
		super(myScene);
		/*Default Map*/
		createDefaultMap();
		setFields(this.getChildren(),updateMap());
		Button c = new Button("Update");	
		c.setOnAction(e -> updateMap());
		this.getChildren().add(c);
		System.out.println("blah is " + myFields.toString());
	}	
	
	private Map<String,String> createDefaultMap(){
		String[] key={"Scrolling Speed","Frame Rate","Scrolling Size"};
		String[] value={"Default","Default","Default"};
		fields=new HashMap<String,String>();
		for(int i=0; i<key.length; i++){
			fields.put(key[i],value[i]);
		}
		return fields;
	}

	private void setFields(ObservableList<Node> parent,
			Map<String, String> fields){
		fields.forEach((label, value) -> {
			HBox h = new HBox(5);
			h.getChildren().addAll(new Text(label),
					new javafx.scene.control.TextField(value));
			parent.add(h);
			myFields.add(h);
		});
	}

	private Map<String, String> updateMap() {
		//		System.out.println();
		
		myFields.forEach(hbox -> {
			String s, t;
			fields.put((s=((Text)hbox.getChildren().get(0)).getText()), (t=((TextField)hbox.getChildren().get(1)).getText()));
		});
		return fields;
		//		System.out.println(sprite.getCharacteristics().toString());
	}
}


