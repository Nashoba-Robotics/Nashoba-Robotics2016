package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.commandgroups.IntakeArmUpHeightCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmPrepareLowGoalCommand extends CommandGroup {

    public IntakeArmPrepareLowGoalCommand() {
    	addSequential(new IntakeArmUpHeightCommand());
    }

}
