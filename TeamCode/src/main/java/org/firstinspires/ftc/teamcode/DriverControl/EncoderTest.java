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

    public void init() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, null, null, null);
    }

    public void start() {
        // set the initial position target:
        robot.backRight.setTargetPosition(5500);
        robot.backLeft.setTargetPosition(5500);
        robot.frontLeft.setTargetPosition(5500);
        robot.frontRight.setTargetPosition(5500);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        robot.backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // set the velocity of the motor
        robot.backRight.setVelocity(200);
        robot.backLeft.setVelocity(200);
        robot.frontRight.setVelocity(200);
        robot.frontLeft.setVelocity(200);
    }

    public void loop() {
        // Start the motor moving by setting the max velocity to 200 ticks per second
        // While the Op Mode is running, show the motor's status via telemetry
        telemetry.addData("velocity", robot.frontRight.getVelocity());
        telemetry.addData("position", robot.frontRight.getCurrentPosition());
        telemetry.addData("is at target", !robot.frontRight.isBusy());
        telemetry.update();
    }

}
