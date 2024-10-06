package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

// adds to list of teleop programs
@TeleOp(name = "SixWheel", group = "driving")
// this is just for six wheel
public class SixWheel extends LinearOpMode {
    Library collection = new Library();

    // makes it so i can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        RobotClass robot = new RobotClass(hardwareMap);

        //waits for start
        waitForStart();
        while (opModeIsActive()) {
            //put all of the code in here
            //collection.regularDrive(gamepad1.left_stick_y, gamepad1.right_stick_x);
            collection.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
            double[] wheelSpeeds = collection.getWheelSpeeds();

            robot.frontLeft.setPower(-wheelSpeeds[0]);
            robot.frontRight.setPower(wheelSpeeds[1]);
            robot.backLeft.setPower(-wheelSpeeds[0]);
            robot.backRight.setPower(wheelSpeeds[1]);
            telemetry.addLine(String.format("heading: %4.3f", robot.getHeading()));
            telemetry.update();
        }
    }

}