package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.RoboRIO.DeviceType;

public abstract class DIODevice extends Device {
	
	public DIODevice( int channel ) {
		super( channel, DeviceType.DIO );
	}
	
}
