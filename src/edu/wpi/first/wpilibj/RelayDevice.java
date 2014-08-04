package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.RoboRIO.DeviceType;


public abstract class RelayDevice extends Device {
	
	public RelayDevice( int channel ) {
		super( channel, DeviceType.RELAY );
	}
	
}
