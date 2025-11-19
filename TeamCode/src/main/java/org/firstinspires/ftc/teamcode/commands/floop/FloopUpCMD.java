package org.firstinspires.ftc.teamcode.commands.floop;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.FloopSubsystem;

public class FloopUpCMD extends CommandBase {
    FloopSubsystem subsystem;

    public FloopUpCMD(FloopSubsystem subsystem){

        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
        subsystem.hookUp();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
