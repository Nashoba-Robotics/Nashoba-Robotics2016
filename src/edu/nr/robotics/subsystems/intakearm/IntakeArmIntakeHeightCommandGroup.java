package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeUntilPhotoCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerIntakeCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmIntakeHeightCommandGroup extends CommandGroup {

    public IntakeArmIntakeHeightCommandGroup() {
        addParallel(new IntakeRollerIntakeCommand());
        addParallel(new LoaderRollerIntakeCommand());
        addParallel(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS));
        addSequential(new LoaderRollerIntakeUntilPhotoCommand());
        addParallel(new IntakeRollerNeutralCommand());
        addParallel(new LoaderRollerNeutralCommand());
    }
}