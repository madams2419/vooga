package game_engine;


import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.collisions.CollisionsManager;
import game_engine.controls.ControlsManager;
import game_engine.objectives.Objective;
import game_engine.scrolling.WrapAround;
import game_engine.scrolling.scroller.BasicScroller;
import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.BasicFocus;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.scrolling.tracker.SpriteTracker;
import game_engine.sprite.Sprite;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;


/**
 * Level contains the information for each different game level and updates
 * 
 * @author Kevin Chang
 *
 */

public class Level implements IActor {

	private List<Objective> myObjectives;
	private ObservableList<Sprite> mySprites;
	private CollisionsManager myCollisionEngine;
	private ControlsManager myControlManager;
	private Collection<Sprite> myToBeRemoved;
	private Group myGroup;
	private Map<String, IAction> myActions;
	
	@IActionAnnotation(description = "Remove sprite", numParams = 1)
	private IAction removeSprite = (params) -> {
	        String id = params[0];
	        Sprite removed = mySprites.filtered(sprite -> sprite.checkID(id)).get(0);
	        myToBeRemoved.add(removed);
	    };

	public Level() {
		myObjectives = new ArrayList<>();
		mySprites = FXCollections.observableArrayList();
		initGroup (mySprites);
		myToBeRemoved = new ArrayList<>();
	}
	
	private void initGroup (ObservableList<Sprite> sprites) {
	    myGroup = new Group();
	    sprites.addListener((ListChangeListener<Sprite>) change -> {
	        while (change.next()) {
	            if (change.wasAdded()) {
	                change.getAddedSubList().forEach(sprite -> myGroup.getChildren().add(sprite.getImageView()));
	            }
	            if (change.wasRemoved()) {
	                change.getRemoved().forEach(sprite -> myGroup.getChildren().remove(sprite.getImageView()));
	            }
	        }
	    });
	}

	/**
	 * method update Update contents of a layer
	 */
	public void update(long timeLapse) {
		myObjectives.forEach(objective -> objective.update(timeLapse));
		mySprites.forEach(sprite -> sprite.update(timeLapse));
		myControlManager.update();
		myCollisionEngine.checkCollisions();
		myToBeRemoved.forEach(this::removeSprite);
		myToBeRemoved.clear();
	}
	
	private void removeSprite (Sprite sprite) {
	    myCollisionEngine.remove(sprite);
	    mySprites.remove(sprite);
	}
	

	public void setControlManager(ControlsManager controlManager) {
		myControlManager = controlManager;
	}

	public ControlsManager getControlManager() {
		return myControlManager;
	}

	public void setCollisionEngine(CollisionsManager collisionEngine) {
		myCollisionEngine = collisionEngine;
	}

	public void setObjectives(List<Objective> objectives) {
		myObjectives = objectives;
	}

	/**
	 * method getObjectives
	 * 
	 * @return list of objectives for the current level
	 */
	public List<Objective> getObjectives() {
		return Collections.unmodifiableList(myObjectives);
	}

	/**
	 * method addLayer adds a layer into a specific level
	 * 
	 * @param layer
	 *            the layer to be added
	 */
	public void addSprite(Sprite layer) {
		mySprites.add(layer);
	}

	/**
	 * method getLayers
	 * 
	 * @return list of layers for the current level
	 */
	public List<Sprite> getSprites() {
		return mySprites;
	}
	
	public Group getRoot () {
	    return myGroup;
	}

	@Override
	public IAction getAction (String name) {
	    return myActions.get(name);
	}
	
	public void start (double width, double height) {
	    try {
	            Group group2 = new Group(myGroup);
	            IScrollFocus focus = new BasicFocus(width, height);
	            IScroller scroller = new BasicScroller(myGroup);
	            WrapAround wrap =
	                    new WrapAround(new Image(new FileInputStream("Resources/images/samplebackground.png")),
	                                   width, height);
	            wrap.repeatHorizontal();
	            scroller.addBackground(wrap, 0.5);
	            group2.getChildren().add(wrap.getGroup());
	            wrap.getGroup().toBack();
	            SpriteTracker tracker = new SpriteTracker(focus, scroller);
	            Sprite sprite = mySprites.get(0);
	            sprite.getImageView().toFront();
	            tracker.setPlayer(sprite, true, false);
	            tracker.enable();
	            tracker.tellY(height - 200);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public void stop () {
	    
	}
 }
