package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveConstantCommand;
import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmBottomHeightCommandGroup;
import edu.nr.robotics.subsystems.intakearm.IntakeArmMoveUpUntilPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmOffCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmSetMaxSpeedCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.nr.robotics.subsystems.intakearm.IntakeArmWaitForBottomCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoShovelOfFriesCommandGroup extends CommandGroup {
	
    public  AutoShovelOfFriesCommandGroup() {
    	addSequential(new DriveDistanceCommand(-0.1,0.6));
        addParallel(new IntakeArmBottomHeightCommandGroup());
        addSequential(new WaitCommand(1.3));
        addParallel(new IntakeArmOffCommand());
        addSequential(new DriveDistanceCommand(1.5,0.6));
        addParallel(new DriveConstantCommand(false, true, true , 0.3));
        addSequential(new IntakeArmMoveUpUntilPositionCommand(RobotMap.INTAKE_TOP_POS + 0.05));
        addParallel(new IntakeArmPositionCommand(RobotMap.INTAKE_TOP_POS, 0.08));
        addSequential(new DriveDistanceCommand(3,0.75));
    }
}
