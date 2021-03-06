package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmUpHeightCommand extends CommandGroup {

    public IntakeArmUpHeightCommand() {
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_TOP_POS));
    }
}
