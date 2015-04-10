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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author hojeanniechung
 *
 */
public class GlobalCreationPane extends EditingPane
{
	private List<HBox> myFields = new LinkedList<>();

	public GlobalCreationPane(Scene myScene) {
		// TODO Auto-generated constructor stub
		super(myScene);
		System.out.println("Blah");
		setFields(this.getChildren(),createFieldMap());
	}
	
	public Map<String,String> createFieldMap(){
		Map<String,String> fields = new HashMap<String,String>();
		fields.put("Frame Rate", "");
		fields.put("Scroll Type", "");
		fields.put("Window Size","");
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
	}


