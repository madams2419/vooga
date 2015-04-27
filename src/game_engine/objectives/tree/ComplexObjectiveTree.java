package game_engine.objectives.tree;

import game_engine.objectives.Objective;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;


/**
 * Builds an objective tree with nodes that have colors representing their state and a tooltip
 * describing the objective.
 * 
 * @author Tony
 *
 */
public class ComplexObjectiveTree extends ObjectiveTree {
    private static final double RADIUS = 20;
    private Map<Objective, Integer> myLevels;
    private Map<Objective, Shape> myShapes;
    private Group myGroup;
    private double myWidth;
    private double myHeight;

    /**
     * 
     * @param allNodes
     * @param width Desired width of the tree.
     * @param height Desired height of the tree.
     */
    public ComplexObjectiveTree (Collection<Objective> allNodes, double width, double height) {
        super(allNodes);
        myWidth = width;
        myHeight = height;
        buildLevels(allNodes);
        makeShapeMap(allNodes);
        populateGroup();
        System.out.println(myLevels);
    }

    private Shape makeShape (Objective objective) {
        Shape shape = new Circle(RADIUS);
        Tooltip tip = new Tooltip(objective.toString());
        Tooltip.install(shape, tip);
        return shape;
    }

    private void makeShapeMap (Collection<Objective> allNodes) {
        myShapes = new HashMap<>();
        allNodes.forEach(obj -> myShapes.put(obj, makeShape(obj)));
    }

    @Override
    public void onComplete (Objective objective) {
        myShapes.get(objective).setFill(Color.GREEN);
    }

    @Override
    public void onFailed (Objective objective) {
        myShapes.get(objective).setFill(Color.RED);
    }

    @Override
    public void onActive (Objective objective) {
        myShapes.get(objective).setFill(Color.YELLOW);
    }

    @Override
    public void onInactive (Objective objective) {
        myShapes.get(objective).setFill(Color.GRAY);
    }

    @Override
    public Node getTree () {
        return myGroup;
    }

    private void buildLevels (Collection<Objective> allNodes) {
        myLevels = new HashMap<>();
        int i = 1;
        Collection<Objective> current = iterate(allNodes);
        while (!current.isEmpty()) {
            for (Objective objective : current) {
                myLevels.put(objective, i);
            }
            i++;
            current = iterate(allNodes);
        }
    }

    private Collection<Objective> iterate (Collection<Objective> allNodes) {
        Collection<Objective> iteration = new ArrayList<>();
        for (Objective objective : allNodes) {
            if (!myLevels.keySet().contains(objective) &&
                myLevels.keySet().containsAll(getChildren(objective))) {
                iteration.add(objective);
            }
        }
        return iteration;
    }

    private void connect (Shape one, Shape two) {
        Point2D onePos = getCenter(one);
        Point2D twoPos = getCenter(two);
        Line line = new Line(onePos.getX(), onePos.getY(), twoPos.getX(), twoPos.getY());
        myGroup.getChildren().add(line);
        line.toBack();
        line.setFill(Color.BLACK);
    }

    private Point2D getCenter (Shape one) {
        Bounds bounds = one.getBoundsInParent();
        double posX = 0.5 * (bounds.getMaxX() + bounds.getMinX());
        double posY = 0.5 * (bounds.getMinY() + bounds.getMaxY());

        return new Point2D(posX, posY);
    }

    private void populateGroup () {
        myGroup = new Group();
        drawNodes();
        drawLines();

    }

    private <T, U> Map<U, Collection<T>> reverseOrderedMap (Map<T, U> map) {
        Map<U, Collection<T>> reverse = new TreeMap<>();
        for (Map.Entry<T, U> entry : map.entrySet()) {
            Collection<T> value = reverse.getOrDefault(entry.getValue(), new ArrayList<T>());
            value.add(entry.getKey());
            reverse.put(entry.getValue(), value);
        }
        return reverse;
    }

    private void drawNodes () {
        VBox vbox = new VBox();
        vbox.setSpacing(50);
        Map<Integer, Collection<Objective>> layers = reverseOrderedMap(myLevels);
        for (int i : layers.keySet()) {
            drawLayer(layers.get(i), 1 - ((double) i) / (layers.keySet().size() + 1));
        }
    }

    private void drawLayer (Collection<Objective> layer, double ratio) {
        double size = (int) layer.size();
        int i = 1;
        for (Objective objective : layer) {
            Shape shape = myShapes.get(objective);
            shape.setTranslateY(myHeight * ratio);
            shape.setTranslateX(myWidth * i / (size + 1));
            myGroup.getChildren().add(shape);
            i++;
        }
    }

    private void drawLines () {
        for (Objective objective : myShapes.keySet()) {
            for (Objective child : getChildren(objective)) {
                connect(myShapes.get(objective), myShapes.get(child));
            }
        }
    }
}
