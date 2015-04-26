package game_engine.scrolling;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class X2 {
    
    @action (name = "inherit")
    public void inherit () {
        System.out.println("inherit");
    }
    
    public static void main (String[] args) {
        XStream x = new XStream (new DomDriver());
        SubClassFinder.
    }
}
