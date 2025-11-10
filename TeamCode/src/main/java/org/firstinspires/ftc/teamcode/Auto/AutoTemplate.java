package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// This is commented out because we do not want it to show up in the driver control panel yet.
// @Autonomous(name="AutonomousName")
public class AutoTemplate extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;

    @Override
    public void runOpMode() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, this, null, null);

        waitForStart();

        if (opModeIsActive()) {
            // insert auto code here
        }
    }
}
