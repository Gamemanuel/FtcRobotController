package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="TurnToAprilTag")
public class TurnToAprilTag extends OpMode {
    RobotClass robot;
    RobotUtils utils;
    String MOTIF;

    @Override
    public void init() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry);
        robot.imu.resetYaw();
    }

    @Override
    public void start() {
        robot.limelight.start();
    }

    @Override
    public void loop() {
        // check for the MOTIF and display it on the driver hub.
        // MOTIF = utils.CheckForMotif();
        // Turn toward tag with speed=0.1, tolerance=1 degree off
//        utils.turnTowardsAprilTag(0.1, 1.0);
        utils.autoTurn(90);
    }
}
