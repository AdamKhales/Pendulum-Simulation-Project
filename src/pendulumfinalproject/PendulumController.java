/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pendulumfinalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    
    @FXML
    private Button loadBtn;
    
    @FXML
    private Button saveBtn;

    //Physics variables
    private double mass = 10.0;
    private double gravity = 9.8;
    private double length = 1.0;
    private double airDrag = 0;

    //initial angle of pi/4 or 45 degrees
    private double angle = Math.PI / 4;
    private double angularVelocity = 0.0;
    private double angularAcceleration = 0.0;

    private GraphController graphController = null;

    private Stage graphStage = null;
    private double graphTime = 0;
    
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
            
            //find the stage on which the pendulum is 
            Stage pendulumStage = (Stage) drawingPane.getScene().getWindow();
            
            //if the pendulum stage is closed the graph stage also closes if its open
            pendulumStage.setOnCloseRequest(e -> {
                if (graphStage != null) {
                    graphStage.close();
                }
            });
        });

        //changes the location of the pendulum if the screen changes sizes 
        drawingPane.layoutBoundsProperty().addListener((obs, old, bounds) -> {
            originX = bounds.getWidth() / 2;
            originY = 3;
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
            length = newVal.doubleValue();
            //change the length of the rope in the layout
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
                    updateGraphs(dt);
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
            startPauseBtn.setStyle("-fx-background-color: lightgreen;"
                    + "-fx-border-color: black;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5");
            
        } else {
            lastTime = 0;
            timer.start();
            startPauseBtn.setText("Pause");
            startPauseBtn.setStyle("-fx-background-color: crimson;"
                    + "-fx-border-color: black;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5");

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
        graphTime = 0;

        // Reset rope and bob visuals
        updatePendulumLayout();
        //clears the graphs if the stage is open
        if (graphController != null) {
            graphController.clearGraphs();
        }
    }

    @FXML
    private void graphBtnPressed(ActionEvent event) {
        try {
            if (graphStage == null) {
                //opens the stage if the stage is not null
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Graph.fxml"));
                Parent root = loader.load();
                graphController = loader.getController();

                graphStage = new Stage();
                graphStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/pendulumIcon.jpg")));
                graphStage.setScene(new Scene(root));
                graphStage.setTitle("Pendulum Graphs");
                graphStage.setOnCloseRequest(e -> {
                    graphStage = null;
                    graphController = null;
                });

                graphStage.show();
            } else {
                graphStage.toFront();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * loads variables from a file to set the initial variables of the pendulum
     * @param event 
     */
    @FXML
    void loadVariables(ActionEvent event) throws FileNotFoundException {
        //creates a file chooser so the user can pick a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Pendulum Settings");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Pendulum Files", "*.pendulum")
        );

        //show the dialog and if the user cancels the result is null
        File file = fileChooser.showOpenDialog(null);
        if (file == null) return;

        try (java.util.Scanner sc = new java.util.Scanner(file)) {

            //temp values so that we are sure the values are all loaded later before applying 
            Double newLength = null;
            Double newMass = null;
            Double newAngle = null;
            Double newVelocity = null;
            Double newGravity = null;
            Double newAirDrag = null;

            //read the file line by line
            while (sc.hasNextLine()) {
                //seperates the lines to find the variable and its value (mass=0.1 -> variable(mass) and value(0.1)
                String line = sc.nextLine();
                String[] parts = line.split("=");

                //skip malformed lines (e.g if it has more than one =)
                if (parts.length != 2) continue;

                String variable = parts[0].trim();
                String value = parts[1].trim();

                //verify which variable it is 
                switch (variable) {
                    case "length":
                        newLength = Double.parseDouble(value);
                        break;
                    case "mass":
                        newMass = Double.parseDouble(value);
                        break;
                    case "angle":
                        newAngle = Double.parseDouble(value);
                        break;
                    case "velocity":
                        newVelocity = Double.parseDouble(value);
                        break;
                    case "gravity":
                        newGravity = Double.parseDouble(value);
                        break;
                    case "damping":
                        newAirDrag = Double.parseDouble(value);
                        break;
                }
            }

            //make sure all the values exist before applying them
            if (newLength == null || newMass == null || newAngle == null ||
                newVelocity == null || newGravity == null || newAirDrag == null) {

                System.out.println("File is missing values");
                return;
            }

            //set the loaded variables 
            length = newLength;
            mass = newMass;
            angle = newAngle;
            angularVelocity = newVelocity;
            gravity = newGravity;
            airDrag = newAirDrag;
            
            //update layout
            updatePendulumLayout();
            
            //reset graphs
            if (graphController != null) {
                graphController.clearGraphs();
            }
            
            //reset times 
            graphTime = 0;
            lastTime = 0;
            
            //update the sliders to be the new values
            lengthSlider.setValue(length);
            massSlider.setValue(mass);
            gravitySlider.setValue(gravity);
            airSlider.setValue(airDrag);
        }
    }
    
    /**
     * saves all the variables in a file using FileChooser
     * @param event 
     */
    @FXML
    void saveVariables(ActionEvent event) {
        //create a fileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Pendulum Settings");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Pendulum Files", "*.pendulum")
        );
        
        File file = fileChooser.showSaveDialog(null);
        if (file == null) return;
        
        try {
            //if the file does not exist it creates a new one with that name
            if (!file.exists()) {
                file.createNewFile();
            }

            try (PrintWriter out = new PrintWriter(file)) {
                //writes all the variables in the file
                out.println("length=" + length);
                out.println("mass=" + mass);
                out.println("angle=" + angle);
                out.println("velocity=" + airDrag);
                out.println("gravity=" + gravity);
                out.println("damping=" + airDrag);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * updates the length of the rope and the position of the bob when called
     */
    private void updatePendulumLayout() {
        //make it a little bigger visually when you change the length
        double pixelLength = length * 100;
        double bobX = originX + pixelLength * Math.sin(angle);
        double bobY = originY + pixelLength * Math.cos(angle);

        rope.setStartX(originX);
        rope.setStartY(originY);

        bob.setCenterX(bobX);
        bob.setCenterY(bobY);

        // a^2 = b^2 + c^2 to find the distance to move the rope such that it hits the edge of the bob
        double distance = Math.sqrt(((bobX - originX) * (bobX - originX)) + ((bobY - originY) * (bobY - originY)));

        double endX = bobX - (bobX - originX) / distance * bob.getRadius();
        double endY = bobY - (bobY - originY) / distance * bob.getRadius();

        rope.setEndX(endX);
        rope.setEndY(endY);

    }

    /**
     * updates the acceleration, the velocity and the angle of the pendulum.
     *
     * @param dt the change in time (time 2 - time 1), interval of the time
     * passed.
     */
    private void updatePhysics(double dt) {
        // α = −g/L ⋅ ​sin(θ)
        angularAcceleration = -(gravity / length) * Math.sin(angle);

        // ω = ω + α⋅Δt 
        angularVelocity += angularAcceleration * dt;
        
        //makes the damping exponential which is more realistic
        angularVelocity *= Math.exp(-airDrag * dt);

        //θ = θ + ω⋅Δt
        angle += angularVelocity * dt;

    }
       /**
        * updates the graphs by adding points in it
        * @param dt change of time
        */
       private void updateGraphs(double dt) {
        if (graphController == null) return;
        //add each change of time to the total time of the graph
        graphTime += dt;
        
        //add the points for each graph
        graphController.addAnglePoint(graphTime, angle);
        graphController.addVelocityPoint(graphTime, angularVelocity);
        graphController.addAccelerationPoint(graphTime, angularAcceleration);
    }

}