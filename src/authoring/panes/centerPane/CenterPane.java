package authoring.panes.centerPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import authoring.dataEditors.Sprite;
import authoring.panes.WindowPane;
import authoring.userInterface.AuthoringWindow;
import authoring.util.FrontEndUtils;

/**
 * 
 * @author Andrew Sun & Daniel "the fresh code machine of bel-duke" Luker
 *
 */
public class CenterPane extends WindowPane {

	private SingleSelectionModel<Tab> levelSelectionModel;

	private ObservableList<Tab> myLevels;
	private Map<TabPane, List<Tab>> myMaps;

	public CenterPane(Scene s, AuthoringWindow w) {
		super(s, new TabPane(), w);
		FrontEndUtils.setKeyActions(this.getContainer());
		((TabPane) getContainer()).setSide(Side.BOTTOM);
		levelSelectionModel = ((TabPane) getContainer()).getSelectionModel();
		myMaps = new HashMap<>();
		initLevelsListener();
		addLevel();
		System.out.printf("Instantiated %s%n", this.getClass().getName());
	}

	private void initLevelsListener() {
		myLevels = ((TabPane) getContainer()).getTabs();
		myLevels.addListener((
				javafx.collections.ListChangeListener.Change<? extends Tab> c) -> {
			while (c.next()) {
				c.getRemoved().forEach(
						tab -> myMaps.remove((TabPane) tab.getContent()));
				c.getAddedSubList().forEach(
						tab -> myMaps.put((TabPane) tab.getContent(),
								((TabPane) tab.getContent()).getTabs()));
			}
		});
	}

	private void addTab() {
		TabPane t;
		((TabPane) getContainer()).getTabs().add(
				new Tab(String.format("Level %d", myLevels.size()+1),
						t = new TabPane()));
		t.setSide(Side.BOTTOM);
		addMap(t);
	}

	public void addLevel() {
		addTab();
	}

	private void addMap(TabPane level) {
		CenterCanvas c = new CenterCanvas(myScene, myParent);
		addTab(level, String.format("Map %d", level.getTabs().size() + 1), c);
	}

	public void addMap() {
		addMap(getActiveTabPane());
	}

	public TabPane getActiveTabPane() {
		return (TabPane) ((TabPane) getContainer()).getTabs()
				.get(levelSelectionModel.getSelectedIndex()).getContent();
	}

	private Tab getActiveTab(TabPane t) {
		return t.getTabs().get(t.getSelectionModel().getSelectedIndex());
	}

	private void addTab(TabPane t, String tabName, Node content) {
		t.getTabs().add(new Tab(tabName, content));
	}

	public CenterCanvas getActiveTab() {
		return (CenterCanvas) getActiveTab(getActiveTabPane()).getContent();
	}
	
	public void removeSprite (Sprite sprite) {
	    getActiveTab().removeSprite(sprite);
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<List<CenterCanvas>> getMaps() {
		List<List<CenterCanvas>> myList = new ArrayList<>();
		myMaps.values().forEach(l -> {
			List<CenterCanvas> m = new ArrayList<>();
			l.forEach(cp -> {
				m.add((CenterCanvas) cp.getContent());
			});
			myList.add(m);
		});
		return myList;
	}

}
