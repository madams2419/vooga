package game_engine.storing;

import java.lang.reflect.Field;

import game_player.VoogaGame;

public class StoreGame {
	public void getFields(Object o){
		for (Field field : o.getClass().getDeclaredFields()) {
		    field.setAccessible(true);
		    
		    Object value = field.get(someObject); 
		    if (value != null) {
		        System.out.println(field.getName() + "=" + value);
		    }
		}
	}
}
