package game_engine.scrolling.tracker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HelperParser {
    private Map<Class<?>, Function<String, ?>> myStringParser;
    private Map<Class<?>, SupplierWithThrow<?>> myOtherParser;
    
    public interface SupplierWithThrow<T> {
        public T supply() throws Exception;
    }
    
    public HelperParser() {
        myStringParser = new HashMap<>();
        myOtherParser = new HashMap<>();
        initPrimitives();
    }
    
    public Object parse (String string, Class<?> clazz) {
        return myStringParser.get(clazz).apply(string);
    }
    
    public Object parse (Class<?> clazz) throws Exception {
        return myOtherParser.get(clazz).supply();
    }
    
    public void put (Class<?> clazz, SupplierWithThrow<?> supplier) {
        myOtherParser.put(clazz, supplier);
    }
    
    private void initPrimitives() {
        myStringParser.put(int.class, Integer::parseInt);
        myStringParser.put(double.class, Double::parseDouble);
        myStringParser.put(String.class, string -> string);
    }
    
    public void putPrimitive (Class<?> clazz, Function<String, Object> func) {
        myStringParser.put(clazz, func);
    }
}
