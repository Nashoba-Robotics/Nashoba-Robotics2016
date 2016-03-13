package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;

public class LoaderRollerIntakeUntilPhotoCommand extends NRCommand {

	public LoaderRollerIntakeUntilPhotoCommand() {
		requires(LoaderRoller.getInstance());
	}

	@Override
	protected void onStart() {
    	LoaderRoller.getInstance().setLoaderSpeed(1);
	}

	@Override
	protected boolean isFinishedNR() {
		return IntakeRoller.getInstance().hasBall();
	}

}