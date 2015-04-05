package game_engine.game_player;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameScreen{
    private int myWidth, myHeight;
    private StackPane myRoot;
    
    public GameScreen(int h, int w){
        myHeight = h;
        myWidth = w;
        myRoot = new StackPane();
    }

    public Scene initialize()
    {
        Scene scene = new Scene(myRoot, myWidth, myHeight);
        scene.setFill(Color.WHITE);
        return scene;
    }
}
