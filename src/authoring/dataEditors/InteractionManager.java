package authoring.dataEditors;

import java.util.HashMap;
import java.util.Map;


/**
 * Singleton class that keeps track of the interactions between sprites.
 * 
 * @author Natalie
 *
 */
public class InteractionManager {
	
	
	public InteractionManager(){
		
	}
	
	public void addInteraction(Sprite a, Sprite b){
		
	}
	
//    private static InteractionManager myInstance;
//    private Map<Sprite, Map<Sprite, Interaction>> myInteractions;
//
//    public static InteractionManager getInstance () {
//        if (myInstance == null)
//            myInstance = new InteractionManager();
//        return myInstance;
//    }
//
//    InteractionManager () {
//        myInteractions = new HashMap<>();
//    }
//
//    public Interaction getOrCreateInteraction (Sprite sprite1, Sprite sprite2) {
//        Map<Sprite, Interaction> intermediateMap = myInteractions.get(sprite1);
//        if (intermediateMap == null) {
//            return makeNewInteraction(sprite1, sprite2);
//        }
//
//        Interaction interaction = myInteractions.get(sprite1).get(sprite2);
//        return interaction == null ? makeNewInteraction(sprite1, sprite2) : interaction;
//    }
//
//    private Interaction makeNewInteraction (Sprite sprite1, Sprite sprite2) {
//        Interaction interaction = new Interaction(sprite1, sprite2);
//        setInteractionMaps(sprite1, sprite2, interaction);
//        return interaction;
//    }
//    
//    public void setInteraction (Sprite sprite1, Sprite sprite2, Interaction interaction) {
//        getOrCreateInteraction(sprite1, sprite2);
//        setInteractionMaps(sprite1, sprite2, interaction);
//    }
//
//    private void setInteractionMaps (Sprite sprite1, Sprite sprite2, Interaction interaction) {
//        addMiniSpriteMap(sprite1, sprite2, interaction);
//        addMiniSpriteMap(sprite2, sprite1, interaction.getOpposite());
//    }
//
//    private void addMiniSpriteMap (Sprite sprite1, Sprite sprite2, Interaction interaction) {
//        Map<Sprite, Interaction> sprite1map = myInteractions.get(sprite1);
//        if (sprite1map == null) {
//            sprite1map = new HashMap<>();
//        }
//        myInteractions.put(sprite1, sprite1map);
//        sprite1map.put(sprite2, interaction);
//        
//        // TODO
//        // do we want sprite to hold the interaction or have them all in a seperate section?
//        // the following line is for having the sprites hold their interactions
//        sprite1.setInteraction(sprite2, interaction);
//    }
//
//    public void printOut () {
////        System.out.println("\n\n\n");
////        for (Sprite sprite1 : myInteractions.keySet()) {
////            System.out.print(sprite1.getName() + " ");
////            for (Sprite sprite2 : myInteractions.get(sprite1).keySet()) {
////                System.out.print(myInteractions.get(sprite1).get(sprite2).getAction1() + " ");
////                System.out.print(sprite2.getName() + " ");
////                System.out.println(myInteractions.get(sprite1).get(sprite2).getAction2());
////            }
////        }
//    }
//
//    public void removeSpriteInteractions (Sprite sprite) {
//        myInteractions.remove(sprite);
//        for (Sprite s : myInteractions.keySet()) {
//            myInteractions.get(s).remove(sprite);
//        }
//    }
}
