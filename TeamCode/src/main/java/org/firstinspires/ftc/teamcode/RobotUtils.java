package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotUtils {
    private RobotClass robot;
    private Telemetry telemetry;
    private LinearOpMode opMode;

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

    public void drive(double leftJoystickY, double rightJoystickX) {
        robot.frontLeft.setPower(leftJoystickY + rightJoystickX);
        robot.frontRight.setPower(leftJoystickY - rightJoystickX);
        robot.backLeft.setPower(leftJoystickY + rightJoystickX);
        robot.backRight.setPower(leftJoystickY - rightJoystickX);
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
}