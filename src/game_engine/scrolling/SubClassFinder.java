package game_engine.scrolling;

import game_engine.annotation.ActionExporter;
import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IActor;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;


public class SubClassFinder {
    private static final String JAVA_SUFFIX = ".java";
    private static final String SRC = "src" + File.separator;
    private static final String MAIN_DIR = "src/game_engine";

    private SubClassFinder() {}
    
    public static File toFile (Class<?> clazz) {
        String fileName = toFileName(clazz.getName());
        return new File(fileName);
    }

    public static Class<?> toClass (File file) throws ClassNotFoundException {
        String className = toClassName(file.getPath());
        return Class.forName(className);
    }

    public static boolean isJavaClass (File file) {
        return file.getPath().endsWith(JAVA_SUFFIX);
    }

    public static String toFileName (String className) {
        return toDirName(className) + JAVA_SUFFIX;
    }

    public static String toDirName (String packageName) {
        return SRC + packageName.replace('.', File.separatorChar);
    }

    public static String toClassName (String fileName) {
        return fileName.replace(File.separatorChar, '.').substring(SRC.length(),
                                                                   fileName.length() -
                                                                           JAVA_SUFFIX.length());
    }

    public static boolean isAbstract (Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public static Map<String, Class<?>> findInstantiableTypes (Class<?> clazz) throws ClassNotFoundException {
        return findTypes(new File(MAIN_DIR), fileClass -> isInstantiable(fileClass, clazz),
                         SubClassFinder::removeSuffix);
    }
    
    public static Map<String, Class<?>> findTypes (File file,  Predicate<Class<?>> predicate,
                                                   Function<Class<?>, String> formatter) throws ClassNotFoundException {
        Map<String, Class<?>> types = new HashMap<>();
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                types.putAll(findTypes(f, predicate, formatter));
            }
        }
        else if (isJavaClass(file)) {
            try{
                Class<?> fileClazz = toClass(file);
                if (predicate.test(fileClazz)){
                    types.put(formatter.apply(fileClazz), fileClazz);
                }
            }
            catch (Exception e) {
                System.out.println(file);
            }
        }
        return types;
    }

    public static String formatName (Class<?> clazz) {
        String[] split = splitCamelCase(clazz.getSimpleName());
        return String.join(" ", split);
    }

    public static String removeSuffix (Class<?> clazz) {
        String[] split = splitCamelCase(clazz.getSimpleName());
        return String.join(" ", Arrays.copyOf(split, split.length - 1));
    }

    public static boolean isInstantiable (Class<?> clazz, Class<?> superClass) {
        return superClass.isAssignableFrom(clazz) && !isAbstract(clazz) && !clazz.isInterface();
    }

    public static String[] splitCamelCase (String string) {
        return string.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
    }
    
    public static Map<String, Map<String, Constructor<?>[]>> getConstructorMap (Class<?>... classes) throws ClassNotFoundException, SecurityException {
        Map<String, Map<String, Constructor<?>[]>> map = new HashMap<>();
        for (Class<?> clazz: classes) {
            map.put(clazz.getSimpleName(), getConstructors(clazz));
        }
        return map;
    }
    
    public static Map<String, Constructor<?>[]> getConstructors (Class<?> clazz) throws ClassNotFoundException, SecurityException {
        Map<String, Constructor<?>[]> constructors = new HashMap<>();
        for (Entry<String, Class<?>> entry: findInstantiableTypes(clazz).entrySet()) {
            constructors.put(entry.getKey(), entry.getValue().getConstructors());
        }
        return constructors;
    }

    public static void main (String[] args) {
        try {
            
            
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
