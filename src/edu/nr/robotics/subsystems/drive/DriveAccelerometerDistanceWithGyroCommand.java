package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.nr.lib.navx.NavX;
import edu.nr.lib.AngleGyroCorrection;
import edu.nr.lib.FieldCentric;

/**
 *
 */
public class DriveAccelerometerDistanceWithGyroCommand extends NRCommand {

	double distance; // in meters
	double speed; // from 0 to 1
	
	double distCurrent;
	double velCurrent;
	
	AngleGyroCorrection gyroCorrection;

	public DriveAccelerometerDistanceWithGyroCommand(double distance, double speed) {
		this.speed = speed;
		this.distance = distance;
		requires(Drive.getInstance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return distCurrent > distance;
	}

	@Override
	protected void onStart() {
        gyroCorrection = new AngleGyroCorrection();
		distCurrent = 0;
		velCurrent = 0;
		FieldCentric.getInstance().reset();
	}

	@Override
	protected void onExecute() {
		velCurrent += NavX.getInstance().getX();
		distCurrent += velCurrent;
		Drive.getInstance().arcadeDrive(speed, gyroCorrection.getTurnValue());
	}

	@Override
	protected void onEnd(boolean interrupted) {
		Drive.getInstance().tankDrive(0, 0);
	}
}
