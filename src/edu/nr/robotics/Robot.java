package edu.nr.robotics;

import java.util.ArrayList;

import edu.nr.lib.ChiefSubsystem;
import edu.nr.lib.FieldCentric;
import edu.nr.robotics.auton.AutonDoNothingCommand;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

    Command autonomousCommand;
    SendableChooser autoCommandChooser;    
    
    public static ArrayList<ChiefSubsystem> subsystems = new ArrayList<ChiefSubsystem>();

    public enum Mode {
    	TELEOP, AUTONOMOUS, DISABLED
    }
    
    public Mode currentMode;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	OI.init();
		Drive.init();
    	subsystems.add(Drive.getInstance());
    	
    	autoCommandChooser = new SendableChooser();
		autoCommandChooser.addDefault("Do Nothing", new AutonDoNothingCommand());
		//Add more options like:
		//autoCommandChooser.addObject(String name, Command command);
		SmartDashboard.putData("Autonomous Chooser", autoCommandChooser);
		
		OI.getInstance().drivingModeChooser = new SendableChooser();
		OI.getInstance().drivingModeChooser.addDefault("arcade", "arcade");
		OI.getInstance().drivingModeChooser.addObject("tank", "tank");
		SmartDashboard.putData("Driving Mode Chooser", OI.getInstance().drivingModeChooser);
		
		for(Subsystem subsystem : subsystems)
			SmartDashboard.putData(subsystem);
    }
	
    /**
     * This function is run when the autonomous period begins
     */
    public void autonomousInit() {
        autonomousCommand =(Command) autoCommandChooser.getSelected();
        autonomousCommand.start();
        initialize(Mode.AUTONOMOUS);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        periodic(Mode.AUTONOMOUS);
    }

    /**
     * This function is run when the operator control period begins
     */
    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. 
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        initialize(Mode.TELEOP);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        periodic(Mode.TELEOP);
    }

    /**
     * This is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	initialize(Mode.DISABLED);
    	
		//Make sure that the PIDs are disabled, otherwise if we disable while the PIDs are set, we might have issues...
    	if(Drive.getInstance().getPIDEnabled()) { 
    		Drive.getInstance().setPIDSetpoint(0, 0);
    	}
    }
    
    /**
     * This is called periodically while the robot is disabled
     */
	public void disabledPeriodic() {
        periodic(Mode.DISABLED);
	}
    
	/**
	 * A generic periodic function that is called by the periodic functions for the specific modes
	 * @param mode The name of the mode that is currently occuring
	 */
    private void periodic(Mode mode)
    {
    	FieldCentric.getInstance().update();
    	Scheduler.getInstance().run();
    
        putSubsystemDashInfo();
    }
    
    /**
	 * A generic initialization function that is called by the periodic functions for the specific modes
	 * @param mode The name of the mode that just started
     */
    private void initialize(Mode mode)
    {
    	currentMode = mode;
    }
    
    /**
     * Calls the putSmartDashboardInfo function of every subsytem
     */
    private void putSubsystemDashInfo() {
    	for(ChiefSubsystem subsystem : subsystems)
    		subsystem.putSmartDashboardInfo();
    }
}
