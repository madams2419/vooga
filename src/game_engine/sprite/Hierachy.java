package game_engine.sprite;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Hierachy<T> {
    private Map<T, T> myParents;

    public Hierachy () {
        myParents = new HashMap<>();
    }

    public void addParent (T child, T parent) {
        myParents.put(child, parent);
    }

    public boolean isAncestor (T child, T ancestor) {
        if (child == ancestor) {
            return true;
        }
        return isAncestor (myParents.get(child), ancestor);
    }
}
