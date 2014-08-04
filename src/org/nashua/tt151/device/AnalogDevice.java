package org.nashua.tt151.device;

import org.nashua.tt151.RoboRIO.DeviceType;

public abstract class AnalogDevice extends Device {
	
	public AnalogDevice( int channel ) {
		super( channel, DeviceType.ANALOG );
	}
	
}
