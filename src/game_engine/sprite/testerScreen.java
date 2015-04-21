package game_engine.sprite;

import game_engine.sprite.Sprite;
import javafx.animation.KeyFrame;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class testerScreen{
    private int myWidth, myHeight;
    private StackPane myRoot;
    Sprite myPlayer = new Sprite(null);
    
    public testerScreen(int h, int w){
        myHeight = h;
        myWidth = w;
        myRoot = new StackPane();
    }

    public Scene initialize()
    {   
        
        myPlayer.addImage("idle", "/Resources/images/standingMario.png");
        myPlayer.addImage("walking", "/Resources/images/walkingMario.png");
        myPlayer.addImage("walking","/Resources/images/standingMario.png");
        myPlayer.addImage("jumping", "/Resources/images/jumpingMario.png");
        myPlayer.addImage("hammer", "/Resources/images/hammerMario.png");
        myPlayer.addImage("hammer", "/Resources/images/hammerMario2.png");
        myPlayer.setState("idle");
        myPlayer.getImageView().setFitHeight(200);
        myPlayer.getImageView().setFitWidth(100);
        
        myRoot.getChildren().add(myPlayer.getImageView());
        Scene scene = new Scene(myRoot, myWidth, myHeight);
        scene.setOnKeyPressed(e -> handleKeyInput(e));
        scene.setFill(Color.WHITE);
        return scene;
    }

    private void handleKeyInput (KeyEvent e) {
        System.out.println(myPlayer.getState());
        KeyCode keyCode = e.getCode();
        if(keyCode == KeyCode.LEFT){
        myPlayer.setState("walking");
        }
        else if(keyCode ==KeyCode.UP)
            myPlayer.setState("jumping");
        else if( keyCode == KeyCode.DOWN)
            myPlayer.setState("hammer");
        
    }
    
    public KeyFrame start(int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate),
                        e -> update());
}
    
    private void update(){
        
    }
}