package org.firstinspires.ftc.teamcode.DriverControl;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

public class EncoderTest extends OpMode {

    RobotClass robot;
    RobotUtils utils;

    public void init() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, null, null, null);
    }

    public void start() {
        // set the initial position target:
        robot.backRight.setTargetPosition(300);
        robot.backLeft.setTargetPosition(300);
        robot.frontLeft.setTargetPosition(300);
        robot.frontRight.setTargetPosition(300);

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
