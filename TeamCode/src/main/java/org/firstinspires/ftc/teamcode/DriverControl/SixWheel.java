package org.firstinspires.ftc.teamcode.DriverControl;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

import java.util.List;
import java.util.Objects;

// adds to list of teleop programs as SixWheelDrive
@TeleOp(name = "SixWheelDrive", group = "driving")
public class SixWheel extends OpMode {

    RobotClass robot;
    RobotUtils utils;
    String MOTIF;

    // makes it so I can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override

    public void init() { //when you press "INIT"
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);

        // This Creates A new Utils object. it thinks that the robot is null but that is because it has not been created yet.
        // you must initialize this object otherwise it thinks that the function is static and the function errors  .
        utils = new RobotUtils(robot, telemetry);
    }
    public void start() { //called once at the start when you press play
        robot.limelight.start();
    } // executes once when you start the program

    public void loop() { //loops as long as the program is running
        utils.drive(gamepad1.left_stick_y, gamepad1.right_stick_x);
        // check for the MOTIF and display it on the driver hub.
        MOTIF = utils.CheckForMotif();
    }
}
