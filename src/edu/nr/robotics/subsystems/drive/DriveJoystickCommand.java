package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;
import edu.nr.lib.GyroCorrection;
import edu.nr.lib.NRMath;
import edu.nr.robotics.OI;
import edu.nr.lib.AngleGyroCorrection;

/**
 *
 */
public class DriveJoystickCommand extends CMD {
	private double oldTurn;
	
	GyroCorrection gyroCorrection;

	public DriveJoystickCommand() {
		requires(Drive.getInstance());
        gyroCorrection = new AngleGyroCorrection();
	}

	@Override
	protected void onStart() {
		oldTurn = OI.getInstance().getArcadeMoveValue();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void onExecute() {
		if (OI.getInstance().drivingModeChooser.getSelected().equals("arcade")) {
			double moveValue = OI.getInstance().getArcadeMoveValue();
			if (OI.getInstance().reverseDriveDirection()) {
				moveValue *= -1;
			}
			
			double rotateAdjustValue = OI.getInstance().getQuickTurn();

			double rotateValue = OI.getInstance().getArcadeTurnValue() * rotateAdjustValue;

			moveValue = NRMath.squareWithSign(moveValue);
			rotateValue = NRMath.squareWithSign(rotateValue);

			if(Math.abs(rotateValue) < 0.05)
	    	{
	    		if (Math.abs(moveValue) > .1)
	    		{
	    			rotateValue = gyroCorrection.getTurnValue();
	    		}
		    	else
		    	{	    		
		    		gyroCorrection.clearInitialValue();
		    	}
	    	}
	    	else
	    	{	    		
	    		gyroCorrection.clearInitialValue();
	    	}
			
			double negInertia = rotateValue - oldTurn;

			// Negative inertia!
			double negInertiaScalar;

			if (rotateValue * negInertia > 0) {
				negInertiaScalar = 0.5;
			} else {
				if (Math.abs(rotateValue) > 0.65) {
					negInertiaScalar = 1.0;
				} else {
					negInertiaScalar = 0.6;
				}
			}

			rotateValue = rotateValue + negInertia * negInertiaScalar;

			Drive.getInstance().arcadeDrive(moveValue,rotateValue,true);

			oldTurn = rotateValue;
		} else {
			// Get values of the joysticks
			double left = OI.getInstance().getTankLeftValue();
			double right = OI.getInstance().getTankRightValue();

			// Do the math for turning
			if (Math.abs(left - right) < .25) {
				left = (Math.abs(left) + Math.abs(right)) / 2 * Math.signum(left);
				right = (Math.abs(left) + Math.abs(right)) / 2 * Math.signum(right);
			}

			// cube the inputs (while preserving the sign) to increase fine
			// control while permitting full power
			right = right * right * right;
			left = left * left * left;

			Drive.getInstance().tankDrive(OI.getInstance().speedMultiplier * left,
					-OI.getInstance().speedMultiplier * right);

		}
	}

	// Always return false for a default command
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void onEnd(boolean interrupted) {
		Drive.getInstance().arcadeDrive(0, 0);
	}
}