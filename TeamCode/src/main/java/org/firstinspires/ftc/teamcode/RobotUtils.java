package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

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
    public void CheckForMotif() {
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
    }

    public void turnTowardsAprilTag(double kP, double minPower, double toleranceDeg) {
        // Ensure Limelight is running the AprilTag pipeline
        robot.limelight.pipelineSwitch(1);
        robot.limelight.start();

        while (opMode != null && opMode.opModeIsActive()) {
            LLResult result = robot.limelight.getLatestResult();

            if (result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {
                // Get the first detected tag (you could add logic to pick a specific ID)
                LLResultTypes.FiducialResult tag = result.getFiducialResults().get(0);

                // Horizontal offset from crosshair to tag center in degrees
                double tx = tag.getTargetXDegrees();

                telemetry.addData("Target X (deg)", tx);

                // If within tolerance, stop turning
                if (Math.abs(tx) <= toleranceDeg) {
                    stopMotors();
                    telemetry.addLine("Aligned with AprilTag!");
                    telemetry.update();
                    break;
                }

                // Proportional control for turning
                double turnPower = kP * tx;

                // Apply minimum power to overcome drivetrain deadband
                if (Math.abs(turnPower) < minPower) {
                    turnPower = Math.signum(turnPower) * minPower;
                }

                // Clamp power to [-1, 1]
                turnPower = Math.max(-1, Math.min(1, turnPower));

                // Turn in place: forward=0, turn=turnPower
                drive(0, -turnPower); // negative because tx sign is opposite of motor direction

            } else {
                telemetry.addLine("No AprilTag detected");
                stopMotors();
            }

            telemetry.update();
        }
    }
}