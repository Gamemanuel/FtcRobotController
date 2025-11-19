package org.firstinspires.ftc.teamcode.TeleOp;

import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.commands.intake.IntakeStopCMD;
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


    }
}
