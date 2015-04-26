package game_engine.scrolling.tracker;

import game_engine.scrolling.SubClassFinder;
import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.scrolling.tracker.HelperParser.SupplierWithThrow;
import game_player.XMLParser;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.function.Function;
import javafx.scene.Group;

public class ScrollBuilder {
    private XMLParser myParser;
    private HelperParser myHelper;
    
    private void putPrimitive (Class<?> clazz, Function<String, Object> func) {
        SupplierWithThrow<?> sup = () -> {
            return func.apply(myParser.getValue("value"));
        };
        myHelper.put(clazz, sup);
    }
    
    public AbstractTracker buildTracker(Group group) throws Exception {
        myHelper.put(IScroller.class, this::buildScroller);
        myHelper.put(IScrollFocus.class, this::buildFocuser);
        putPrimitive(double.class, Double::parseDouble);
        Class<?> clazz = new Object(){}.getClass().getEnclosingMethod().getReturnType();
        return (AbstractTracker) getObject(clazz);
    }
    
    public IScroller buildScroller() throws Exception {
        Class<?> clazz = new Object(){}.getClass().getEnclosingMethod().getReturnType();
        return (IScroller) getObject(clazz);
    }
    
    public IScrollFocus buildFocuser() throws Exception {
        Class<?> clazz = new Object(){}.getClass().getEnclosingMethod().getReturnType();
        return (IScrollFocus) getObject(clazz);
    }
    
    public Object getObject (Class<?> clazz) throws Exception {
        Constructor<?> constructor = getConstructor(clazz);
        Object[] params = getParams(constructor);
        return constructor.newInstance(params);
    }
    public Constructor<?> getConstructor (Class<?> clazz) throws ClassNotFoundException, SecurityException {
        Map<String, Constructor<?>[]> map = SubClassFinder.getConstructors(clazz);
        String type = myParser.getValue("type");
        int constructorID = Integer.parseInt(myParser.getValue("constructorID"));
        return map.get(type)[constructorID];
    }
    
    public Object[] getParams(Constructor<?> constructor) throws Exception {
        Object[] params = new Object[constructor.getParameterCount()];
        myParser.moveDown("params");
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            myParser.moveDown("param_" + i);
            params[i] = myHelper.parse(constructor.getParameterTypes()[i]);
            myParser.moveUp();
        }
        myParser.moveUp();
        return params;
    }
}
