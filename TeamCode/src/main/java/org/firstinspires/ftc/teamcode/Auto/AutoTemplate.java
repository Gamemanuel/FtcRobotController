package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="LeaveTheLine")
public class AutoTemplate extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;
    @Override
    public void runOpMode() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, this);

        waitForStart();

        if (opModeIsActive()) {
            // insert code here
        }
    }
}
