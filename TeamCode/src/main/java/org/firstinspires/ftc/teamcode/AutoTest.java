package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoTest", group = "auto")
public class AutoTest extends LinearOpMode {
    Library collection = new Library();
    RobotClass robot;
    double angularDistance;

    public void stopMotors() {
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new RobotClass(hardwareMap);

        robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        robot.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        //turnUntilAngleM(90, 0.3);// turns for 90 degrees
        turnUntilAngle(90);
//        autoRegularDrive(0.25, 0, 500);
//        autoRegularDrive(0, 0.45, 800);   (park code)
//        autoRegularDrive(0.5, 0, 1300);

    }

    public void autoRegularDrive(double Forward, double Turn, long time) throws InterruptedException {
        collection.regularDrive(-Forward, Turn);
        double[] wheelSpeeds = collection.getWheelSpeeds();
        robot.frontLeft.setPower(-wheelSpeeds[0]);
        robot.frontRight.setPower(wheelSpeeds[1]);
        robot.backLeft.setPower(-wheelSpeeds[0]);
        robot.backRight.setPower(wheelSpeeds[1]);
        sleep(time);
        collection.regularDrive(0, 0);
        robot.frontLeft.setPower(-wheelSpeeds[0]);
        robot.frontRight.setPower(wheelSpeeds[1]);
        robot.backLeft.setPower(-wheelSpeeds[0]);
        robot.backRight.setPower(wheelSpeeds[1]);
    }

    // turns to angle using IMU
    public void turnUntilAngleF(int angle) {
        // sets up stop variable
        double turnVal = 1;
        double maxErrorAllowed = 0.5;
        double powerReduce = 1;
        boolean finish = false;
        // robot try to hit the correct angle

        angularDistance = Math.min(Math.abs(robot.getHeading() - angle), Math.abs((180 - Math.abs(robot.getHeading())) + (180 - Math.abs(angle))));
        // wait till robot is finished
        telemetry.addData("angularDistance", angularDistance);
        telemetry.update();
        while (robot.frontLeft.isBusy()) {
            //do nothing
        }

        //correct angle if needed
        //if the angle is off....
        while (!isStopRequested()) {
            while (!finish) {
                powerReduce = (angularDistance / angle);
                telemetry.addData("angularDistance", angularDistance);
                telemetry.addData("powerReduce", powerReduce);
                telemetry.addData("currentAngle", robot.getHeading());
                telemetry.update();

                //MIN(ABS(actual - wanted), ABS((180 - ABS(actual)) + (180 - ABS(wanted))));
                angularDistance = Math.min(Math.abs(robot.getHeading() - angle), Math.abs((180 - Math.abs(robot.getHeading())) + (180 - Math.abs(angle))));
                // sets direction
                if (robot.getHeading() <= angle) {
                    if ((robot.getHeading() - angle) <= 180) {
                        //right
                    } else {
                        //left
                        turnVal = -turnVal;
                    }
                } else {
                    if (angle - robot.getHeading() <= 180) {
                        //left
                        turnVal = -turnVal;
                    } else {
                        //right
                    }
                }
//                powerReduce = Math.min(Math.max(powerReduce, .25), .15);
//                turnVal = powerReduce * turnVal;

                robot.frontLeft.setPower(-turnVal / 2);
                robot.frontRight.setPower(turnVal / 2);
                robot.backLeft.setPower(-turnVal / 2);
                robot.backRight.setPower(turnVal / 2);

                if (angularDistance < maxErrorAllowed) {
                    stopMotors();

                }
                if (angularDistance < maxErrorAllowed) {
                   finish = true;
                }
            }
        }


        // drives forward to a precise amount using imu
//    public void driveForwardPerfect ( int forward) {
//        // give the robot a try
//        robot.frontLeft.setTargetPosition( forward );
//        robot.frontRight.setTargetPosition( forward );
//        robot.backLeft.setTargetPosition( forward );
//        robot.backRight.setTargetPosition( forward );
//        // fix it's mistakes
//        while (forward != distanceCheckerOnRobot) {
//            if (forward != distanceCheckerOnRobot) {
//                forward - distanceCheckerOnRobot = forward;
//                robot.frontLeft.setTargetPosition(forward);
//                robot.frontRight.setTargetPosition(forward);
//                robot.backLeft.setTargetPosition(forward);
//                robot.backRight.setTargetPosition(forward);
//            } else {
//            // do nothing
//            }
//        }
//
//
//    }


    }

    public void turnUntilAngle(int angle) {
        // sets up stop variable
        boolean stop = false;
        double turnVal = 1;
        double maxErrorAllowed = 2.5;
        double slowDownAngle = 15;
        while (robot.frontLeft.isBusy()) {
            //do nothing
        }

        //correct angle if needed
        //if the angle is off....
        while (!isStopRequested()) {

            //MIN(ABS(actual - wanted), ABS((180 - ABS(actual)) + (180 - ABS(wanted))));
            angularDistance = Math.min(Math.abs(robot.getHeading() - angle), Math.abs((180 - Math.abs(robot.getHeading())) + (180 - Math.abs(angle))));
            if (!stop) {
                //powerReduce = (angularDistance / angle);
                telemetry.addData("angularDistance", angularDistance);
                telemetry.addData("currentAngle", robot.getHeading());
                telemetry.addData("Power", turnVal);
                telemetry.update();
                if (angularDistance <= maxErrorAllowed) {
                    stopMotors();
                    stop = true;
                } else {
                    if (robot.getHeading() <= angle) {
                        if ((robot.getHeading() - angle) <= 180) {
                            //right
                        } else {
                            //left
                            turnVal = -turnVal;
                        }
                    } else {
                        if (angle - robot.getHeading() <= 180) {
                            //left
                            turnVal = -turnVal;
                        } else {
                            //right
                        }

                        robot.frontLeft.setPower(-turnVal);
                        robot.frontRight.setPower(turnVal);
                        robot.backLeft.setPower(-turnVal);
                        robot.backRight.setPower(turnVal);
                    }
                }
            }

        }
    }

    public void turnUntilAngleM(int angle, double cDir) {
        // sets up stop variable
        boolean stop = false;
        double turnVal = 1;
        double maxErrorAllowed = 2.5;
        double slowDownAngle = 15;
        while (robot.frontLeft.isBusy()) {
            //do nothing
        }

        //correct angle if needed
        //if the angle is off....
        while (!isStopRequested()) {
            //powerReduce = (angularDistance / angle);
            telemetry.addData("angularDistance", angularDistance);
            telemetry.addData("currentAngle", robot.getHeading());
            telemetry.addData("Power", turnVal);
            telemetry.update();

            //MIN(ABS(actual - wanted), ABS((180 - ABS(actual)) + (180 - ABS(wanted))));
            angularDistance = Math.min(Math.abs(robot.getHeading() - angle), Math.abs((180 - Math.abs(robot.getHeading())) + (180 - Math.abs(angle))));
            if (!stop) {
                if (angularDistance <= maxErrorAllowed) {
                    stopMotors();
                    stop = true;
                } else {
                    robot.frontLeft.setPower(-turnVal * cDir);
                    robot.frontRight.setPower(turnVal * cDir);
                    robot.backLeft.setPower(-turnVal * cDir);
                    robot.backRight.setPower(turnVal * cDir);
                }
            }

        }
    }
}