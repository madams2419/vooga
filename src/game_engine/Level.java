package game_engine;


import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.collisions.CollisionsManager;
import game_engine.control.ControlManagerFactory;
import game_engine.controls.ControlsManager;
import game_engine.objectives.Objective;
import game_engine.physics.PhysicsEngine;
import game_engine.scrolling.WrapAround;
import game_engine.scrolling.scroller.BasicScroller;
import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.BasicFocus;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.scrolling.tracker.SpriteTracker;
import game_engine.sprite.Sprite;
import game_player.HeadsUpDisplay;

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
	private ControlManagerFactory myControlFactory;
	private Collection<Sprite> myToBeRemoved;
	private Group mySpriteGroup;
	private Group myGroup;
	private Map<String, IAction> myActions;
	private PhysicsEngine myPhysics;
	private  HeadsUpDisplay hud;
	
	@IActionAnnotation(description = "Remove sprite", numParams = 1, paramDetails = "sprite's id")
	private IAction removeSprite = (params) -> {
	        String id = params[0];
	        Sprite removed = mySprites.filtered(sprite -> sprite.checkID(id)).get(0);
	        myToBeRemoved.add(removed);
	    };
	

	public Level(PhysicsEngine physics) {
		myObjectives = new ArrayList<>();
		mySprites = FXCollections.observableArrayList();
		initGroup (mySprites);
		myToBeRemoved = new ArrayList<>();
		myActions = buildActionMap();
		myPhysics = physics;
	}
	
	private void initGroup (ObservableList<Sprite> sprites) {
	    mySpriteGroup = new Group();
	    sprites.addListener((ListChangeListener<Sprite>) change -> {
	        while (change.next()) {
	            if (change.wasAdded()) {
	                change.getAddedSubList().forEach(sprite -> mySpriteGroup.getChildren().add(sprite.getImageView()));
	            }
	            if (change.wasRemoved()) {
	                change.getRemoved().forEach(sprite -> mySpriteGroup.getChildren().remove(sprite.getImageView()));
	            }
	        }
	    });
	}

	/**
	 * method update Update contents of a layer
	 */
	public void update(double framePeriod) {
		long framePeriodMillis = (long) (framePeriod * 1000);
		myObjectives.forEach(objective -> objective.update(framePeriodMillis));
		myPhysics.update(framePeriod); // update PhysicsObjects and handle physical collisions
		mySprites.forEach(sprite -> sprite.update(framePeriodMillis)); // update animations
		//myControlManager.update();
		myCollisionEngine.checkCollisions(); // handle behavioral collisions
		myToBeRemoved.forEach(this::removeSprite);
		myToBeRemoved.clear();
		hud.update(framePeriod);
	}
	
	private void removeSprite (Sprite sprite) {
	    myCollisionEngine.remove(sprite);
	    mySprites.remove(sprite);
	    myPhysics.removePhysicsObject(sprite.getPhysicsObject());
	}

	public void setControlManager(ControlsManager controlManager) {
		myControlManager = controlManager;
	}
	
	public void setControlFactory(ControlManagerFactory controlFactory){
		myControlFactory = controlFactory;
	}

	public ControlsManager getControlManager() {
		return myControlManager;
	}
	
	public ControlManagerFactory getControlFactory(){
		return myControlFactory;
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
	            myGroup = new Group(mySpriteGroup);
	            IScrollFocus focus = new BasicFocus(width, height);
	            IScroller scroller = new BasicScroller(mySpriteGroup);
	            SpriteTracker tracker = new SpriteTracker(focus, scroller);
	            Sprite sprite = mySprites.get(0);
	            sprite.getImageView().toFront();
	            tracker.enable();
	            tracker.setPlayer(sprite, true, true);
	            tracker.tellY(height - 200);
	            hud = new HeadsUpDisplay(sprite);
	            myGroup.getChildren().add(hud.getHUD());
	            WrapAround wrap =
                            new WrapAround(new Image(new FileInputStream("Resources/images/samplebackground.png")),
                                           width, height);
                    wrap.repeatHorizontal();
                    wrap.repeatVertical();
                    scroller.addBackground(wrap, 0.5);
                    myGroup.getChildren().add(wrap.getGroup());
                    wrap.getGroup().toBack();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public void stop () {
	    
	}
 }
