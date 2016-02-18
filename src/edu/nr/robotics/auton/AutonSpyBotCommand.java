package edu.nr.robotics.auton;

import edu.nr.robotics.commandgroups.PrepareLongShotCommandGroup;
import edu.nr.robotics.subsystems.drive.AlignCommand;
import edu.nr.robotics.subsystems.loaderroller.LaserCannonTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonSpyBotCommand extends CommandGroup {
    
    public  AutonSpyBotCommand() {
        addParallel(new PrepareLongShotCommandGroup());
        addSequential(new AlignCommand());
        addSequential(new LaserCannonTriggerCommand());
    }
}
