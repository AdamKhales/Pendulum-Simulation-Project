/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pendulumfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author Shanxis
 */
public class PendulumController implements Initializable {

     @FXML
    private Slider airSlider;

    @FXML
    private Circle bob;

    @FXML
    private Pane drawingPane;

    @FXML
    private Button graphBtn;

    @FXML
    private Slider gravitySlider;

    @FXML
    private Slider lengthSlider;

    @FXML
    private Slider massSlider;

    @FXML
    private Line rope;

    @FXML
    private Button startPauseBtn;
    
    @FXML
    private Button resetBtn;
    
    //Physics variables
    private double mass = 10.0;
    private double gravity = 9.8;
    private double length = 1.0;
    private double airDrag = 0;
    
    //initial angle of pi/4 or 45 degrees
    private double angle = Math.PI / 4; 
    private double angularVelocity = 0.0;
    private double angularAcceleration = 0.0;
    
    //rope origin
    private double originX;
    private double originY;
    
    private AnimationTimer timer;
    private boolean running = false;
    private long lastTime = 0;

    public PendulumController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            originX = drawingPane.getWidth() / 2;
            originY = 3;
            updatePendulumLayout();
        });
        
        // TODO
        drawingPane.layoutBoundsProperty().addListener((obs, old, bounds) -> {
            originX = bounds.getWidth() / 2;
            originY = 0;
            updatePendulumLayout();
        });
        
        updatePendulumLayout();
        
        //Adding the listeners to all the sliders
        massSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mass = newVal.doubleValue();
        });
        
        gravitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gravity = newVal.doubleValue();
        });
        
        lengthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {

            double oldLength = length;
            double newLength = newVal.doubleValue();

            // update length
            length = newLength;

            // conserve angular momentum so no weird loops happen
            angularVelocity *= (oldLength / newLength);
            
            //changes the length of the line (rope)
            updatePendulumLayout();
        });
        
        airSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            airDrag = newVal.doubleValue();
        });
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime > 0) {
                    //finds the difference between the current time and the last recorded time and divides by 10^9 to convert nanoseconds into seconds
                    double dt = (now - lastTime) / 1e9;
                    updatePhysics(dt);
                    updatePendulumLayout();
                }
                //sets the last recorded time to the current one
                lastTime = now;
            }
        };
    }    

    @FXML
    private void startPauseBtnPressed(ActionEvent event) {
        //checks if the animation is running
        if (running) {
            timer.stop();
            startPauseBtn.setText("Start");
            //reset the lastTime so dt doesnt change or jump (was one of the previous issues)
            lastTime = 0;
        } else {
            timer.start();
            startPauseBtn.setText("Pause");
        }
        //If it was true, it becomes false and the opposite is also true.
        running = !running;
    }
    
    @FXML
    void resetBtnPressed(ActionEvent event) {
        //Back to the initial variables
        angle = Math.PI / 4;
        angularVelocity = 0.0;
        angularAcceleration = 0.0;

        // Reset rope and bob visuals
        
        updatePendulumLayout();
    }

    @FXML
    private void graphBtnPressed(ActionEvent event) {
        //TODO
    }
    
    /**
     * updates the length of the rope and the position of the bob when something is changed.
     */
    private void updatePendulumLayout() {
        double pixelLength = length * 100;
        double bobX = originX + pixelLength * Math.sin(angle);
        double bobY = originY + pixelLength * Math.cos(angle);
        
        rope.setStartX(originX);
        rope.setStartY(originY);
        
        
        bob.setCenterX(bobX);
        bob.setCenterY(bobY);
        
        // a^2 = b^2 + c^2 to find the distance to move the rope such that it hits the edge of the bob
        double distance  = Math.sqrt( ((bobX - originX) * (bobX - originX)) + ((bobY - originY) * (bobY - originY)));

        double endX = bobX - (bobX - originX) / distance * bob.getRadius();
        double endY = bobY -  (bobY - originY)  / distance * bob.getRadius();
        
        
        rope.setEndX(endX);
        rope.setEndY(endY);
        
    }
    
    /**
     * updates the acceleration, the velocity and the angle of the pendulum.
     * @param dt the change in time (time 2 - time 1), interval of the time passed.
     */
    private void updatePhysics(double dt) {
        
        // α = −g/L ⋅ ​sin(θ)
        angularAcceleration = -(gravity/length) * Math.sin(angle);
        
        // ω = ω + α⋅Δt 
        angularVelocity += angularAcceleration * dt;
        
        angularVelocity *= Math.exp(-airDrag * dt);
        
        //θ = θ + ω⋅Δt
        angle += angularVelocity * dt;
        
    }
    
}
