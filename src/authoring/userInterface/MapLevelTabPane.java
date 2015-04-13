package authoring.userInterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MapLevelTabPane extends AnchorPane {

	private CenterPane myCenterPane;
	
	public MapLevelTabPane(CenterPane c){
//		myCenterPane = c;
//		HBox h = new HBox();
//		Tab plusTab = new Tab("+");
//		plusTab.setContent(h);
//		this.getTabs().add(plusTab);
//		this.getTabs().add(0, new Tab("testing"));
//		plusTab.setOnSelectionChanged(e -> {
//			System.out.println("changed");
//		});
//		
//		
		    final TabPane tabs = new TabPane();
		    final Button addButton = new Button("+");

		    AnchorPane.setTopAnchor(tabs, 5.0);
		    AnchorPane.setLeftAnchor(tabs, 5.0);
		    AnchorPane.setRightAnchor(tabs, 5.0);
		    AnchorPane.setTopAnchor(addButton, 10.0);
		    AnchorPane.setRightAnchor(addButton, 10.0);

		    addButton.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		        final Tab tab = new Tab("Tab " + (tabs.getTabs().size() + 1));
		        tabs.getTabs().add(tab);
		        tabs.getSelectionModel().select(tab);
		      }
		    });
		    
		    tabs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		        @Override
		        public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		            
		        }
		    }); 

		    this.getChildren().addAll(tabs, addButton);

	}
	
}