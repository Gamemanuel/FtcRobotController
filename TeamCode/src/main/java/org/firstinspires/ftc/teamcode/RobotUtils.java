package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

    public void turnToAngle(double angleT) {
        boolean finished = false;
        double maxError = 1;
        while (!finished) {
            double difference = getHeading() - angleT;
            if (difference > 0) {
                drive(0, 0.25);
            } else {
                drive(0, -0.25);
            }
            if (Math.abs(difference) < maxError) {
                finished = true;
                stopMotors();
            }
        }

        }
    public double getHeading() {
        Orientation theta = robot.imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        return theta.thirdAngle;
    }
}