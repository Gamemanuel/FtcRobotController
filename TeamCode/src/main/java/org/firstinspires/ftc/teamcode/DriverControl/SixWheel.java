package org.firstinspires.ftc.teamcode.DriverControl;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// adds to list of teleop programs as SixWheelDrive
@TeleOp(name = "SixWheelDrive", group = "driving")
public class SixWheel extends LinearOpMode {

    RobotClass robot;
    RobotUtils utils;

    // makes it so I can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);

        // This Creates A new Utils object. it thinks that the robot is null but that is because it has not been created yet.
        // you must initialize this object otherwise it thinks that the function is static and the function errors  .
        utils = new RobotUtils(robot, telemetry);

        // TODO: ADD a limelight check for april tags on the obelisk

        // waits for you to click the start button
        waitForStart();

        // while the program is running
        while (opModeIsActive()) {
            utils.drive(gamepad1.left_stick_y, gamepad1.right_stick_x);
        }
    }
}
