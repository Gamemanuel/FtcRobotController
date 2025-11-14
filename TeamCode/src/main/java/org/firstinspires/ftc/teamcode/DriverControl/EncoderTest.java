package org.firstinspires.ftc.teamcode.DriverControl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@TeleOp
public class EncoderTest extends OpMode {

    RobotClass robot;
    RobotUtils utils;
    // token from the official rev docs
    static final double COUNTS_PER_MOTOR_REV = 28.0;

    // the docs are here: https://docs.revrobotics.com/duo-control/hello-robot-java/part-3/autonomous-navigation-onbot/converting-encoder-ticks-to-a-distance
    // we have a 860:29 gear ratio and so we are reducing it by 29/860 because it is a reduction and not a increase
    static final double DRIVE_GEAR_REDUCTION = ((double) 29 /860);

    // 4.6 is the diameter (appx.) and we convert to mm which is 116.84 (appx.) we are going to use 115.0 for leeway
    static final double WHEEL_CIRCUMFERENCE_MM = 115.0 * Math.PI;
    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;

    // define target position
    int leftTarget;
    int rightTarget;

    public void init() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, null, null, null);
    }

    public void start() {
        // convert inches to encoder ticks:


        // set the drive to encoder drive
        robot.backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // set the initial position target:
        setDrivePositonEncoder(1);

        // TPS is ticks per second
        double TPS = (175/60) * COUNTS_PER_WHEEL_REV;

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        robot.backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // set the velocity of the motor
        robot.backRight.setVelocity(TPS);
        robot.backLeft.setVelocity(TPS);
        robot.frontRight.setVelocity(TPS);
        robot.frontLeft.setVelocity(TPS);
    }

    public void loop() {
        // Start the motor moving by setting the max velocity to 200 ticks per second
        telemetry.addData("velocity", robot.frontRight.getPower());
        telemetry.addData("position", robot.frontRight.getCurrentPosition());
        telemetry.addData("is at target", !robot.frontRight.isBusy());
        telemetry.update();
    }

    public double feetToMillimeter(double feet) {
        return feet * 304.8;
    }

    public void setDrivePositonEncoder(double feetToDrive) {
        int leftTarget = (int)(feetToMillimeter(feetToDrive) * COUNTS_PER_MM);
        int rightTarget = (int)(feetToMillimeter(feetToDrive) * COUNTS_PER_MM);


        // set the velocity of the motor
        // right side
        robot.backRight.setPower(leftTarget);
        robot.frontRight.setPower(leftTarget);

        // left side
        robot.backLeft.setPower(rightTarget);
        robot.frontLeft.setPower(rightTarget);
    }
}
