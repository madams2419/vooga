package authoring.panes.centerPane.modes;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import authoring.dataEditors.Sprite;
import authoring.dialogs.FileChooserDialog;
import authoring.panes.centerPane.Region;
import authoring.userInterface.SpriteCursor;

public class Mode {
    public void canvasClicked(MouseEvent e, Region myCurrentRectangle, Scene myScene, Group myGroup, List<Sprite> listOfSprites) {
        try {
                if (e.getButton() == MouseButton.SECONDARY) {
                        FileChooserDialog chooser = new FileChooserDialog(
                                        new FileChooser.ExtensionFilter("JPG files (*.jpg)",
                                                        "*.JPG"), new FileChooser.ExtensionFilter(
                                                        "PNG files (*.png)", "*.PNG"));
                        myCurrentRectangle.setBackgroundImage(chooser.grabImage());
                }
                Sprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();

                s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
                s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
                myGroup.getChildren().add(s);
                listOfSprites.add(s);
                myScene.setCursor(ImageCursor.DEFAULT);
                s.setOnMouseDragged(a -> imageDragged(a, s));

        } catch (ClassCastException a) {
        } catch (NullPointerException b) {
        }
}
    
    private void imageDragged(MouseEvent a, Sprite s) {
        s.setXPosition(a.getSceneX() - (s.getImage().getWidth() / 2));
        s.setYPosition(a.getSceneY() - (s.getImage().getHeight() / 2));
    }
}
