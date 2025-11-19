package org.firstinspires.ftc.teamcode.commands.floop;


import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.FloopSubsystem;

public class UpAndDownCMD extends SequentialCommandGroup {

    public UpAndDownCMD(FloopSubsystem floopSubsystem) {
        super(
                new FloopUpCMD(floopSubsystem),
                new WaitCommand(300),
                new FloopDownCMD(floopSubsystem),
                new WaitCommand(300),
                new InstantCommand(()->{
                    floopSubsystem.addnFLick();
                })
        );
    }

}
