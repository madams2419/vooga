package game_engine.game_player;

import java.util.ArrayList;
import game_engine.HitBox;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import game_engine.sprite.Player;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameScreen{
    private int myWidth, myHeight;
    private StackPane myRoot;
    private Player myPlayer;
    private Vector myImpulse;
    
    public GameScreen(int h, int w){
        myHeight = h;
        myWidth = w;
        myRoot = new StackPane();
        myPlayer = new Player();
    }

    public Scene initialize()
    {
        myPlayer.addImage("walk", "/Resources/walkingMario.png");
        myPlayer.addImage("walk","/Resources/standingMario.png");
        myPlayer.addImage("stand", "/Resources/standingMario.png");
        myPlayer.addImage("jump", "/Resources/jumpingMario.png");
        
       
        myRoot.getChildren().add(myPlayer.getImageView());
        Material material = new Material(1, 1);
//        PhysicsEngine pEngine = new PhysicsEngine(1,-9.8);
//        Vector vector = new Vector(myPlayer.getImageView().getTranslateX(),myPlayer.getImageView().getTranslateY());
//        HitBox hitBox = new HitBox(new ArrayList<Rectangle>());
//        System.out.println(hitBox.getVolume());
//        PhysicsObject physics = new PhysicsObject(pEngine, material, hitBox, myPlayer.getState(), vector, new Vector(0,0));
//        myImpulse = new Vector(0,5);
//        myPlayer.getPhysicsObject().applyImpulse(myImpulse);
         myPlayer.setState("stand");
         
        Scene scene = new Scene(myRoot, myWidth, myHeight);
        
        scene.setOnKeyPressed(e -> handleKeyInput(e));
        scene.setFill(Color.WHITE);
        return scene;
    }

    private void handleKeyInput (KeyEvent e) {
       KeyCode keyCode = e.getCode();
       if(keyCode == KeyCode.UP){
           myPlayer.setState("jump");
           myPlayer.getPhysicsObject().applyImpulse(myImpulse);
       }
       
       else if(keyCode == KeyCode.DOWN)
           myPlayer.setState("stand");
       
       else if(keyCode == KeyCode.RIGHT)
           myPlayer.setState("walk");
       
    }
}
