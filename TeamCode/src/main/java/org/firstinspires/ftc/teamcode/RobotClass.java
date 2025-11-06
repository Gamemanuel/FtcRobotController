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
    public DcMotorEx frontLeft, frontRight, backRight, backLeft
           ,intake1, shooter, liftL, liftR // jank looking for easy commenting out
            ;
    public CRServo turntable;

    public Servo intake2;
    public IMU imu;

    public Limelight3A limelight;

    public RobotClass(HardwareMap hardwareMap){
        // configures your robot so that the program can interact with it
        // robot is oriented from the control hub facing you

        // drivetrain
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        // intake
        intake1 = hardwareMap.get(DcMotorEx.class, "intake1");
//        intake2 = hardwareMap.get(CRServo.class, "intake2");
        intake2 = hardwareMap.servo.get("intake2");

        // extake
        turntable = hardwareMap.get(CRServo.class, "turntable");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // lift
//        liftL = hardwareMap.get(DcMotorEx.class, "liftL");
//        liftR = hardwareMap.get(DcMotorEx.class, "liftR");

        // reverses motors in code so that our code is easier to read
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

//        // IMU
//        imu = hardwareMap.get(IMU.class, "imu");
//        // Adjust the orientation parameters to match your robot
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
//
//        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
//        imu.initialize(parameters);

        // limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }

}
