package game_engine.objective;

import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class ObjectiveTester extends Application{
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {  
        Group group = new Group();
        Objective o1 = new Objective();
        o1.setName("Get 4 coins");
        Objective o2 = new Objective();
        o2.setName("Kill Bowser");
        Objective o3 = new Objective();
        o3.setName("Run into a wall");
        Objective o4 = new Objective();
        o4.setName("Jump off a cliff");
        Objective o5 = new Objective();
        o5.setName("Stand still");
        Objective o6 = new Objective();
        o6.setName("Finish this");
        o5.setPreReqs(Arrays.asList(o3, o4));
        o3.setPreReqs(Arrays.asList(o2, o6));
        o2.setPreReqs(Arrays.asList(o1));
        
        
        ComplexObjectiveTree c = new ComplexObjectiveTree(Arrays.asList(o1, o2, o3, o4, o5, o6), 300, 300);
        c.update(0);
        group.getChildren().add(c.getTree());
        System.out.println(c.getTree());
        Scene scene = new Scene(group, 300, 300);
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT1) {
                o1.getAction("setStatus").execute("complete");
            }
            if (e.getCode() == KeyCode.DIGIT2) {
                o2.getAction("setStatus").execute("complete");
            }
            if (e.getCode() == KeyCode.DIGIT3) {
                o3.getAction("setStatus").execute("complete");
            }
            if (e.getCode() == KeyCode.DIGIT4) {
                o4.getAction("setStatus").execute("complete");
            }
            if (e.getCode() == KeyCode.DIGIT5) {
                o5.getAction("setStatus").execute("complete");
            }
            if (e.getCode() == KeyCode.DIGIT6) {
                o6.getAction("setStatus").execute("complete");
            }
            c.update(0);
            c.update(0);
        });
        stage.setScene(scene);
        stage.show();
    }
}
