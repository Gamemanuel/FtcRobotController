package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotClass {
    public DcMotorEx frontLeft, frontRight, backRight, backLeft, intake1, shooter, liftL, liftR;
    public CRServo turntable;
    public Servo intake2;
    public IMU imu;
    public Limelight3A limelight;

    public RobotClass(HardwareMap hardwareMap) {
        // configures your robot so that the program can interact with it
        // robot is oriented from the control hub facing you

        // drivetrain definitions
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        // intake1 (the front intake of the robot)
        intake1 = hardwareMap.get(DcMotorEx.class, "intake1");

        // The intake2 is the floop for the back of the robot to push the final ball up into the shooter.
        // this intake 2 is for the axon servo but i don't think that we are going to use it this way because we are not using it as a continuous servo.
        // intake2 = hardwareMap.get(CRServo.class, "intake2");
        intake2 = hardwareMap.servo.get("intake2");

        // extake
        turntable = hardwareMap.get(CRServo.class, "turntable");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // lift This is commented out because the lift is not on the robot
        // liftL = hardwareMap.get(DcMotorEx.class, "liftL");
        // liftR = hardwareMap.get(DcMotorEx.class, "liftR");

        // reverses motors in code so that our code is easier to read
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // switching to the default pipeline
        limelight.pipelineSwitch(0);

        // The code below this is for the encoder control SUBJECT TO CHANGE WITHOUT NOTICE!
        // Reset the motor encoder so that it reads zero ticks




        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                // hey bru, this website is really helpful for configuring this: https://ftc-docs.firstinspires.org/en/latest/programming_resources/imu/imu.html
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
    }
}
