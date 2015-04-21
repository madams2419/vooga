package game_engine.sprite.utilitybuilder;

import game_engine.sprite.Sprite;
import groovy.ui.SystemOutputInterceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ActionExporter {
	private ArrayList<String> methodNames = new ArrayList<>();
	private ArrayList<String> descriptions = new ArrayList<>();
	private ArrayList<Integer> paramCounts = new ArrayList<>();
	private String propertyRoot;
	
	public ActionExporter(String propertyRootName){
		this.propertyRoot = propertyRootName;
	}
	
	/**
	 * runs ActionExporter
	 * @param fullClassName
	 * @param fileName
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void runExporter(String fullClassName, String fileName) 
			throws SecurityException, ClassNotFoundException, IOException{
		processAnnotations(fullClassName);
		writeToFile(fileName);
	}
	
	/**
	 * Retrieves IActionAnnotations from specified class and populates ArrayLists
	 * @param fullClassName
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	private void processAnnotations(String fullClassName) 
			throws SecurityException, ClassNotFoundException{
		Field[] fields = Class.forName(fullClassName).getDeclaredFields();
		for (Field field: fields) {
			if (field.isAnnotationPresent(IActionAnnotation.class)){
				IActionAnnotation ia = (IActionAnnotation) field.getAnnotation(IActionAnnotation.class);
				int paramCount = ia.numParams();
				String description = ia.description();
				String[] fieldString = field.toString().split("\\.");
				methodNames.add(fieldString[fieldString.length-1]);
				descriptions.add(description);
				paramCounts.add(paramCount);
			}
		}
	}
	
	/**
	 * accesses ArrayLists and writes properties to file
	 * propertyRootName + number
	 * propertyRootName + number + Description
	 * propertyRootName + number + ParamCount
	 * @param fileName
	 * @throws IOException
	 */
	private void writeToFile(String fileName) throws IOException{
		WriteProperties propertiesWriter = new WriteProperties(fileName);
		for(int i = 0; i < methodNames.size(); i++){
			String propertyName = propertyRoot + i;
			String descriptionName = propertyName+ "Description";
			String paramCountName = propertyName+ "ParamCount";
			propertiesWriter.addProperty(descriptionName,
					methodNames.get(i));
			propertiesWriter.addProperty(descriptionName,
					descriptions.get(i));
			propertiesWriter.addProperty(paramCountName,
					paramCounts.get(i).toString());
			
		}
		propertiesWriter.writeToFile();
	}
	
	public static void main(String[] args) {
		ActionExporter ae = new ActionExporter("Action");
		try {
			ae.runExporter("game_engine.sprite.Sprite", "Actions.properties");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
