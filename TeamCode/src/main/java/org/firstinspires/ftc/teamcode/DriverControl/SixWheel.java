package org.firstinspires.ftc.teamcode.DriverControl;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// adds to list of teleop programs as SixWheelDrive
@TeleOp(name = "SixWheelDrive", group = "driving")
public class SixWheel extends OpMode {

    RobotClass robot;
    RobotUtils utils;
    String MOTIF;
    boolean Smanual = false;
    boolean Smanualatt = false;
    boolean Lmanual = false;
    boolean Lmanualatt = false;

    private double distance;

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

    // loops as long as the program is running
    public void loop() {
        // driving
        utils.drive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        // lift
//        if (gamepad1.left_bumper && gamepad1.right_bumper && !Lmanualatt) {
//            Lmanual = true;
//            Lmanualatt = true;
//        } else if (!(gamepad1.left_bumper && gamepad1.right_bumper)) {
//            Lmanualatt = false;
//        }
//
//        if (Lmanual) {
//            if (gamepad1.dpad_down) {
//                robot.liftL.setPower(.75);
//                robot.liftR.setPower(.75);
//            } else {
//                robot.liftL.setPower(0);
//                robot.liftR.setPower(0);
//            }
//        } else {
//            robot.liftL.setPower(gamepad1.left_trigger);
//            robot.liftR.setPower(gamepad1.right_trigger);
//        }

        // intake
        robot.intake1.setPower(gamepad2.left_trigger - gamepad2.right_trigger);
//        TODO:// make the intake act as a toggle for the flipper
        robot.intake2.setPosition(-gamepad2.left_stick_y);
//        robot.intake2.setPower(gamepad2.left_stick_x);
        robot.shooter.setPower(gamepad2.right_stick_y);
        // extake
        if (gamepad2.back && gamepad2.dpad_left && !Smanualatt) { // backup in case of limelight break
            Smanual = !Smanual;
            Smanualatt = true;
        } else if (!(gamepad2.back && gamepad2.dpad_left)) { // make sure that it doesn't switch every tic0k
            Smanualatt = false;
        }
        if (Smanual) {
            if (gamepad2.a) {
                // shoot from afar
            }
            if (gamepad2.x) {
                // shoot from close
            }
            robot.turntable.setPower(gamepad2.left_stick_x);
        } else {
            // gamepad2.a -> shoot based on distance using limelight
            // turntable will automatically rotate because of limelight
            utils.faceAprilTag(3, 1);
        }
//        utils.CheckForMotif();
    }
}
