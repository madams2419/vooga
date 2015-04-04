package authoring_UserInterface;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import authoring_environment.SpriteElement;

/**
 * 
 * @author Daniel, Natalie
 *
 */
public class SpriteElementTester {

    @Test
    public void spriteTester() {
        SpriteElement sprite = new SpriteElement();
        List<String> path = Arrays.asList(new String[]{"sprites", "mario", "location", "x"}); 
        sprite.addElement(path, "5");
        System.out.println(sprite.toString());
    }

}
