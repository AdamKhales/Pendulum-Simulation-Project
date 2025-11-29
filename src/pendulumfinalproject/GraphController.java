/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pendulumfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author archa
 */
public class GraphController implements Initializable {

    @FXML
    private VBox vbox;

    private LineChart<Number, Number> angleChart;
    private LineChart<Number, Number> velocityChart;
    private LineChart<Number, Number> accelerationChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // angle chart
        NumberAxis angleX = new NumberAxis();
        angleX.setLabel("Time (s)");

        NumberAxis angleY = new NumberAxis();
        angleY.setLabel("Angle (rad)");

        angleChart = new LineChart<>(angleX, angleY);
        angleChart.setTitle("Pendulum Angle");
        angleChart.setAnimated(false);
        angleChart.setPrefHeight(200);

        // velocity chart
        NumberAxis velX = new NumberAxis();
        velX.setLabel("Time (s)");

        NumberAxis velY = new NumberAxis();
        velY.setLabel("Velocity (rad/s)");

        velocityChart = new LineChart<>(velX, velY);
        velocityChart.setTitle("Pendulum Velocity");
        velocityChart.setAnimated(false);
        velocityChart.setPrefHeight(200);

        // acceleration chart
        NumberAxis accX = new NumberAxis();
        accX.setLabel("Time (s)");

        NumberAxis accY = new NumberAxis();
        accY.setLabel("Acceleration (rad/s²)");

        accelerationChart = new LineChart<>(accX, accY);
        accelerationChart.setTitle("Pendulum Acceleration");
        accelerationChart.setAnimated(false);
        accelerationChart.setPrefHeight(200);

        // add charts to VBox
        vbox.getChildren().addAll(angleChart, velocityChart, accelerationChart);
    }
}
