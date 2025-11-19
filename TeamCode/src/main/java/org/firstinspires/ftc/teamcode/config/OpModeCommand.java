package org.firstinspires.ftc.teamcode.config;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.Subsystem;

import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.DrivetrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FloopSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LLSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;

public abstract class OpModeCommand extends OpMode {

    public DrivetrainSubsystem drivetrainSubsystem;
    public IntakeSubsystem intakeSubsystem;
    public TurretSubsystem turretSubsystem;
    public ShooterSubsystem shooterSubsystem;
    public FloopSubsystem floopSubsystem;
    public LLSubsystem llSubsystem;

    private Alliance alliance;

    public OpModeCommand(Alliance alliance) {
        this.alliance = alliance;
    }

    public IMU imu;
    // Reset the command list
    public void reset() {
        CommandScheduler.getInstance().reset();
    }

    // run the scheduler
    public void run() {
        CommandScheduler.getInstance().run();
    }

    // program commands to the scheduler
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    // registers subsystems to the scheduler
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        register(
                drivetrainSubsystem =  new DrivetrainSubsystem(hardwareMap),
                intakeSubsystem = new IntakeSubsystem(hardwareMap),
                turretSubsystem = new TurretSubsystem(hardwareMap),
                shooterSubsystem = new ShooterSubsystem(hardwareMap),
                floopSubsystem = new FloopSubsystem(hardwareMap)
        );

        imu = hardwareMap.get(IMU.class, "imu");

        initialize();
    }

    public void initImu() {
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
        imu.resetYaw();
    }


    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        run();
    }

    public void stop() {
        reset();
    }

    public abstract void initialize();

}
