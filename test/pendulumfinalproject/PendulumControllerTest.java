

package pendulumfinalproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

public class PendulumControllerTest {

    private PendulumController controller;

    @BeforeEach
    public void setup() {
        controller = new PendulumController();

        //set initial physics values manually
        set("mass", 10.0);
        set("gravity", 9.8);
        set("length", 1.0);
        set("airDrag", 0.0);

        set("angle", Math.PI / 4);
        set("angularVelocity", 0.0);
        set("angularAcceleration", 0.0);
    }

    //helper to set fields 
    private void set(String field, double value) {
        try {
            var fields = PendulumController.class.getDeclaredField(field);
            fields.setAccessible(true);
            fields.setDouble(controller, value);
        } catch (Exception e) {
            fail(e);
        }
    }

    //helper to call updatePhysics(dt) 
    private void runPhysics(double dt) {
        try {
            var m = PendulumController.class.getDeclaredMethod("updatePhysics", double.class);
            m.setAccessible(true);
            m.invoke(controller, dt);
        } catch (Exception e) {
            fail(e);
        }
    }

    private double get(String field) {
        try {
            var f = PendulumController.class.getDeclaredField(field);
            f.setAccessible(true);
            return f.getDouble(controller);
        } catch (Exception e) {
            fail(e);
            return 0;
        }
    }
    
    @Test
    public void testAccelerationFormulaCorrect() {
        runPhysics(0.1);

        double acc = get("angularAcceleration");
        double expected = -(9.8 / 1.0) * Math.sin(Math.PI / 4);

        assertEquals(expected, acc, 1e-6,
                "Acceleration α should equal -(g/L)*sin(angle)");
    }

    @Test
    public void testVelocityIncreasesFromAcceleration() {
        runPhysics(0.1);

        double vel = get("angularVelocity");

        assertNotEquals(0.0, vel,
                "Velocity should change from acceleration");
    }

    @Test
    public void testAngleChangesFromVelocity() {
        double oldAngle = get("angle");

        runPhysics(0.1);

        double newAngle = get("angle");

        assertNotEquals(oldAngle, newAngle,
                "Angle should update based on velocity");
    }

    @Test
    public void testAirDragReducesVelocity() {
        set("airDrag", 1.0); // strong damping
        set("angularVelocity", 2.0);

        runPhysics(0.1);

        double newVel = get("angularVelocity");

        assertTrue(newVel < 2.0,
                "Velocity should decrease when damping is applied");
    }

    @Test
    public void testZeroGravityProducesZeroAcceleration() {
        set("gravity", 0.0);

        runPhysics(0.1);

        double acc = get("angularAcceleration");

        assertEquals(0.0, acc, 1e-6,
                "Acceleration should be zero when there is no gravity");
    }

    @Test
    public void testSmallTimeStepCreatesSmallAngleChange() {
        set("angularVelocity", 1.0);

        double oldAngle = get("angle");
        runPhysics(0.001);
        double smallDtAngle = get("angle");

        set("angle", oldAngle);
        set("angularVelocity", 1.0);
        runPhysics(0.1);
        double largeDtAngle = get("angle");

        assertTrue(Math.abs(largeDtAngle - oldAngle) > Math.abs(smallDtAngle - oldAngle),
                "Larger dt should result in larger angle change");
    }
}

