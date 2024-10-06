package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoTest2", group = "auto")
public class auto_correct extends LinearOpMode {
    Library collection = new Library();
    RobotClass robot;
    double angularDistance;

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new RobotClass(hardwareMap);
        robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        turnUntilAngle(90);


    }

    public void stopMotors() {
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    public void turnUntilAngle(double angle) throws InterruptedException {
        boolean finish = false;
        double turnVal = 1;
        double maxErrorAllowed = .25;
        double powerReduce = .6;

        while (!finish) {
            opModeIsActive();
            angularDistance = Math.min(Math.abs(robot.getHeading() - angle), Math.abs((180 - Math.abs(robot.getHeading())) + (180 - Math.abs(angle))));


            telemetry.addData("angularDistance", angularDistance);
            telemetry.addData("currentAngle", robot.getHeading());
            telemetry.addData("Power", turnVal);
            telemetry.addData("power reduce", powerReduce);
            telemetry.addData("isDone", finish);
            telemetry.update();

            double currentAngle = robot.getHeading();
            if (currentAngle < 0) {//if negative
                currentAngle += 360;
            }

            if (currentAngle >= angle) {
                if ((currentAngle - angle) <= 180) {
                    //right
                    turnVal = powerReduce;
                } else {
                    //left
                    turnVal = -powerReduce;
                }
            } else {
                if (angle - currentAngle <= 180) {
                    //left
                    turnVal = -powerReduce;
                } else {
                    //right
                    turnVal = powerReduce;
                }
            }

            robot.frontLeft.setPower(turnVal);
            robot.frontRight.setPower(turnVal);
            robot.backLeft.setPower(turnVal);
            robot.backRight.setPower(turnVal);
            if (angularDistance <= (1)) {
                powerReduce = .38;
                if (angularDistance <= (maxErrorAllowed)) {
                    telemetry.addData("isDone", finish);
                    telemetry.update();
                    finish = true;
                    powerReduce = .27;
                    stopMotors();

                }
            }
        }


    }

    public void test(double turnVal) {
        robot.frontLeft.setPower(turnVal);
        robot.frontRight.setPower(turnVal);
        robot.backLeft.setPower(turnVal);
        robot.backRight.setPower(turnVal);

    }

}
//            if (currentAngle <= 0) {//if negative
//                currentAngle += 180;
//            }
//
//            if (currentAngle <= angle) {
//                if ((currentAngle - angle) <= 180) {
//                    //right
//                    turnVal = powerReduce;
//                } else {
//                    //left
//                    turnVal = -powerReduce;
//                }
//            } else {
//                if (angle - currentAngle <= 180) {
//                    //left
//                    turnVal = -powerReduce;
//                } else {
//                    //right
//                    turnVal = powerReduce;
//                }
//            }