package org.firstinspires.ftc.teamcode.TeleOp;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.commands.intake.IntakeStopCMD;
import org.firstinspires.ftc.teamcode.commands.shooter.ShooterAutoLLCMD;
import org.firstinspires.ftc.teamcode.commands.turret.TurretAutoLLCMD;
import org.firstinspires.ftc.teamcode.commands.turret.TurretManualCMD;
import org.firstinspires.ftc.teamcode.config.OpModeCommand;
import org.firstinspires.ftc.teamcode.commands.intake.IntakeInCMD;
import org.firstinspires.ftc.teamcode.commands.intake.IntakeOutCMD;

public abstract class TeleOp extends OpModeCommand {

    // Define the Drivers in a global variable
    GamepadEx Hunter;
    GamepadEx Madelyn;

    public TeleOp(Alliance alliance) {
        super(alliance);
    }

    @Override
    public void initialize() {
        // Assign the drivers to different gamepads
        Hunter = new GamepadEx(gamepad1);
        Madelyn = new GamepadEx(gamepad2);

        // This runs the driveTrain we Pass suppliers for the joystick values
        CommandScheduler.getInstance().setDefaultCommand(
                drivetrainSubsystem,
                drivetrainSubsystem.Drive(
                        () -> (double) gamepad1.right_stick_x,
                        () -> (double) -gamepad1.left_stick_y
                )
        );

        // This is the intake configuration
        CommandScheduler.getInstance().setDefaultCommand(intakeSubsystem, new IntakeStopCMD(intakeSubsystem));

        Button IntakeIn = new GamepadButton(
                Madelyn, GamepadKeys.Button.A
        );

        IntakeIn.whenHeld(new IntakeInCMD(intakeSubsystem));

        Button IntakeOut = new GamepadButton(
                Madelyn, GamepadKeys.Button.B
        );

        IntakeOut.whenHeld(new IntakeOutCMD(intakeSubsystem));
        // END INTAKE CONFIG

        // Turret Config:
        // This will run automatically whenever no other command is using the turret. (auto-aim)
        turretSubsystem.setDefaultCommand(
                new TurretAutoLLCMD(turretSubsystem, llSubsystem)
        );

        // this creates and binds the manual override.
        Command manualOverrideCMD = new TurretManualCMD(turretSubsystem, gamepad2);

        // Get the bumper buttons from gamepad2
        GamepadButton leftBumper = Madelyn.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER);
        GamepadButton rightBumper = Madelyn.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER);

        // When EITHER bumper is held, run the manual command. When released,
        // the manual command will stop and the auto-aim command will take over.
        (leftBumper.or(rightBumper)).whileActiveOnce(manualOverrideCMD);

        // END TURRET CONFIG

        // Shooter config:
        // this will turn off and on the flywheel with the press of the button x.
        Madelyn.getGamepadButton(GamepadKeys.Button.X).toggleWhenActive(new ShooterAutoLLCMD(shooterSubsystem, llSubsystem));

        // END OF SHOOTER CONFIG

    }
}
