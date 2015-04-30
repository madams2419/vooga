package game_player;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HeadsUpDisplay extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start (Stage stage) throws Exception {
        // TODO Auto-generated method stub
        stage.setTitle("Heads Up Display");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(primaryScreenBounds.getMaxX());
        stage.setY(primaryScreenBounds.getMinY());
        Group group = new Group();
        Scene scene = new Scene(group);
     
        
       
        FileInputStream fis = new FileInputStream("resources/images/brick.png");
        ImageView player = new ImageView(new Image(fis));
        player.setFitHeight(50);
        player.setFitWidth(50);
        player.setTranslateX(100);
        player.setTranslateY(400);
        
        //SubScene elements
        Group hudGroup = new Group();
        SubScene hud = new SubScene(hudGroup,primaryScreenBounds.getWidth(),200);
        hud.setOpacity(0.7);
        hud.setFill(Color.AZURE);
        Label xPos = new Label("xPos");
        hudGroup.getChildren().add(xPos);
        
        Label xCoor = new Label();
        SimpleStringProperty x = new SimpleStringProperty();
        x.setValue(Double.toString(player.getTranslateX()));
        xCoor.textProperty().bind(x);
        hudGroup.getChildren().add(xCoor);
        xCoor.setTranslateX(50);
       
        scene.setOnKeyPressed(e -> handleKeyInput(e,player));
        group.getChildren().add(hud);
        group.getChildren().add(player);
        stage.setScene(scene);
        stage.show();
        
    }

    private void handleKeyInput (KeyEvent e,ImageView player) {
        KeyCode key = e.getCode();
        
        if(key == KeyCode.UP){
            player.setTranslateY(player.getTranslateY()-10);
        }
        if(key == KeyCode.DOWN){
            player.setTranslateY(player.getTranslateY()+10);
        }
        if(key == KeyCode.LEFT){
            player.setTranslateX(player.getTranslateX()-10);
        }
        if(key == KeyCode.RIGHT){
            player.setTranslateX(player.getTranslateX()+10);
        }
    }

}
