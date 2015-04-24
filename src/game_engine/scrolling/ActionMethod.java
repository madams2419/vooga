package game_engine.scrolling;

import game_engine.behaviors.IBehavior;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ActionMethod implements IBehavior{
    private Method myMethod;
    private Object myObject;
    private Object[] myArgs;
    
    public ActionMethod (Method method, Object object, Object... args) {
        myMethod = method;
        myObject = object;
        myArgs = args;
    }
    
    
    @Override
    public void perform () {
        try {
            myMethod.invoke(myObject, myArgs);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public static void main (String[] args) {
        XStream x = new XStream(new DomDriver());

    }



}
