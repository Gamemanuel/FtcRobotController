package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="LeaveTheLine")
public class LeaveTheLine extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;

    @Override
    public void runOpMode() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);
        // we Use "this" to pass in the opMode
        utils = new RobotUtils(robot, telemetry, this);

        // TODO: ADD a limelight check for april tags on the obelisk

        // waits for you to push the start button
        waitForStart();

        if (opModeIsActive()) {
            utils.driveBasic(0.75, 0.75, 1000);
        }
    }
}
