package edu.nr.lib.navx;

import edu.nr.lib.NRMath;
import edu.nr.lib.SmartDashboardSource;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX implements SmartDashboardSource {
	private SerialPort serial_port;
	private IMU imu;

	private NavX() {
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);

			byte update_rate_hz = 100;
			// imu = new IMU(serial_port,update_rate_hz);
			imu = new IMUAdvanced(serial_port, update_rate_hz);
		} catch (Exception e) {
			System.out.println("ERROR: An error occured while initializing the MXP Board");
		}
	}

	int fullRotationCount = 0;
	double lastYaw;

	public double getYaw() {
		if (imu != null && imu.isConnected()) {
			double currentYaw = imu.getYaw();
			if ((lastYaw < -90 || lastYaw > 90) && (currentYaw > 90 || currentYaw < -90)) {
				if (lastYaw < 0 && currentYaw > 0) {
					fullRotationCount--;
				} else if (lastYaw > 0 && currentYaw < 0) {
					fullRotationCount++;
				}
			}

			lastYaw = currentYaw;
			return currentYaw + 360 * fullRotationCount;
		}
		return 0;
	}
	
	public double getYawRad() {
		return NRMath.degToRad(getYaw());
	}

	public double getRoll() {
		if (imu != null && imu.isConnected()) {
			return imu.getRoll();
		}
		return 0;
	}
	
	public double getRollRad() {
		return NRMath.degToRad(getRoll());
	}

	public double getPitch() {
		if (imu != null && imu.isConnected()) {
			return imu.getPitch();
		}
		return 0;
	}
	
	public double getPitchRad() {
		return NRMath.degToRad(getPitch());
	}

	public void resetAll() {
		if (imu != null && imu.isConnected()) {
			imu.zeroYaw();
		}
	}

	// Singleton code
	private static NavX singleton;

	public static NavX getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new NavX();
		}
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("NavX Yaw", getYaw());
		SmartDashboard.putNumber("NavX Roll", getRoll());
		SmartDashboard.putNumber("NavX Pitch", getPitch());
	}
}
