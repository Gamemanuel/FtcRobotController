package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.DifferentialDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.seattlesolvers.solverslib.command.RunCommand; // Import RunCommand
import java.util.function.Supplier; // Import Supplier

public class DrivetrainSubsystem extends SubsystemBase {

    // CPR (counts per motor revolution) calculations
    static final double     COUNTS_PER_MOTOR_REV    = 28.0;
    static final double     DRIVE_GEAR_REDUCTION    = 29.7;
    static final double     COUNTS_PER_WHEEL_REV    = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;

    // RPM calculations
    static final double     MOTOR_FREE_SPEED        = 6000;
    static final double     THEORETICAL_RPM         = MOTOR_FREE_SPEED / DRIVE_GEAR_REDUCTION;

    DifferentialDrive Drivetrain;

    public DrivetrainSubsystem(HardwareMap hMap) {
        // Here we are defining our motor groups for the drivetrain
        MotorGroup leftSideOfRobot = new MotorGroup(
                new Motor(hMap, "frontLeft", COUNTS_PER_WHEEL_REV, THEORETICAL_RPM),
                new Motor(hMap, "backLeft", COUNTS_PER_WHEEL_REV, THEORETICAL_RPM)
        );

        MotorGroup rightSideOfRobot = new MotorGroup(
                new Motor(hMap, "frontRight", COUNTS_PER_WHEEL_REV, THEORETICAL_RPM),
                new Motor(hMap, "backRight", COUNTS_PER_WHEEL_REV, THEORETICAL_RPM)
        );

        // reverses the motors on the right side of the drivetrain
        rightSideOfRobot.setInverted(true);

        // define the DifferentialDrive
        Drivetrain = new DifferentialDrive(leftSideOfRobot, rightSideOfRobot);

    }

    public Command Drive(Supplier<Double> xAxis, Supplier<Double> yAxis) {
        // Use RunCommand to continuously execute the driving logic
        return new RunCommand(() -> {
            // Get the current values from the gamepad suppliers
            double x = xAxis.get();
            double y = yAxis.get();
            Drivetrain.arcadeDrive(x, y);
        }, this); // Register this subsystem as a requirement
    }
}
