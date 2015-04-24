package game_engine.score;

import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class ScoreTracker {
    private Collection<Sprite> myCollected;
    private Map<String, Integer> myScoreMap;
    
    public ScoreTracker (Map<String, Integer> scoreMap) {
        myCollected = new ArrayList<>();
        myScoreMap = scoreMap;
    }
    
    public void collect (Sprite sprite) {
        myCollected.add(sprite);
    }
    
    public void drop (Predicate<Sprite> condition) {
        myCollected.removeIf(condition);
    }
    
    public int calculate () {
        int score = 0;
        for (Sprite collected: myCollected) {
            //score += myScoreMap.get(collected.getType());
        }
        return score;
    }
}
