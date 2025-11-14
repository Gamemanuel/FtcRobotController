package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// This is commented out because we do not want it to show up in the driver control panel yet.
// @Autonomous(name="AutonomousName")
public class FlywheelShootAuto extends LinearOpMode {
    RobotClass robot;
    RobotUtils utils;
    static final double     COUNTS_PER_MOTOR_REV    = 28.0;
    static final double     WHEEL_CIRCUMFERENCE_MM  = 101.6 * 3.14;
    static final double     COUNTS_PER_MM           = COUNTS_PER_MOTOR_REV / WHEEL_CIRCUMFERENCE_MM;

    @Override
    public void runOpMode() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, this, null, null);

        // init the robot flywheel encoders
        robot.shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        int TargetVelocityInTPS = (int)((5000/60) * COUNTS_PER_MOTOR_REV);

        if (opModeIsActive()) {
            robot.shooter.setVelocity(TargetVelocityInTPS);
        }
    }
}
