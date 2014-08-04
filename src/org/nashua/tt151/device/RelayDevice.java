package org.nashua.tt151.device;

import org.nashua.tt151.RoboRIO.DeviceType;

public abstract class RelayDevice extends Device {
	
	public RelayDevice( int channel ) {
		super( channel, DeviceType.RELAY );
	}
	
}
