package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.List;

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

    public double[] getMotorPowers(boolean disp) {
        double[] array = {robot.frontLeft.getPower(), robot.frontRight.getPower(), robot.backLeft.getPower(), robot.backRight.getPower()};
        if (disp) {
            for (int i = 0; i < 5; i++) {
                telemetry.addData("motor " + i, array[i]);
            }
        }
        return array;
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

    public void turnUntilAngle(double angle) {
        boolean finish = false;
        double turnVal = 1;
        double maxErrorAllowed = .15;
        double powerReduce = 1;
        while (!finish) {
            double angularDistance = Math.min(Math.abs(getHeading() - angle), Math.abs((180 - Math.abs(getHeading())) + (180 - Math.abs(angle))));
            telemetry.addData("angularDistance", angularDistance);
            telemetry.addData("currentAngle", getHeading());
            telemetry.addData("Power", turnVal);
            telemetry.addData("power reduce", powerReduce);
            telemetry.addData("isDone", finish);
            telemetry.update();
            double currentAngle = getHeading();
            if (currentAngle < 0) {//if negative
                currentAngle += 360;
            }
            if (currentAngle >= angle) {
                if ((currentAngle - angle) <= 180) {
                    //right
                    turnVal = powerReduce;
                } else {
                    //left
                    turnVal = -powerReduce;
                }
            } else {
                if (angle - currentAngle <= 180) {
                    //left
                    turnVal = -powerReduce;
                } else {
                    //right
                    turnVal = powerReduce;
                }
            }
            robot.frontLeft.setPower(turnVal);
            robot.frontRight.setPower(turnVal);
            robot.backLeft.setPower(turnVal);
            robot.backRight.setPower(turnVal);
            if (angularDistance <= 1) {
                powerReduce = .8;
                if (angularDistance <= (maxErrorAllowed)) {
                    telemetry.addData("isDone", finish);
                    telemetry.update();
                    finish = true;
                    stopMotors();
                }
            }
        }
    }

    public void faceAprilTag(double tolerance, double speed) {
        robot.limelight.pipelineSwitch(3);
        LLResult llResult = robot.limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            double tx = llResult.getTx() + 5; // offset correction
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Tx with offset", tx);
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Ta", llResult.getTa());
            telemetry.addData("wants to stop", Math.abs(tx) < tolerance);
            if (Math.abs(tx) > tolerance) {
                if (tx > 0) {
                    // negative is right
                    robot.turntable.setPower(-speed);
                } else {
                    robot.turntable.setPower(speed);
                }
            } else {
                robot.turntable.setPower(0);
            }
        } else {
            telemetry.addData("", "Nothing is being detected");
            robot.turntable.setPower(0);
        }
        telemetry.update();
    }

    public double getHeading() {
        Orientation theta = robot.imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        return theta.thirdAngle;
    }
}