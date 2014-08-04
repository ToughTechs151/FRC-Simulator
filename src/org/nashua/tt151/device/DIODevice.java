package org.nashua.tt151.device;

import org.nashua.tt151.RoboRIO.DeviceType;

public abstract class DIODevice extends Device {
	
	public DIODevice( int channel ) {
		super( channel, DeviceType.DIO );
	}
	
}
