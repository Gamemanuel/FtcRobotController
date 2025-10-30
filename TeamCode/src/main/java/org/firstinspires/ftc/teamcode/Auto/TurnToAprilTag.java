package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="TurnToAprilTag")
public class TurnToAprilTag extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;

    @Override
    public void runOpMode() throws InterruptedException {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry);
        robot.imu.resetYaw();
        waitForStart();
        robot.limelight.start();

        boolean isDone = false;
        while (!isStopRequested() && !isDone) {
            isDone = utils.faceAprilTag(3);
            utils.getMotorPowers(true);
        }
    }
}
