package game_engine.scrolling;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ScrollTester extends Application
{
    private Group myGroup;
    private Rectangle me;
    private Circle circle;
    private PerspectiveCamera cam;
    private DoubleBinding camHalfWidth;
    private DoubleBinding camHalfHeight;
    
    public double tan (double degree) {
        return Math.tan(Math.toRadians(degree));
    }
    
    @Override
    public void start (Stage stage) throws Exception {
        circle = new Circle(20, Color.RED);
        Group myGroup = new Group();
        ImageView r = new ImageView("images/brick.png");
        r.setFitHeight(1000);
        r.setFitWidth(1000);
        Rectangle r2 = new Rectangle(100, 100, Color.BLUE);
        myGroup.getChildren().addAll(r, r2);
        SubScene scene = new SubScene(myGroup, 300, 300);
        cam = new PerspectiveCamera(true);
        cam.setTranslateZ(-500);
        cam.setFarClip(5000);
        DoubleBinding hAngle = new DoubleBinding() {
            {
                super.bind(cam.fieldOfViewProperty(), scene.widthProperty(), scene.heightProperty());
            }

            @Override
            protected double computeValue () {
                return Math.toDegrees(Math.atan(scene.getWidth() / scene.getHeight() * tan(cam.getFieldOfView())));
            }
        };
        
        camHalfWidth = new DoubleBinding() {
            {
                super.bind(cam.translateZProperty(), hAngle);
            }

            @Override
            protected double computeValue () {
                return -cam.getTranslateZ() * tan(hAngle.get() /2);
            }
        };
        
        camHalfHeight = new DoubleBinding () {
            {
                super.bind(cam.translateZProperty(), cam.fieldOfViewProperty());
            }

            @Override
            protected double computeValue () {
                return -cam.getTranslateZ() * tan (cam.getFieldOfView() /2);
            }
        };
        
        DoubleProperty myX = r2.translateXProperty();
        DoubleProperty myY = r2.translateYProperty();
        DoubleBinding anotherX = new DoubleBinding() {
            {
                super.bind(myX);
            }

            @Override
            protected double computeValue () {
                return focusX(myX.get() + r2.getWidth());
            }
        };
        DoubleBinding inBoundsX = new DoubleBinding() {
            {
                super.bind(camHalfWidth, anotherX);
            }

            @Override
            protected double computeValue () {
                double value = Math.max(camHalfWidth.get(), anotherX.get());
                return Math.min(1000 - camHalfWidth.get(), value);
            }
        };
        
        
        

        DoubleBinding inBoundsY = new DoubleBinding() {
            {
                super.bind(camHalfHeight, myY);
            }

            @Override
            protected double computeValue () {
                double value = Math.max(camHalfHeight.get(), myY.get() + r2.getHeight());
                return Math.min(myGroup.getBoundsInLocal().getMaxY() - camHalfHeight.get(), value);
            }
        };
        
        ObservableNumberValue[] n = new ObservableNumberValue[] {
            inBoundsX,
            inBoundsY,
            hAngle,
            camHalfWidth,
            camHalfHeight,
            cam.translateXProperty(),
            cam.translateYProperty(),
            myX,
            myY
        };
        for (ObservableNumberValue nb: n){
            nb.addListener((change) -> nb.getValue());
        }
        r2.translateXProperty().addListener((change) -> {
            double amount = r2.translateXProperty().get()  + r2.getWidth() *0.5;
            cam.setTranslateX(inBoundsX(focusX(amount)));
        });
        r2.translateYProperty().addListener((change) -> {
            double amount = r2.translateYProperty().get()  + r2.getHeight() *0.5;
            cam.setTranslateY(inBoundsY(focusY(amount)));
            
        });
        double amount = r2.translateXProperty().get() + r2.getWidth() *0.5;
        cam.setTranslateX(inBoundsX(focusX(amount)));
        amount = r2.translateYProperty().get()  + r2.getHeight() *0.5;
        cam.setTranslateY(inBoundsY(focusY(amount)));
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                r2.setTranslateX(r2.getTranslateX() + 5);
            }
            else if (e.getCode() == KeyCode.LEFT) {
                r2.setTranslateX(r2.getTranslateX() - 5);
            }
            else if (e.getCode() == KeyCode.UP) {
                r2.setTranslateY(r2.getTranslateY() - 5);
            }
            else if (e.getCode() == KeyCode.DOWN) {
                r2.setTranslateY(r2.getTranslateY() + 5);
            }
            zoom(e);
        });
        
        scene.setOnMousePressed(e -> {
            scene.setCursor(Cursor.MOVE);
            double pressedX = e.getX();
            double pressedY = e.getY();
            scene.setOnMouseDragged(t -> {
                
                double amountX = -t.getX() + pressedX + cam.getTranslateX();
                cam.setTranslateX(inBoundsX(focusX(amountX)));
                double amountY = -t.getY() + pressedY + cam.getTranslateY();
                cam.setTranslateY(inBoundsY(focusY(amountY)));
            });
            scene.setOnMouseReleased(f -> scene.setCursor(Cursor.DEFAULT));
        });
        
        scene.setCamera(cam);
        Scene main = new Scene(new Group(scene), 300, 300);
        scene.requestFocus();
        stage.setScene(main);        
        stage.show();
    }
    
    private double inBoundsY (double focusY) {
        double value = Math.max(camHalfHeight.get(), focusY);
        return Math.min(1000 - camHalfHeight.get(), value);
    }

    private double focusY (double amount) {
        if (amount - cam.getTranslateY() > 20) {
            return amount - 20;
        }
        else if (amount - cam.getTranslateY() < -20){
            return amount + 20;
        }
        return cam.getTranslateY();
    }

    private void zoom (KeyEvent e) {
        if (e.getCode() == KeyCode.PAGE_UP) {
            cam.setTranslateZ(cam.getTranslateZ() + 5);
        }
        if (e.getCode() == KeyCode.PAGE_DOWN) {
            cam.setTranslateZ(cam.getTranslateZ() - 5);
        }
    }
    
    public double focusX (double x) {
        if (x - cam.getTranslateX() > 20) {
            return x - 20;
        }
        else if (x - cam.getTranslateX() < -20){
            return x + 20;
        }
        return cam.getTranslateX();
    }

    public double inBoundsX (double x) {
        double value = Math.max(camHalfWidth.get(), x);
        return Math.min(500 - camHalfWidth.get(), value);
    }
    
    public static void main (String[] args) {
        launch(args);
    }





}
