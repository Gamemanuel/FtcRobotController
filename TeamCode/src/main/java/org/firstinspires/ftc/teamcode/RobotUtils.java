package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;
import java.util.Objects;

public class RobotUtils {
    private RobotClass robot;
    private Telemetry telemetry;
    private LinearOpMode opMode;
    boolean isMotifDecoded = false;
    String MOTIF = "";

    // Constructor for TeleOp (no opMode needed)
    public RobotUtils(RobotClass robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    // Constructor for Auto (opMode needed for opModeIsActive)
    public RobotUtils(RobotClass robot, Telemetry telemetry, LinearOpMode opMode) {
        this(robot, telemetry);
        this.opMode = opMode;
    }

    public void drive(double forward, double turn) {
        robot.frontLeft.setPower(forward + turn);
        robot.frontRight.setPower(forward - turn);
        robot.backLeft.setPower(forward + turn);
        robot.backRight.setPower(forward - turn);
    }

    public void runMotors(double power) {
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(power);
    }

    public void stopMotors() {
        runMotors(0);
    }

    public void driveBasic(double left, double right, long timeMs) {
        robot.frontLeft.setPower(left);
        robot.backLeft.setPower(left);
        robot.frontRight.setPower(right);
        robot.backRight.setPower(right);

        long startTime = System.currentTimeMillis();
        while ((opMode == null || opMode.opModeIsActive()) &&
                System.currentTimeMillis() - startTime < timeMs) {
            telemetry.addData("Driving", "L: %.2f R: %.2f", left, right);
            telemetry.update();
        }

        stopMotors();
    }

    // check for the MOTIF and display it
    public String CheckForMotif() {
        // make it so the motif is only decoded once
        if (!isMotifDecoded) {
            // start the pipeline number zero
            robot.limelight.pipelineSwitch(0); // Set to your AprilTag pipeline
            robot.limelight.start();

            // get the april tag ID
            LLResult result = robot.limelight.getLatestResult();
            // the result must not be null, must be valid, and there must be no current MOTIF (this makes sure that once it finds the motif it does not sense for it again"
            if (result != null && result.isValid()) {
                // Get list of detected AprilTags
                List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

                for (LLResultTypes.FiducialResult fr : fiducials) {
                    int tagId = fr.getFiducialId();  // Correct usage
                    telemetry.addData("AprilTag ID", tagId);
                    // You can also access fr.getFamily(), fr.getTargetXDegrees(), etc.

                    // convert tag ID to corresponding MOTIF pattern
                    // 21 = GPP, 22 = PGP, 23 = PPG
                    if (tagId == 21) {
                        MOTIF = "GPP";
                    }
                    if (tagId == 22) {
                        MOTIF = "PGP";
                    }
                    if (tagId == 23) {
                        MOTIF = "PPG";
                    }

                    isMotifDecoded = true;
                }

            } else {
                telemetry.addData("Limelight", "No valid AprilTag result");
            }
        }

        // display the MOTIF value
        telemetry.addData("MOTIF", MOTIF);

        // update the telemetry
        telemetry.update();

        return MOTIF;
    }

    public void turnTowardsAprilTag(double speed, double tolerance) {
        // Ensure Limelight is running the AprilTag pipeline
        robot.limelight.pipelineSwitch(1);

        // if limelight is not started start it
        if (!robot.limelight.isRunning()) {
            robot.limelight.start();
        }

//        make it so it turns until x offset is equal to zero
        LLResult result = robot.limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }

    }

    // Assuming robot and telemetry objects are defined elsewhere
    public void autoTurn(double targetAngle) {
        // --- Step 1: Normalize the target angle to -180 to +180 ---
        // The IMU reports angles in the -180 to +180 range, so we should convert
        // our target to match that range for a simpler comparison.
        if (targetAngle > 180) {
            targetAngle -= 360;
        } else if (targetAngle < -180) {
            targetAngle += 360;
        }

        // --- Step 2: Initialize IMU and state variables ---
        // Do NOT reset yaw inside the loop. Reset once at the start of your OpMode
        // or before calling this method.
        double currentAngle;
        double turnPower;
        double angleError;

        // --- Step 3: Loop until the robot reaches the target angle ---
        // This loop continues as long as the robot is outside the tolerance.
        // The `opModeIsActive()` check prevents the code from running forever.
        while (opMode.opModeIsActive() && Math.abs(getAngleError(targetAngle)) > 1.0) {

            // --- Step 4: Calculate the error and motor power ---
            angleError = getAngleError(targetAngle);

            // Simple proportional control: power is proportional to the error.
            // `0.01` is a starting constant; you will need to tune this value.
            // Larger error = higher power.
            turnPower = angleError * 0.01;

            // Cap the motor power to avoid oscillation near the target
            // and to ensure enough power to turn against friction.
            turnPower = Math.max(-0.6, Math.min(turnPower, 0.6));
            turnPower = Math.copySign(Math.max(0.2, Math.abs(turnPower)), turnPower);

            // --- Step 5: Drive the motors ---
            // For a tank drive, turnPower is positive for one side and negative for the other.
            // Your `drive` function may require a different syntax.
            // This is a placeholder for your robot's specific driving commands.
            // Assuming your drive function takes (turningPower, 0 for forward/back)
            drive(turnPower, 0);

            // --- Step 6: Provide telemetry feedback ---
            telemetry.addData("Target Angle", targetAngle);
            telemetry.addData("Current Angle", getCurrentAngle());
            telemetry.addData("Angle Error", angleError);
            telemetry.addData("Turn Power", turnPower);
            telemetry.update();
        }

        // --- Step 7: Stop the motors after the turn is complete ---
        stopMotors();
    }

    // Helper method to get the current IMU heading
    public double getCurrentAngle() {
        return robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    // Helper method to calculate the normalized angle error, accounting for the -180/180 wrap
    public double getAngleError(double targetAngle) {
        double currentAngle = getCurrentAngle();
        double angleDifference = targetAngle - currentAngle;

        // Normalize the error to the range of -180 to +180
        while (angleDifference > 180) {
            angleDifference -= 360;
        }
        while (angleDifference <= -180) {
            angleDifference += 360;
        }

        return angleDifference;
    }
}