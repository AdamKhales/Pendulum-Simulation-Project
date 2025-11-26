/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pendulumfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
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
    
    private double mass = 10.0;
    private double gravity = 9.8;
    private double length = 1.0;
    private double airDrag = 0;
    
    //initial angle of pi/4 or 45 degrees
    private double angle = Math.PI / 4; 
    private double angularVelocity = 0.0;
    private double angularAcceleration = 0.0;
    
    //rope origin
    private final double originX = pendulumPane.getLayoutX() + pendulumPane.getWidth()/ 2.0;
    private final double originY = pendulumPane.getLayoutY();
    
    
    

    public PendulumController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startPauseBtnPressed(ActionEvent event) {
    }

    @FXML
    private void graphBtnPressed(ActionEvent event) {
    }
    
}
