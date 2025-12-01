/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

//https://github.com/AdamKhales/Pendulum-Simulation-Project.git

package pendulumfinalproject;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author 2493077
 */
public class PendulumFinalProject extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("Pendulum.fxml"));
        //sets the icon of the stage
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/pendulumIcon.jpg")));
        
        Scene scene = new Scene(root);
        stage.setTitle("Pendulum Simulator");
        stage.setScene(scene);
        stage.show();
    }
    
}
