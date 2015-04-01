package game_engine.objective;

import game_engine.Behavior;
import java.util.List;
import java.util.function.IntSupplier;

public class CollectObjective extends DoNTimesObjective {

    public CollectObjective (int num,
                             ICollectible coin,
                             Behavior onComplete,
                             List<Objective> linked, Player...players) {
        super(num, ()-> getNumCollected(coin, players), onComplete, linked);
    }
    
    public static int getNumCollected(ICollectible coin, Player... players){
        int ans =0;
        for (Player player: players){
            ans += player.getNumCollected(coin);
        }
        return ans;
    }

}
