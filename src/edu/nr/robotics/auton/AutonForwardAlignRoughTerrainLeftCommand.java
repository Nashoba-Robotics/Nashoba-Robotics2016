package edu.nr.robotics.auton;

import edu.nr.lib.AngleUnit;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDAutonCommand;
import edu.nr.robotics.subsystems.drive.DriveConstantCommand;
import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.nr.robotics.subsystems.hood.HoodMoveDownUntilLimitSwitchCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmHomeHeightCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmIntakeHeightCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonForwardAlignRoughTerrainLeftCommand extends CommandGroup {

	public AutonForwardAlignRoughTerrainLeftCommand() {
		addSequential(new HoodMoveDownUntilLimitSwitchCommand());
		//addSequential(new IntakeArmMoveUpUntilLimitSwitchCommand());
		addSequential(new DriveSimpleDistanceCommand(3.1, 0.3));
		addParallel(new DriveConstantCommand(false, true, true, 0));
		addSequential(new WaitCommand(0.2));
		addSequential(new DriveDistanceCommand(14.4, 1));
		addParallel(new IntakeArmHomeHeightCommand());
		addSequential(new WaitCommand(0.5));
		addSequential(new DriveAnglePIDAutonCommand(30, AngleUnit.DEGREE));
		addSequential(new AutonAlignCommand());
	}
	
}
