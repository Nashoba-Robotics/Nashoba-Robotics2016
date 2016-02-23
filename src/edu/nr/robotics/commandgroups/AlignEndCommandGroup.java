package edu.nr.robotics.commandgroups;

import edu.nr.lib.NRCommand;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AlignEndCommandGroup extends CommandGroup {
    
	
    public  AlignEndCommandGroup() {
    	requires(Drive.getInstance());
    }
    
    @Override
    public void start() {
    	IntakeArm.getInstance().disable();
    	Hood.getInstance().disable();
    	Shooter.getInstance().disable();
    	OI.getInstance().alignCommand.cancel();
    }
}
