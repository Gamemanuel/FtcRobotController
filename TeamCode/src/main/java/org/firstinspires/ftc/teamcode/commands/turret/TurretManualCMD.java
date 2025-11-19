package org.firstinspires.ftc.teamcode.commands.turret; // Adjust this package name

import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;

public class TurretManualCMD extends CommandBase {
    private final TurretSubsystem turretSubsystem;
    // stores the gamepad
    private final Gamepad gamepad;

    /**
     * Creates a new TurretManualCMD.
     * @param turretSubsystem The turret subsystem to control.
     * @param gamepad The gamepad to read trigger inputs from.
     */
    public TurretManualCMD(TurretSubsystem turretSubsystem, Gamepad gamepad) {
        this.turretSubsystem = turretSubsystem;
        this.gamepad = gamepad;

        // This allows us to interrupt any other commands running at a current time.
        addRequirements(turretSubsystem);
    }

    @Override
    public void initialize() {/* this needs to be here even if it does nothing :{ */}

    /**
     * This method is called repeatedly while the command is running.
     */
    @Override
    public void execute() {
        // Right trigger gives positive power, left trigger gives negative
        double manualPower = gamepad.right_trigger - gamepad.left_trigger;
        turretSubsystem.setTurretPower(manualPower);
    }

    /**
     * Called when the command ends (e.g., bumpers are released).
     */
    @Override
    public void end(boolean interrupted) {
        // Stop the motor
        turretSubsystem.setTurretPower(0);
    }

    /**
     * This command should run as long as the bumpers are held.
     * The button binding will handle stopping it, so we return false.
     */
    @Override
    public boolean isFinished() {
        return false;
    }
}