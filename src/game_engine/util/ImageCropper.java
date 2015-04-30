package game_engine.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class ImageCropper {

    public ImageCropper(String Image, int xBegin, int yBegin, int width, int height,String writeName){
        try {
            BufferedImage original = ImageIO.read(new File(Image));
            BufferedImage cropped = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            cropped.getGraphics().drawImage(original,0,0, width, height, xBegin, yBegin, 
                                            xBegin+width, yBegin+height, null);
            
            ImageIO.write(cropped, "png", new File(writeName));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        ImageCropper cropper = new ImageCropper("resources/images/paperMarioSpriteSheetLarge.png",580,10,650,1000,"resources/images/cropTest.png");
    }
}
