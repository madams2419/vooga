package game_engine.scrolling;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ActionExporter {
    private Map<String, List<String>> myMap;
    private Map<String, Function<AnnotatedElement, String>> myFunctions;
    private Class<? extends Annotation> myRootClass;
    private String myName;
    private int mySize;

    public ActionExporter (Class<? extends Annotation> clazz, String name) {
        myRootClass = clazz;
        myName = name;
        myMap = new LinkedHashMap<>();
        myFunctions = new HashMap<>();
        mySize = 0;
    }

    public ActionExporter (Class<? extends Annotation> clazz) {
        this(clazz, clazz.getSimpleName());
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
     * See addAttribute(String, Function). This method uses a default function of calling the annotations attribute method.
     * @param attribute
     */
    public void addAttribute (String attribute) {
        addAttribute(attribute, annotated -> getAnnotationAttribute(annotated, attribute));
    }

    /**
     * 
     * @param annotated Annotated element so can be a field or a method.
     * @param attribute Name of the attribute. The annotation for this exporter must have a method attribute for this already.
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
            // TODO Auto-generated catch block
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

    public void runExporter (String fullClassName, String fileName)
            throws SecurityException, ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        processAnnotations(fullClassName);
        // writeToFile(fileName);
    }

    /**
     * Processed the annotations of a class by finding the annotated elements (fields or methods) and processing those elements.
     * @param clazz
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void processAnnotations (Class<?> clazz) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        processAnnotations(clazz.getDeclaredFields());
        processAnnotations(clazz.getDeclaredMethods());
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
    
    
    private void writeToFile(String fileName) throws IOException{
        
            //WriteProperties propertiesWriter = new WriteProperties(fileName);
            for(int i = 0; i < mySize; i++){
                    String propertyRoot = i+"_"+ myName;
                    
                    for (String attribute: myMap.keySet()) {
                        String propertyName = propertyRoot + "_" + attribute;
                        //propertiesWriter.addProperty(propertyName, myMap.get(attribute).get(i));
                    }
            }
            //propertiesWriter.writeToFile();
    }

    public static void main (String[] args) {
        ActionExporter e = new ActionExporter(IActionAnnotation.class);
        e.addAttribute("name", e::getDefaultName);
        e.addAttribute("description");
        e.addAttribute("numParams");
        System.out.println(e.myMap);
    }
}
