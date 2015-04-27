package game_engine;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.collisions.CollisionsManager;
import game_engine.controls.ControlsManager;
import game_engine.objectives.Objective;
import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;


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

    public Level () {
        myObjectives = new ArrayList<>();
        mySprites = FXCollections.observableArrayList();
        initGroup(mySprites);
        myToBeRemoved = new ArrayList<>();
        myActions = buildActionMap();
    }

    private void initGroup (ObservableList<Sprite> sprites) {
        myGroup = new Group();
        sprites.addListener((ListChangeListener<Sprite>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(sprite -> myGroup.getChildren()
                            .add(sprite.getImageView()));
                }
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(sprite -> myGroup.getChildren()
                            .remove(sprite.getImageView()));
                }
            }
        });
    }

    /**
     * method update Update contents of a layer
     */
    public void update (double now) {
        myObjectives.forEach(objective -> objective.update(now));
        mySprites.forEach(sprite -> sprite.update(now));
        myControlManager.update();
        myCollisionEngine.checkCollisions();
        myToBeRemoved.forEach(this::removeSprite);
        myToBeRemoved.clear();
    }

    private void removeSprite (Sprite sprite) {
        myCollisionEngine.remove(sprite);
        mySprites.remove(sprite);
    }

    @IActionAnnotation(description = "Remove sprite", numParams = 1)
    private IAction removeSprite = (params) -> {
        System.out.println(Arrays.asList(params));
        String id = params[0];
        Sprite removed = mySprites.filtered(sprite -> sprite.checkID(id)).get(0);
        myToBeRemoved.add(removed);
    };

    public void setControlManager (ControlsManager controlManager) {
        myControlManager = controlManager;
    }

    public ControlsManager getControlManager () {
        return myControlManager;
    }

    public void setCollisionEngine (CollisionsManager collisionEngine) {
        myCollisionEngine = collisionEngine;
    }

    /**
     * method addObjective adds a Game objective to the current level
     * 
     * @param objective
     *        the GameObjective to be added
     */
    public void addObjective (Objective objective) {
        myObjectives.add(objective);
    }

    /**
     * method removeObjective removes a Game objective from the current level
     * 
     * @param objective
     *        the objective to be removed
     */
    public void removeObjective (Objective objective) {
        myObjectives.remove(objective);
    }

    public void setObjectives (List<Objective> objectives) {
        myObjectives = objectives;
    }

    /**
     * method getObjectives
     * 
     * @return list of objectives for the current level
     */
    public List<Objective> getObjectives () {
        return myObjectives;
    }

    /**
     * method addLayer adds a layer into a specific level
     * 
     * @param layer
     *        the layer to be added
     */
    public void addSprite (Sprite layer) {
        mySprites.add(layer);
    }

    /**
     * method getLayers
     * 
     * @return list of layers for the current level
     */
    public List<Sprite> getSprites () {
        return mySprites;
    }

    public Group getRoot () {
        return myGroup;
    }

    @Override
    public IAction getAction (String name) {
        return myActions.get(name);
    }
}
