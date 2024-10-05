package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "sparkfuntest", group = "auto")
public class sparkFunAuto extends LinearOpMode {
    //define the sensor
    SparkFunOTOS myOtos;
    Library collection = new Library();
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Get a reference to the sensor
        myOtos = hardwareMap.get(SparkFunOTOS.class, "sparkFun");
        // get refrence to the motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        // All the configuration for the OTOS is done in this helper method, check it out!
        configureOtos();
        // Wait for the start button to be pressed
        waitForStart();
        // Get the latest position, which includes the x and y coordinates, plus the
        // heading angle
        SparkFunOTOS.Pose2D pos = myOtos.getPosition();
        //so we can see it is working:
        // Log the position to the telemetry
        telemetry.addData("X coordinate", pos.x); //forward
        telemetry.addData("Y coordinate", pos.y);
        telemetry.addData("Heading angle", pos.h); //turning

        // Update the telemetry on the driver station
        telemetry.update();
        // Reset the tracking algorithm - this resets the position to the origin,
        // but can also be used to recover from some rare tracking errors
        // myOtos.resetTracking();

    }

    private void configureOtos() {
        myOtos.setLinearUnit(DistanceUnit.INCH);
        myOtos.setAngularUnit(AngleUnit.DEGREES);
        // set offset if needed
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setOffset(offset);
        //ajustments if needed
        myOtos.setLinearScalar(1.0);
        myOtos.setAngularScalar(1.0);

        //calibrate
        myOtos.calibrateImu();
    }

    private void NearPerfectDrive(double amountToGoForward, double amountToTurn) {
        //this is set in inches because we are american
        collection.regularDrive(amountToGoForward, amountToTurn);
        SparkFunOTOS.Pose2D pos = myOtos.getPosition();


    }

}
