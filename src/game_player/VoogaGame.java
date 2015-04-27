package game_player;

import game_engine.Level;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.controls.ControlsManager;
import game_engine.scrolling.WrapAround;
import game_engine.scrolling.scroller.BasicScroller;
import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.BasicFocus;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.scrolling.tracker.MiniMapTracker;
import game_engine.scrolling.tracker.SpriteTracker;
import game_engine.sprite.Sprite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class VoogaGame implements IActor {

    private List<Level> levels;
    private Level activeLevel;
    private Group root;
    private Timeline timeline;
    private double width, height;
    private ControlsManager controlsManager;
    private double frameRate;

    public VoogaGame (double fr, double w, double h) {
        levels = new ArrayList<Level>();
        root = new Group();
        frameRate = fr;
        timeline = new Timeline(getFrame(frameRate));
        timeline.setCycleCount(Timeline.INDEFINITE);
        width = w;
        height = h;
    }

    private KeyFrame getFrame (double frameRate) {
        return new KeyFrame(Duration.millis(frameRate), (frame) -> update());
    }

    public void addLevel (Level l) {
        levels.add(l);
    }

    public double getHeight () {
        return height;
    }

    public IAction getAction (String name) {
        return setActiveLevel;
    }

    private IAction setActiveLevel = (params) -> {
        int index = Integer.parseInt(params[0]);
        activeLevel = levels.get(index);
    };

    public void setActiveLevel (int index) {
        root.getChildren().clear();
        activeLevel = levels.get(index);
        controlsManager = activeLevel.getControlManager();
        if (!activeLevel.getSprites().isEmpty()) {
            setUpScrolling(activeLevel.getRoot());
        }
    }

    public void setUpScrolling (Group group) {
        try {
            Group group2 = new Group(group);
            IScrollFocus focus = new BasicFocus(width, height);
            IScroller scroller = new BasicScroller(group);
            WrapAround wrap =
                    new WrapAround(new Image(new FileInputStream("Resources/images/samplebackground.png")),
                                   width, height);
            wrap.repeatHorizontal();
            scroller.addBackground(wrap, 0.5);
            group2.getChildren().add(wrap.getGroup());
            wrap.getGroup().toBack();
            SpriteTracker tracker = new SpriteTracker(focus, new BasicScroller(group));
            Sprite sprite = activeLevel.getSprites().get(0);
            sprite.getImageView().toFront();
            tracker.setPlayer(sprite, true, false);
            tracker.enable();
            tracker.tellY(height - 200);
            root.getChildren().add(group2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update () {
        activeLevel.update(frameRate);
    }

    public void start () {
        Stage stage = new Stage();
        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(e -> controlsManager.handleInput(e));
        scene.setOnKeyReleased(e -> controlsManager.handleInput(e));
        stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
        stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
        root.requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> timeline.stop());
        timeline.play();
    }

    public Group getRoot () {
        return root;
    }
}
