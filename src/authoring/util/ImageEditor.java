package authoring.util;

import javafx.scene.image.ImageView;


/**
 * This class is responsible for editing ImageViews, such as reducing/restoring their opacity and
 * resizing it when the size of the image is limited.
 * 
 * @author Natalie Chanfreau
 *
 */
public class ImageEditor {

    public static void reduceOpacity (ImageView s, double reductionRatio) {
        s.setOpacity(s.getOpacity() * reductionRatio);
    }

    public static void restoreOpacity (ImageView s, double reductionRatio) {
        s.setOpacity(s.getOpacity() / reductionRatio);
    }

    public static void setToAppropriateWidthAndHeight (ImageView imageView,
                                                       int maxWidth,
                                                       int maxHeight) {
        double newHeight = maxHeight;
        double width = imageView.getFitWidth();
        double newWidth = width > maxWidth ? maxWidth : width;

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(newHeight);
        imageView.setFitWidth(newWidth);

    }

}
