package org.firstinspires.ftc.teamcode.DriverControl;

import static android.os.SystemClock.sleep;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
//    boolean Lmanual = false;
//    boolean Lmanualatt = false;
    static final double     COUNTS_PER_MOTOR_REV    = 28.0;
    static final double     WHEEL_CIRCUMFERENCE_MM  = 101.6 * 3.14;
    static final double     COUNTS_PER_MM           = COUNTS_PER_MOTOR_REV / WHEEL_CIRCUMFERENCE_MM;
    private double distance;

    // makes it so I can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override

    public void init() { //when you press "INIT"
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);

        // This Creates A new Utils object. it thinks that the robot is null but that is because it has not been created yet.
        // you must initialize this object otherwise it thinks that the function is static and the function errors  .
        utils = new RobotUtils(robot, telemetry, null, gamepad1, gamepad2);

        // Optional: Add telemetry if needed
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    //called once at the start when you press play
    public void start() {
        robot.limelight.start();
//        robot.shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        robot.shooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    } // executes once when you start the program

    // loops as long as the program is running
    public void loop() {
        // 1. Update the utility class's internal gamepad state
        utils.updateGamepadState();

        telemetry.update();

        // driving
        utils.drive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        // lift
//        if (gamepad1.left_bumper && gamepad1.right_bumper && !Lmanualatt) { // check if button combo is pressed and wasn't already pressed before
//            Lmanual = true; // toggle manual mode for lift
//            Lmanualatt = true; // you just attempted to button combo (whether you meant to or not)
//        } else if (!(gamepad1.left_bumper && gamepad1.right_bumper)) { // make sure that it doesn't switch every tick
//            Lmanualatt = false;
//        }
//
//        if (Lmanual) { // if manual mode
//            if (gamepad1.dpad_down) {
//                robot.liftL.setPower(.75);
//                robot.liftR.setPower(.75);
//            } else {
//                robot.liftL.setPower(0);
//                robot.liftR.setPower(0);
//            }
//        } else { // if not manual mode
//            robot.liftL.setPower(gamepad1.left_trigger);
//            robot.liftR.setPower(gamepad1.right_trigger);
//        }

        // intake
        robot.intake1.setPower(gamepad2.left_trigger - gamepad2.right_trigger);
        robot.intake2.setPosition(-gamepad2.left_stick_y * 0.75);
//        int TargetVelocityInTPS = (int)((5000/60) * COUNTS_PER_MOTOR_REV);
//        robot.shooter.setVelocity(TargetVelocityInTPS);
        robot.shooter.setPower(gamepad2.right_stick_y);

        // extake
        if (gamepad2.back && gamepad2.dpad_left && !Smanualatt) { // check if button combo is pressed and wasn't already pressed before
            Smanual = !Smanual; // toggle manual mode for shooter
            Smanualatt = true; // you just attempted to button combo (whether you meant to or not)
        } else if (!(gamepad2.back && gamepad2.dpad_left)) { // make sure that it doesn't switch every tick
            Smanualatt = false;
        }
        if (Smanual) { // is it manual mode
            if (gamepad2.a) {
                // shoot from afar
            }
            if (gamepad2.x) {
                // shoot from close
            }
            robot.turntable.setPower(gamepad2.left_stick_x);
        } else { // is it automatic mode
            // gamepad2.a -> shoot based on distance using limelight
            // turntable will automatically rotate because of limelight
            aprilTagTracking(3, .75);

        }
        // utils.CheckForMotif();
//        telemetry.speak("ding");
//        sleep(2000);
//        telemetry.addData("shooterVelocity", robot.shooter.getVelocity());
//        telemetry.update();
    }

    /**
     The function aprilTagTracking has 2 inputs:
         <ul>
             <li>
                double tolerance: tolerance is used to define how precise you want the aiming to be
                note that a lower tolerance does mean a more accurate target but in order to reduce oscillation,
                you are also going to need to lower the speed otherwise you will get a lot of oscillation.
             </li>
             <li>
                double speed: speed is how fast the tracking happens. here it is how fast the turntable is
                going to be going and how fast it can turn.
             </li>
         </ul>
     This function is a manuel override wrapper for the function faceAprilTag in the utils library
     and it allows the driver2 (Madelyn) to override the tracking of the april tag to make sure that
     we can control the robot if the robot goes haywire.

     * @author Gavin Rappleye & william Finch
     * @version 1

     */
    public void aprilTagTracking(double tolerance, double speed) {
        // If the bumpers on Madelyn's controller are pressed we override the regular
        // limelight code for auto tracking
        if (gamepad2.left_bumper || gamepad2.right_bumper) {
            double turntablePower = 0;
            if (gamepad2.left_bumper) {
                turntablePower = speed;
            } else if (gamepad2.right_bumper) {
                turntablePower = -speed;
            }
            robot.turntable.setPower(turntablePower);
        // If the bumpers on Madelyn's controller are not pressed then let the limelight
        // handel the auto targeting code based on the utils function
        } else {
            utils.faceAprilTag(tolerance, speed);
        }
    }
}
