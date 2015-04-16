package authoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Singleton class that keeps track of the levels and maps.
 * 
 * @author Mengchao
 *
 */
public class LevelManager {
    private static LevelManager myInstance;
    private static Map<Integer, ArrayList<Integer>> myLevels;

    public static LevelManager getInstance () {
        if (myInstance == null)
            myInstance = new LevelManager();
        return myInstance;
    }

    LevelManager () {
        myLevels = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> one = new ArrayList<Integer>();
        one.add(1);
        myLevels.put(1, one);
    }

    public static Map<Integer, ArrayList<Integer>> getLevels () {
        return myLevels;
    }

    public static void makeNewLevel () {
        int nextLevel = Collections.max(myLevels.keySet());
        ArrayList<Integer> maps = new ArrayList<Integer>();
        maps.add(1);
        myLevels.put(nextLevel+1, maps);
    }
    
    public static void deleteLevel (int level) {
        myLevels.remove(level);
    }
    
    public static void deleteMap (int level, int map) {
        myLevels.get(level).remove(map);
    }
    
    public static void addMap (int level) {
        int nextMap = Collections.max(myLevels.get(level));
        myLevels.get(level).add(nextMap+1);
    }
}
