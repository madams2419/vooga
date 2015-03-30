package authoring_interfaces;

import javafx.scene.image.ImageView;

public interface AuthoringView {
    /**
     * puts image on our ScrollPane Group, adds ImageView  to our Map<ImageView, Element>, 
     * and calls XML writer to write parameters to the XML file
     * @param image
     */
    void putOnView(ImageView image);
    
    /**
     * removes ImageView from both our ScrollPane and our Map
     */
    void removeFromView(ImageView image);

}
