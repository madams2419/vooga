package game_engine.objectives;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public abstract class StatusTree<T> {
    private Map<Predicate<T>, Consumer<T>> myConditions;
    private Map<T, Collection<T>> myTree;
    private Collection<T> myRoots;

    public StatusTree (Collection<T> allNodes) {
        myTree = buildTreeFromParents(allNodes);
        myRoots = findRoots(allNodes);
        myConditions = new HashMap<>();
    }
    
    public void addCondition (Predicate<T> predicate, Consumer<T> consumer) {
        myConditions.put(predicate, consumer);
    }

    // does updating order matter.
    public void update (long now) {
        for (T t : myTree.keySet()) {
            update(t, now);
        }
    }

    public abstract Collection<T> getParents (T t);

    public void update (T t, long now) {
        for (Predicate<T> condition : myConditions.keySet()) {
            if (condition.test(t)) {
                myConditions.get(condition).accept(t);
            }
        }
    }

    private Map<T, Collection<T>> buildTreeFromParents (Collection<T> allNodes) {
        Map<T, Collection<T>> tree = new HashMap<>();
        for (T t : allNodes) {
            tree.put(t, getParents(t));
        }
        return tree;
    }

    private Collection<T> findRoots (Collection<T> allNodes) {
        return allNodes.stream()
                .filter(node -> getParents(node).isEmpty())
                .collect(Collectors.toSet());
    }
}
