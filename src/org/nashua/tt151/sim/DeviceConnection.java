package org.nashua.tt151.sim;

import org.nashua.tt151.device.Device;

public abstract class DeviceConnection {
	public abstract void invoke( Device master, Device connected );
}
