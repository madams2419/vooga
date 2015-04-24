package game_engine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class XStreamExp extends X2{
    public class wrap {
        private Method method;
        private Object o;
        private Object[] args;
        wrap (Method method, Object o, Object[] args) {
            this.method = method;
            this.o = o;
            this.args = args;
        }
        
        public void run () throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            method.invoke(o, args);
        }
    }
    
    @action(name = "shoot")
    public void shoot () {
        System.out.println("shoot");
    }

    @action(name = "run")
    public int run () {
        System.out.println("run");
        return 1;
    }

    @action(name = "jump")
    public void jump (int num) {
        System.out.println("jump" + num);
    }

    public Collection<Method> processMethods (Class<?> clazz) {
        return Arrays.asList(clazz.getMethods()).stream()
                .filter(m -> m.isAnnotationPresent(action.class)).collect(Collectors.toList());
    }
    
    public Collection<Method> abilities (Class<?> clazz) {
        Collection<Method> methods = processMethods(clazz);
        if (clazz.getSuperclass() != null) {
            methods.addAll(abilities(clazz.getSuperclass()));
        }
        return methods;
    }

    public static void main (String[] args) {
        try {
            XStreamExp e = new XStreamExp();
            XStream x = new XStream(new DomDriver());
            Collection<Method> methods = e.processMethods(e.getClass());
            String s = x.toXML(methods);
            Collection<Object> objects = Arrays.asList(e, e, e, e);
            Collection<Object[]> stuff = new ArrayList<>();
            for (Method m: methods) {
                if (m.getParameterCount() ==0) {
                    stuff.add(new Object[]{});
                }
                else{
                    stuff.add(new Object[]{1});
                }
            }
            Collection<wrap> c = new ArrayList<>();
            Iterator<Method> i1 = methods.iterator();
            Iterator<Object> i2 = objects.iterator();
            Iterator<Object[]> i3 = stuff.iterator();
            for (int i =0; i < methods.size(); i ++) {
                c.add(e.new wrap(i1.next(), i2.next(), i3.next()));
            }
            s = x.toXML(c);
            System.out.println(s);
            Collection<wrap> d = (Collection<wrap>) x.fromXML(s);
            for (wrap d2: d) {
                d2.run();
            }
            
        }
        catch (Exception e) {
            System.out.println("here?");
            e.printStackTrace();
        }
    }
}
