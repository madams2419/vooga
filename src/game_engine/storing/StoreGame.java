package game_engine.storing;

import game_engine.scrolling.SubClassFinder;

import java.lang.reflect.Field;

public class StoreGame {
	public void getFields(Object o) throws IllegalArgumentException, IllegalAccessException{
		System.out.println(o.getClass().getPackage().getName());
		if(o.getClass().getPackage().getName().startsWith("game")){
			for (Field field : o.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				System.out.println(o.getClass());
				Object value = field.get(o); 
				if (value != null) {
					System.out.println(field.getName() + "=" + value);
				}
				try{
					getFields(value);
				}catch (IllegalAccessException e){
					System.out.println("no fields exist");
				}
			}
		}
	}
}
