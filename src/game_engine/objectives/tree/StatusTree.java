package game_engine.objectives.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Abstract class for building a status tree, an object that provides some representation of the
 * status of nodes that form a tree structure.
 * 
 * @author Tony
 *
 * @param <T> Tree node. Does not have to be forced into a certain subclass or implementation of
 *        some interface. When creating a status tree, there is a getParents method that is needed to be filled out.
 */
public abstract class StatusTree<T> {
    private Map<Predicate<T>, Consumer<T>> myConditions;
    private Map<T, Collection<T>> myTree;

    /**
     * Constructor.
     * @param allNodes Collection of all the nodes that form the tree structure.
     */
    public StatusTree (Collection<T> allNodes) {
        myTree = buildTreeFromParents(allNodes);
        myConditions = new HashMap<>();
    }

    /**
     * Adds a status condition and what happens.
     * @param predicate Predicate representing if the object tested is of that status.
     * @param consumer What happens if the object is of that status. This may be some visual representation of the node.
     */
    public void addCondition (Predicate<T> predicate, Consumer<T> consumer) {
        myConditions.put(predicate, consumer);
    }

    // does updating order matter.
    /**
     * Updates the tree structure with the current time.
     * @param now current time.
     */
    public void update (long now) {
        for (T t : myTree.keySet()) {
            update(t, now);
        }
    }

    /**
     * Returns a collection of objects that are the parents of the input.
     * @param t
     * @return
     */
    public abstract Collection<T> getParents (T t);
    
    protected void update (T t, long now) {
        for (Predicate<T> condition : myConditions.keySet()) {
            if (condition.test(t)) {
                myConditions.get(condition).accept(t);
            }
        }
    }

    private Map<T, Collection<T>> buildTreeFromParents (Collection<T> allNodes) {
        Map<T, Collection<T>> tree = new HashMap<>();
        allNodes.forEach(t -> tree.put(t, new ArrayList<>()));
        for (T t : allNodes) {
            for (T parent: getParents(t)) {
                tree.get(parent).add(t);
            }
        }
        System.out.println(tree);
        return tree;
    }
    
    protected Collection<T> getChildren (T t) {
        return myTree.get(t);
    }
    

}
