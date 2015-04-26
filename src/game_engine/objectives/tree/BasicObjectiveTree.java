package game_engine.objectives.tree;

import game_engine.objectives.Objective;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BasicObjectiveTree extends ObjectiveTree{
    private Map<Objective, Node> myNodes;
    private Pane myPane;
    private Pane myFinished;
    private Pane myActive;
    
    public BasicObjectiveTree(Collection<Objective> objectives) {
        super(objectives);
        myNodes = new HashMap<>();
        objectives.forEach(goal -> myNodes.put(goal, makeNode(goal)));
        myPane = new HBox();
        myFinished = new VBox();
        myActive = new VBox();
        myPane.getChildren().addAll(myActive, myFinished);
    }
    
    private Node makeNode (Objective objective) {
        return new Text(objective.toString());
    }

    @Override
    public void onComplete (Objective objective) {
        Node node = myNodes.get(objective);
        node.setStyle("-fx-strikethrough: true");
        node.setStyle("-fx-stroke: green");
        if (!myFinished.getChildren().contains(node)) {
            myFinished.getChildren().add(node);
        }
    }

    @Override
    public void onFailed (Objective objective) {
        Node node = myNodes.get(objective);
        node.setStyle("-fx-strikethrough: true");
        node.setStyle("-fx-stroke: red");
        if (!myFinished.getChildren().contains(node)) {
            myFinished.getChildren().add(node);
        }
    }

    @Override
    public void onActive (Objective objective) {
        Node node = myNodes.get(objective);
        if (!myActive.getChildren().contains(node)) {
            myActive.getChildren().add(node);
        }
    }

    @Override
    public void onInactive (Objective objective) {
        //do nothing
    }

    @Override
    public Node getTree () {
        return myPane;
    }
}