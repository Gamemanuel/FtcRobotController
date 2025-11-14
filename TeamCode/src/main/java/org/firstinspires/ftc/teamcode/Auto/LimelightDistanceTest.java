package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

import java.util.concurrent.TimeUnit;
import java.lang.Math;

// You will likely need to implement a helper class to get NetworkTable values.
// Below is a placeholder for that functionality. The FTC SDK doesn't natively include
// NetworkTables access in the OpMode class directly like FRC's WPILib does.

// The snippet below mimics how you might get the data if you had a helper function

@TeleOp(name = "LimelightDistanceTest", group = "Linear OpMode")
public class LimelightDistanceTest extends LinearOpMode {

    // --- Configuration Constants (Tune these for your specific robot) ---
    final double LIMELIGHT_MOUNT_ANGLE_DEGREES = 25.0; // Angle the camera is pitched up from horizontal
    final double LIMELIGHT_LENS_HEIGHT_INCHES = 20.0;  // Height of the camera lens from the floor
    final double GOAL_HEIGHT_INCHES = 60.0;           // Height of the target center from the floor
    // or used a simple class to wrap the NetworkTables interaction.
    RobotClass robot;
    RobotUtils utils;

    @Override
    public void runOpMode() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, this, null, null);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Get the vertical angle offset (ty) from the Limelight
            // NOTE: This assumes a mechanism to get 'ty' from NetworkTables.
            // You may need a custom class to interface with the Control Hub's NetworkTables.
            // For example, using a theoretical helper class:
            double targetOffsetAngle_Vertical = getLimelightTy(); // This function needs implementation

            // Calculate the distance
            double distance = calculateDistance(targetOffsetAngle_Vertical);

            // Send telemetry data to the Driver Station
            telemetry.addData("Status", "Running");
            telemetry.addData("Vertical Angle (ty)", targetOffsetAngle_Vertical);
            telemetry.addData("Distance (Inches)", distance);
            telemetry.update();
        }
    }

    /**
     * Calculates the distance to the target using trigonometry based on camera angles and heights.
     *
     * @param targetOffsetAngle_Vertical The 'ty' value from the Limelight in degrees.
     * @return The estimated horizontal distance to the target in inches.
     */
    private double calculateDistance(double targetOffsetAngle_Vertical) {
        // Calculate the total angle from the horizontal plane to the top of the goal
        double angleToGoalDegrees = LIMELIGHT_MOUNT_ANGLE_DEGREES + targetOffsetAngle_Vertical;
        double angleToGoalRadians = Math.toRadians(angleToGoalDegrees);

        // Calculate distance: d = (h2 - h1) / tan(angle)
        double distanceFromLimelightToGoalInches = (GOAL_HEIGHT_INCHES - LIMELIGHT_LENS_HEIGHT_INCHES) / Math.tan(angleToGoalRadians);

        return distanceFromLimelightToGoalInches;
    }

    /**
     * Placeholder function to get the 'ty' value from the Limelight via NetworkTables.
     * This needs an actual implementation using NetworkTables libraries available for FTC.
     * For basic operation, you might get this value from a dashboard entry.
     */
    private double getLimelightTy() {
        // In a real FTC implementation, you would use a NetworkTable instance to retrieve
        // the "ty" value. The exact code depends on the specific library used.
        // Example:
        // return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);

        // Return a dummy value for demonstration if actual NT access is unavailable
        return 5.0; // Replace with actual code
    }
}
