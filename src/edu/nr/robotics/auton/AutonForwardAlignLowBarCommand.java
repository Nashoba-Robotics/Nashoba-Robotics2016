package edu.nr.robotics.auton;

import edu.nr.lib.AngleUnit;
import edu.nr.lib.WaitUntilGyroCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.nr.robotics.subsystems.drive.DriveConstantCommand;
import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.hood.HoodMoveDownUntilLimitSwitchCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonForwardAlignLowBarCommand extends CommandGroup {

	public AutonForwardAlignLowBarCommand() {
    	addSequential(new HoodMoveDownUntilLimitSwitchCommand());
		addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS, 0.05));
		addParallel(new ShooterHighCommand());
		addSequential(new DriveDistanceCommand(14, 0.6));
		addParallel(new DriveConstantCommand(true, 0.7, 0));
		addSequential(new WaitUntilGyroCommand(20));
		addSequential(new AutonAlignCommand());
	}
	
}