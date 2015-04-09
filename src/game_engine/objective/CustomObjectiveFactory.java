package game_engine.objective;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


public class CustomObjectiveFactory extends ObjectiveFactory {

    
/*    public Objective makeCollectObjective (int num,
                                           Collectible coin,
                                           Player player,
                                           IBehavior behavior) {
        return makeDoNumObjective(num, () -> player.countNumOf(coin), behavior);
    }

    public Objective makeCollectAllObjective (Collectible coin, Level level, IBehavior behavior) {        
        return makeDoAllObjective(() -> countNumOf(coin, level), behavior);
    }

    private int countNumOf(Sprite sprite, Level level) {
        int count = 0;
        level.getLayers().forEach(layer -> count += countNumOf(sprite, layer));
        return count;                          
    }
    
    private int countNumOf(Sprite sprite, Layer layer){
        int count = 0;
        count += layer.getSprites().stream().filter(spriteA -> spriteA.getName().equals(sprite.getName())).count();
        return count;
    }

    public Objective makeBeatObjective (int num, IEnemy enemy, Player player, IBehavior behavior) {
        return makeDoNumObjective(num, () -> player.countNumOf(enemy), behavior);
    }
    
    public Objective makeBeatAllObjective (Enemy enemy, Level level, IBehavior behavior) {
        return makeDoAllObjective(() -> countNumOf(enemy, level), behavior);
    }

    public Objective makeReachObjective (Sprite sprite,
                                         Player player,
                                         CollisionEngine collisionEngine,
                                         IBehavior behavior) {
        return makeObjective( (now) -> collisionEngine.checkCollision(player, sprite), behavior);
    }

    public Objective makeDontObjective (Predicate<Long> condition, IBehavior behavior) {
        return makeObjective(condition.negate(), behavior);
    }

    public Objective makePlayerDeathObjective (Player player, IBehavior behavior) {
        return makeDontObjective( () -> player.isAlive(), behavior);
    }*/
    


}
