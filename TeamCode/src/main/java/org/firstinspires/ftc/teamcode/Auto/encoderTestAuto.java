package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

@Autonomous(name="encoderTestAuto")
public class encoderTestAuto extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;
    @Override
    public void runOpMode() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, this, null, null);

        waitForStart();

        if (opModeIsActive()) {
            while (true) {
                double CPR = 2;

                // Get the current position of the motor
                int position = robot.backRight.getCurrentPosition();
                double revolutions = position / CPR;

                double angle = revolutions * 360;
                double angleNormalized = angle % 360;

                // Show the position of the motor on telemetry
                telemetry.addData("Encoder Position", position);
                telemetry.addData("Encoder Revolutions", revolutions);
                telemetry.addData("Encoder Angle (Degrees)", angle);
                telemetry.addData("Encoder Angle - Normalized (Degrees)", angleNormalized);
                telemetry.update();
            }
        }
    }
}
