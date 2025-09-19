package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="TurnToAprilTag")
public class TurnToAprilTag extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;
    String MOTIF;

    @Override
    public void runOpMode() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);
        // we Use "this" to pass in the opMode
        utils = new RobotUtils(robot, telemetry, this);

        // waits for you to push the start button
        waitForStart();

        if (opModeIsActive()) {
            // check for the MOTIF and display it on the driver hub.
            MOTIF = utils.CheckForMotif();

            // Turn toward tag with kP=0.03, minPower=0.1, tolerance=1 degree
            utils.turnTowardsAprilTag(0.03, 0.1, 1.0);

        }
    }
}
