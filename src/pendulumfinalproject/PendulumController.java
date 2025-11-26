/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pendulumfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
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
    private Pane pendulumPane;
    @FXML
    private Slider massSlider;
    @FXML
    private Slider gravitySlider;
    @FXML
    private Slider lengthSlider;
    @FXML
    private Slider airSlider;
    @FXML
    private Button startPauseBtn;
    @FXML
    private Button graphBtn;
    @FXML
    private Line rope;
    @FXML
    private Circle bob;
    
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

    public PendulumController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        originX = pendulumPane.getLayoutX() + pendulumPane.getWidth()/ 2.0;
        originY = pendulumPane.getLayoutY();
        
        updatePendulumLayout();
        
        //Adding the listeners to all the sliders
        massSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mass = newVal.doubleValue();
        });
        
        gravitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gravity = newVal.doubleValue();
        });
        
        lengthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            length = newVal.doubleValue();
            //change the length of the rope in the layout
            updatePendulumLayout();
        });
        
        airSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            airDrag = newVal.doubleValue();
        });
    }    

    @FXML
    private void startPauseBtnPressed(ActionEvent event) {
    }

    @FXML
    private void graphBtnPressed(ActionEvent event) {
    }
    
    /**
     * updates the length of the rope and the position of the bob when something is changed.
     */
    private void updatePendulumLayout() {
        double bobX = originX + length * 10 * Math.sin(angle);
        double bobY = originY + length * 10 * Math.cos(angle);
        
        rope.setStartX(originX);
        rope.setStartY(originY);
        rope.setEndX(bobX);
        rope.setEndY(bobY);
        
        bob.setCenterX(bobX);
        bob.setCenterY(bobY);
    }
    
    /**
     * updates the acceleration, the velocity and the angle of the pendulum.
     * @param dt the change in time (time 2 - time 1), interval of the time passed.
     */
    private void updatePhysics(double dt) {
        double damping = 1 - airDrag;
        
        // α = −g/L ⋅ ​sin(θ)
        angularAcceleration = -(gravity/length) * Math.sin(angle);
        
        // ω = ω + α⋅Δt 
        angularVelocity += angularAcceleration * dt;
        
        angularVelocity *= damping;
        
        //θ = θ + ω⋅Δt
        angle += angularVelocity * dt;
        
    }
    
}
