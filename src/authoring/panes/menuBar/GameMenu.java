package authoring.panes.menuBar;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Creates the menu bar for the game.
 * @author Andrew
 *
 */
public class GameMenu extends MenuBar {

	private List<Menu> myMenuList;
	
	public GameMenu(String... args){
		myMenuList = new ArrayList<>();
		for (String s : args){
			Menu m = new Menu(s);
			myMenuList.add(m);
			this.getMenus().add(m);
		}
	}
	
	public void addItemToMenu(int i, String name, EventHandler<ActionEvent> e){
		MenuItem m = new MenuItem(name);
		m.setOnAction(e);
		myMenuList.get(i).getItems().add(m);
	}
	
}
