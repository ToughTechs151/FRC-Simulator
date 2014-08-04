package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.wpilibj.Device;

public abstract class DeviceConnection {
	public abstract void invoke( Device master, Device connected );
}
