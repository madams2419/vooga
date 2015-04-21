package game_engine.sprite.utilitybuilder;

import game_engine.sprite.Sprite;
import groovy.ui.SystemOutputInterceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ActionExporter {
	public String[] writeUtilitiesFile(String fullClassName) throws SecurityException, ClassNotFoundException{
		Field[] fields = Class.forName(fullClassName).getDeclaredFields();
		String[] methodNames = new String[fields.length];
		int index = 0;
		for (Field field: fields) {
			if (field.isAnnotationPresent(IActionAnnotation.class)){
				IActionAnnotation ia = (IActionAnnotation) field.getAnnotation(IActionAnnotation.class);
				int paramCount = ia.numParams();
				String[] fieldString = field.toString().split("\\.");
				System.out.println(fieldString[fieldString.length-1]);
				methodNames[index] = fieldString[fieldString.length-1];
				index++;
			}
		}
		return methodNames;
		
	}
	
	public static void main(String[] args) {
		ActionExporter ae = new ActionExporter();
		try {
			String[] actionNames = ae.writeUtilitiesFile("game_engine.sprite.Sprite");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
