package game_engine.scrolling;

import game_engine.behaviors.IAction;
import java.lang.reflect.Method;
import javafx.scene.image.Image;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ActionMethod implements IAction{
    private Method myMethod;
    private Object myObject;
    
    public ActionMethod (Method method, Object object) {
        myMethod = method;
        myObject = object;
    }
    
    @Override
    public void execute (String ... params) {
        if (myMethod.getParameterCount() != params.length) {
            System.out.println("you screwed up");
            return;
        }
        Object[] objects = new Object[myMethod.getParameterCount()];
        for (int i = 0; i < myMethod.getParameterCount(); i ++ ){
            
        }

    }
    
    
    public static void main (String[] args) {
        XStream x = new XStream(new DomDriver());

    }

}
