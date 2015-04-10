package game_engine.sprite;

import javafx.animation.KeyFrame;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class testerScreen{
    private int myWidth, myHeight;
    private StackPane myRoot;
    Player myPlayer = new Player();
    
    public testerScreen(int h, int w){
        myHeight = h;
        myWidth = w;
        myRoot = new StackPane();
    }

    public Scene initialize()
    {   
        
        myPlayer.addImage("idle", "/Resources/images/standingMario.png");
        myPlayer.addImage("walking", "/Resources/images/jumpingMario.png");
        myPlayer.setState("idle");
        
        myRoot.getChildren().add(myPlayer.getImageView());
        Scene scene = new Scene(myRoot, myWidth, myHeight);
        scene.setOnKeyPressed(e -> handleKeyInput(e));
        scene.setFill(Color.WHITE);
        return scene;
    }

    private void handleKeyInput (KeyEvent e) {
       myPlayer.setState("walking");
    }
    
    public KeyFrame start(int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate),
                        e -> update());
}
    
    private void update(){
        
    }
}