package edu.nr.robotics.auton;

import edu.nr.robotics.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFollowInstructionsCommand extends CommandGroup {
    
    public  AutoFollowInstructionsCommand() {
        addSequential((Command) Robot.getInstance().autoCommandPickerOne.getSelected());
        addSequential((Command) Robot.getInstance().autoCommandPickerTwo.getSelected());
        addSequential((Command) Robot.getInstance().autoCommandPickerThree.getSelected());
        addSequential((Command) Robot.getInstance().autoCommandPickerFour.getSelected());
        addSequential((Command) Robot.getInstance().autoCommandPickerFive.getSelected());
        addSequential((Command) Robot.getInstance().autoCommandPickerSix.getSelected());
    }
}
