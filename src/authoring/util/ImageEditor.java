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
    private static final double maximumOpacity = 1.0;

    /**
     * This method reduces the opacity according to a reduction ratio between 0 and 1.
     * 
     * @param imageView
     * @param reductionRatio
     */
    public static void reduceOpacity (ImageView imageView, double reductionRatio) {
        imageView.setOpacity(reductionRatio);
    }

    /**
     * This method should be used after reduceOpacity has been called in order to restore
     * the original opacity of the image.
     * 
     * @param imageView
     * @param reductionRatio
     */
    public static void restoreOpacity (ImageView imageView) {
        imageView.setOpacity(maximumOpacity);
    }

    /**
     * This method adjusts the height and width of an ImageView so the ratio of height to width
     * remains the same but they are both less than or equal to the maximum allowable width and
     * height.
     * 
     * @param imageView
     * @param maxWidth
     * @param maxHeight
     */
    public static void setToAppropriateWidthAndHeight (ImageView imageView,
                                                       int maxWidth,
                                                       int maxHeight) {
        imageView.setPreserveRatio(true);

        boolean shouldScaleHeight =
                (imageView.getImage().getHeight() / maxHeight) >
                (imageView.getImage().getWidth() / maxWidth);

        if (shouldScaleHeight) {
            imageView.setFitHeight(maxHeight);
        }
        else {
            imageView.setFitWidth(maxWidth);
        }
    }
}
