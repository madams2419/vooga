package game_engine.scrolling;

import game_engine.collision.PixelPerfectDetector;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;



public class ScrollTester extends Application{

    @Override
    public void start (Stage stage) throws Exception {
        Group group = new Group();
        ImageView image = new ImageView("Resources/images/block.png");
        WrapAround wrap = new WrapAround("Resources/images/block.png", 400, 400);
        wrap.repeatHorizontal();
        wrap.repeatVertical();
        Node node = wrap.getGroup();
        group.getChildren().addAll(image, wrap.getGroup());
        
        
        
        Scene scene = new Scene(group, 400, 400);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                node.setTranslateY(node.getTranslateY() - 10);
            }
            if (e.getCode() == KeyCode.DOWN) {
                node.setTranslateY(node.getTranslateY() + 10);
            }
            if (e.getCode() == KeyCode.RIGHT){
                node.setTranslateX(node.getTranslateX()  + 10);
            }
            if (e.getCode() == KeyCode.LEFT) {
                node.setTranslateX(node.getTranslateX() - 10);
            }
            wrap.getNodesOnScreen().forEach(view -> {
                PixelPerfectDetector p = new PixelPerfectDetector(view, image);
                System.out.println(p.isColliding());
            });
        });
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main (String[] args) {
        launch(args);
    }


}
