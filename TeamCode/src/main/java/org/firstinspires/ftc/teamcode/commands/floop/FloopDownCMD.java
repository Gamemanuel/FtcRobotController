package org.firstinspires.ftc.teamcode.commands.floop;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.FloopSubsystem;

public class FloopDownCMD extends CommandBase {
    FloopSubsystem subsystem;

    public FloopDownCMD(FloopSubsystem subsystem){

        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
        subsystem.hookDown();
    }

    @Override
    public boolean isFinished(){
            return true;
    }
}
