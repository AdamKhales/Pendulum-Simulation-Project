# Pendulum Simulation Project

This project is a **JavaFX-based pendulum simulator** that visually animates a swinging pendulum and displays its motion through graphs such as angle vs. time and velocity vs. time. It is designed as a small physics/graphics project that demonstrates animation, geometry, and UI control in JavaFX.

## Features:
- Real-time pendulum animation
- Custumizable pendulum parameters (mass, length of the rope, gravity, damping, etc.)
- Graph window that displays graphs relevent to the pendulum
- Reset button that resets the animation and clears all graphs
  

### Requirements
To run this project, there are a few requirements: You must have JDK 17+ installed on your computer as well as an IDE that supports javaFX.

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

# Teamwork Summary - A section where the contribution of each team member is clearly stated
##  Adam Khales
- Created the Pendulum FXML
- Created PendulumController
- Implemented the Event handlers for all buttons 
- Created the physics methods that follow simple harmonic motion theory using Euler's method (small time frames)
- Implemented AnimationTimer for realistic animation
  - Created updatePhysics(), updateLayout(), and updateGraphs() methods that are called many times per second to ensure the motion of the pendulum to be smooth
- Added JUnit tests for physics-related methods  
- Fixed pause/start issues affecting pendulum behavior
- Added the Load and Save feature  
- Improved loading/saving variable logic
- Added icons to the pendulum and graph windows
- Implemented graph elements in the controllers
- Cleaned up the code and added comments and javaDoc everywhere
- Fixed issues all over the code

##  Thomas Archambault
- Created Graph window FXML
- Created and organized the GraphController class
- Implemented the graph window button that opens a new stage with the graphs
- Implemented plotting fucntionnality for:
   - Angle vs. time
   - Velocity vs. time
   - Acceleration vs. time
- Added real-time chart updating using data series
- Added clearGraphs() to reset all chart data
- Helped shape the first version of Pendulum FXML
- Fixewd issues all ober the code
- Added comments and JavaDoc everwhere

## Summary
- Adam focused on pendulum logic, physics, and unit testing.
- Thomas ensured the that the graphing system displays and updates cleanly and dynamically without issues.

- We both worked on things like documentation and comments as well as each FXML layout relevant to our tasks.

  

   




