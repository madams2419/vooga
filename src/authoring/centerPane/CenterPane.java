package authoring.centerPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.ListChangeListener.*;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import authoring.userInterface.AuthoringWindow;
import authoring.userInterface.WindowPane;
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
		FrontEndUtils.setKeyActions(this.myContainer);
		((TabPane) myContainer).setSide(Side.BOTTOM);
		levelSelectionModel = ((TabPane) myContainer).getSelectionModel();
		myMaps = new HashMap<>();
		initLevelsListener();
		addLevel();
		System.out.printf("Instantiated %s%n", this.getClass().getName());
	}

	private void initLevelsListener() {
		myLevels = ((TabPane) myContainer).getTabs();
		myLevels.addListener((
				javafx.collections.ListChangeListener.Change<? extends Tab> c) -> {
			while (c.next()) {
				c.getRemoved().forEach(
						tab -> myMaps.remove((TabPane) tab.getContent()));
				c.getAddedSubList().forEach(
						tab -> myMaps.put((TabPane) tab.getContent(),
								((TabPane) tab.getContent()).getTabs()));
				System.out.println(myMaps.toString());
			}
		});
	}

	private void addTab() {
		TabPane t;
		((TabPane) myContainer).getTabs().add(
				new Tab(String.format("Level %d", myLevels.size()),
						t = new TabPane()));
		t.setSide(Side.BOTTOM);
		addMap(t);
		// myMaps.put(t, t.getTabs());
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
		return (TabPane) ((TabPane) myContainer).getTabs()
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

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<List<CenterCanvas>> getMaps() {
		// return myLevels.iterator();
		return null;
	}

}
