package game_engine.sprite;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class testerScreen{
    private int myWidth, myHeight;
    private StackPane myRoot;
    
    public testerScreen(int h, int w){
        myHeight = h;
        myWidth = w;
        myRoot = new StackPane();
    }

    public Scene initialize()
    {   
        Player myPlayer = new Player();
        myPlayer.addImage("idle", "Resources/images/standingMario.png");
//        myPlayer.addImage("walking", "/Resources/images/walkingMario.png");
        
        Scene scene = new Scene(myRoot, myWidth, myHeight);
        scene.setFill(Color.WHITE);
        return scene;
    }
}