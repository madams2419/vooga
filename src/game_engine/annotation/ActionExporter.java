package game_engine.annotation;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * pulls annotations from specified root class and writes them to properties folder
 * @author Emre Sonmez
 */
public class ActionExporter {
	private Map<String, List<String>> myMap;
	private Map<String, Function<AnnotatedElement, String>> myFunctions;
	private Class<? extends Annotation> myRootClass;
	private String myName;
	private int mySize;


	public ActionExporter(Class<? extends Annotation> rootClass, String name) {
		myRootClass = rootClass;
		myName = name;
		myMap = new LinkedHashMap<>(); // preserve order of insertion
		myFunctions = new HashMap<>();
		mySize = 0;
	}

	public ActionExporter (Class<? extends Annotation> rootClass) {
		this(rootClass, rootClass.getSimpleName());
	}

	/**
	 * runs ActionExporter
	 * @param fullClassName
	 * @param fileName
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public void runExporter(String fullClassName, String fileName) 
			throws SecurityException, ClassNotFoundException, IOException, 
			NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		processAnnotations(fullClassName);
		writeToFile(fileName);
	}

	/**
	 * Adds an attribute that will be printed to the properties file.
	 * @param attribute Attribute
	 * @param function Given an annotated element, defines how to get the value of this attribute.
	 */
	public void addAttribute (String attribute, Function<AnnotatedElement, String> function) {
		myFunctions.put(attribute, function);
		myMap.put(attribute, new ArrayList<>());
	}

	/**
	 * See addAttribute(String, Function). 
	 * This method uses a default function of calling the annotations attribute method.
	 * @param attribute
	 */
	public void addAttribute (String attribute) {
		addAttribute(attribute, annotated -> getAnnotationAttribute(annotated, attribute));
	}

	/**
	 * 
	 * @param annotated Annotated element so can be a field or a method.
	 * @param attribute Name of the attribute. The annotation for this exporter 
	 * must have a method attribute for this already.
	 * @return the value of this attribute
	 */
	private String getAnnotationAttribute (AnnotatedElement annotated, String attribute) {
		Annotation annotation = annotated.getAnnotation(myRootClass);
		Method method;
		Object object;
		try {
			method = myRootClass.getMethod(attribute);
			object = method.invoke(annotation);
			return object.toString();
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the name of the annotated element which is assumed to be the string after the last period.
	 * @param annotated
	 * @return The name of the annotated element.
	 */
	public String getDefaultName (AnnotatedElement annotated) {
		String[] annotatedArray = annotated.toString().split("\\.");
		return annotatedArray[annotatedArray.length - 1];
	}

	/**
	 * Processed the annotations of a class by finding the annotated elements (fields or methods) 
	 * and processing those elements.
	 * @param rootClass
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void processAnnotations (Class<?> rootClass) throws NoSuchMethodException,
	SecurityException, IllegalAccessException, IllegalArgumentException,
	InvocationTargetException {
		processAnnotations(rootClass.getDeclaredFields());
		processAnnotations(rootClass.getDeclaredMethods());
		System.out.println(myMap);
	}

	/**
	 * Process the annotations for an array of annotated elements.
	 * @param elements
	 */
	private void processAnnotations (AnnotatedElement[] elements) {
		for (AnnotatedElement element : elements) {
			if (element.isAnnotationPresent(myRootClass)) {
				for (String attribute : myMap.keySet()) {
					String value = myFunctions.get(attribute).apply(element);
					myMap.get(attribute).add(value);
				}
				mySize ++ ;
			}
		}
	}

	/**
	 * Convenience method. Calls processAnnotations (Class.forName(fullClassName)). 
	 * @param fullClassName
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void processAnnotations (String fullClassName)
			throws SecurityException, ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		processAnnotations(Class.forName(fullClassName));
	}

	/**
	 * uses WriteProperties to write annotations to file
	 * @param fileName
	 * @throws IOException
	 */
	private void writeToFile(String fileName) throws IOException{
		WriteProperties propertiesWriter = new WriteProperties();
		for(int i = 0; i < mySize; i++){
			String propertyRoot = i+"_"+ myName;

			for (String attribute: myMap.keySet()) {
				String propertyName = propertyRoot + "_" + attribute;
				propertiesWriter.addProperty(propertyName, myMap.get(attribute).get(i));
			}
		}
		propertiesWriter.writeToFile(fileName);
	}

//	public static void main (String[] args) {
//		ActionExporter e = new ActionExporter(IActionAnnotation.class);
//		e.addAttribute("name", e::getDefaultName);
//		e.addAttribute("description");
//		e.addAttribute("numParams");
//		System.out.println(e.myMap);
//		
//		try {
//			e.runExporter("game_engine.sprite.Sprite", "Actions.properties");
//		}catch (NoSuchMethodException | SecurityException | IllegalAccessException
//				| IllegalArgumentException | InvocationTargetException | IOException 
//				| ClassNotFoundException e1) {
//			e1.printStackTrace();
//			
//		}
//		
//	}
}
