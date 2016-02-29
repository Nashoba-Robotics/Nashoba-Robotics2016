package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	AngleGyroCorrectionSource correction;
	boolean resetCorrection;
	
	double integralDisableDistance = 0; //TODO: Find the integral disable distance

	double accuracyFinishCount = 3;
	double currentCount = 0;
	
    public DriveAnglePIDCommand(double angle, AngleUnit unit) {
    	this(angle, new AngleGyroCorrectionSource(unit), true);    	
    }

    public DriveAnglePIDCommand(double angle, AngleGyroCorrectionSource correction, boolean resetCorrection) {
    	angle = angle - (0.2768*angle - 3.1668) * Math.signum(angle);
    	this.angle = angle;
    	this.correction = correction;
    	this.resetCorrection = resetCorrection;
    	requires(Drive.getInstance());
	}

	// Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	SmartDashboard.putData("Angle PID", pid);
    	SmartDashboard.putNumber("Angle PID Error", pid.getError());
		
		if(Math.abs(pid.getError()) > integralDisableDistance) {
			pid.setPID(SmartDashboard.getNumber("Turn Angle P"), 0, SmartDashboard.getNumber("Turn Angle D"));
		} else {
			pid.setPID(SmartDashboard.getNumber("Turn Angle P"), SmartDashboard.getNumber("Turn Angle I"), SmartDashboard.getNumber("Turn Angle D"));
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Math.abs(pid.getError()) < 2) {
    		currentCount++;
    	} else {
    		currentCount = 0;
    	}
    	return currentCount > accuracyFinishCount;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		pid.disable();
	}

	@Override
	protected void onStart() {
		pid = new PID(SmartDashboard.getNumber("Turn Angle P"),SmartDashboard.getNumber("Turn Angle I"),SmartDashboard.getNumber("Turn Angle D"), new AngleGyroCorrectionSource(), new AngleController());
		pid.enable();
    	pid.setSetpoint(angle);
	}
}
