# Pendulum Simulation Project

This project is a **JavaFX-based pendulum simulator** that visually animates a swinging pendulum and displays its motion through graphs such as angle vs. time and velocity vs. time. It is designed as a small physics/graphics project that demonstrates animation, geometry, and UI control in JavaFX.

## Features:
- Real-time pendulum animation
- Custumizable pendulum parameters (mass, length of the rope, gravity, damping, etc.)
- Graph window that displays graphs relevent to the pendulum
- Reset button that resets the animation and clears all graphs
  

### Requirements
To run this project, there a few requirements. You must have JDK 17+ installed on your computer as well as an IDE that supports javaFX.

## How to run the project
1. Clone the repository using gitbash or the IDE: (git clone https://github.com/AdamKhales/Pendulum-Simulation-Project.git)
   
2. Open the project in your IDE

3. Add the JavaFx library to your project if the cloned ones don't work

4. Run the <mark>Main</mark> class to start the pendulum simulation

### In the app:
5. Select the variables you want to change in the simulation

6. Press the button start when you want to start the simulation

The app allows you to pause the movement of the pendulum whenever you want by pressing the Pause button and starting it again by pressing the same button. If you want, you can also press on the graphs button to open another window where you can see three different graphs that show the Angle vs Time, Velocity vs Time, and Acceleration vs Time of the pendulum. These three graphs update in real time and pause when you pause the pendulum. There is also a Reset button in the app that allows the user to reset the position of the Pendulum to its initial angle which is 45 degrees or pi/2 radiants. 

## Save and Load
The Pendulum Simulation app allows the user to save the variables to a file that ends with .pendulum. Naturally, the app also has a feature that lets the user load back the selected variables from a valid file using FileChooser.
   




