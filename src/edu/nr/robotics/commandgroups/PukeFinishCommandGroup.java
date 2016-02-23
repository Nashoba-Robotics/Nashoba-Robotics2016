package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerOuttakeCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerIntakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerOuttakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.nr.robotics.subsystems.shooter.ShooterOffCommand;
import edu.nr.robotics.subsystems.shooter.ShooterReverseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PukeFinishCommandGroup extends CommandGroup {
    
	double oldRampRate;
	
    public  PukeFinishCommandGroup() {
        addParallel(new ShooterOffCommand());
        addParallel(new LoaderRollerNeutralCommand());
        addParallel(new IntakeRollerNeutralCommand());
    }
}
